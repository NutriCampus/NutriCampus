package com.nutricampus.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Felipe on 19/07/2017.
 */

public class DadosAnimalFragment extends Fragment
        implements View.OnClickListener{

    private Spinner spinnerPropriedade;
    private EditText inputIdentificador;
    private EditText inputDataNasc;
    private Switch switchAtivo;
    private Button btnConfirmarDados;
    private EditText inputIdPropriedade;

    private static final String ARG_PARAM1 = "param1";

    String parametro1;

    public static DadosAnimalFragment newInstance(String param1) {
        DadosAnimalFragment fragment = new DadosAnimalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parametro1= getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_dados_animal, container, false);

        inputIdentificador = (EditText) layout.findViewById(R.id.input_identificador);
        inputDataNasc = (EditText) layout.findViewById(R.id.input_data_nascimento);
        spinnerPropriedade = (Spinner) layout.findViewById(R.id.spinnerPropriedade);
        switchAtivo = (Switch) layout.findViewById(R.id.switch_ativo);
        btnConfirmarDados = (Button) layout.findViewById(R.id.btnConfimarDados);
        inputIdPropriedade = (EditText) layout.findViewById(R.id.input_id_propriedade);

        btnConfirmarDados.setOnClickListener(this);
        preencherSpinnerListaPropriedade();

        return layout;
    }

    @Override
    public void onClick(View v) {

        if (!validaDados()) {
            Toast.makeText(getActivity(), R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Animal animal = new Animal();

        Activity activity = getActivity();
        if(activity instanceof AoClicarConfirmaDados) {
            AoClicarConfirmaDados listener = (AoClicarConfirmaDados) activity;
            listener.confirmarDados();
        }

    }

    public interface AoClicarConfirmaDados {
        void confirmarDados();
    }

    protected boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputIdentificador);
        camposTexto.add(inputDataNasc);

        for (TextView view : camposTexto)
            view.setError(null);

        List<TextView> camposVazios = ValidaFormulario.camposTextosVazios(camposTexto);

        if (!camposVazios.isEmpty()) {
            for (TextView view : camposVazios)
                ValidaFormulario.defineStatusCampo(view, getString(R.string.msg_erro_campo));

            valido = false;
        }

        if (!ValidaFormulario.isSelecaoValida(spinnerPropriedade.getSelectedItemPosition(), 0)) {
            TextView text = (TextView) spinnerPropriedade.getSelectedView();
            text.setTextColor(Color.RED);
            valido = ValidaFormulario.defineStatusCampo(text, "");
        } else {
            TextView text = (TextView) spinnerPropriedade.getSelectedView();
            text.setError(null);
            text.setTextColor(Color.BLACK);
        }

        return valido;
    }

    public void preencherSpinnerListaPropriedade() {

        RepositorioPropriedade repositorioPropriedade= new RepositorioPropriedade(getActivity());
        List<Propriedade> todasPropriedades = repositorioPropriedade.buscarTodasPropriedades();

        if (!(todasPropriedades.isEmpty())) {

            // Adiciona a msg de "Selecione..." no spinner da propriedade
            Propriedade posZero = new Propriedade(0, getString(R.string.msg_spinner_propriedade), "", "", "", "", "", "", "", 1,1);
            todasPropriedades.add(0, posZero);

            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

            int posicao;
            String idPropriedade = inputIdPropriedade.getText().toString();

            if (idPropriedade.isEmpty())
                posicao = 0;
            else
                posicao = spinnerPropriedadeAdapter.getPosition(repositorioPropriedade.buscarPropriedade(
                        Integer.parseInt(idPropriedade)));


            spinnerPropriedade.setSelection(posicao);
        } else {
            Propriedade prop = new Propriedade(0, "<< " + getString(R.string.msg_cadastre_propriedade) + " >>", "", "", "", "", "", "", "", 1,1);
            todasPropriedades.add(prop);
            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

        }
    }

}

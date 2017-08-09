package com.nutricampus.app.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarPropriedadeActivity;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.utils.Conversor;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class DadosAnimalFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ANIMAL = "animal";
    public static final String EXTRA_PROPRIEDADE = "propriedade";
    public static final String EXTRA_CAD_ANIMAL = "CadastroDeAnimal";

    private Spinner spinnerPropriedade;
    private EditText inputIdentificador;
    private EditText inputData;
    private Switch switchAtivo;
    private EditText inputIdPropriedade;

    private Calendar data;
    private Animal animal;
    private Propriedade propriedade;

    private SharedPreferencesManager session;

    public static DadosAnimalFragment newInstance(Animal animal, Propriedade propriedade) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ANIMAL, animal);
        args.putSerializable(EXTRA_PROPRIEDADE, propriedade);
        DadosAnimalFragment fragment = new DadosAnimalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getActivity());
        session.checkLogin();

        animal = (Animal) getArguments().getSerializable(EXTRA_ANIMAL);
        propriedade = (Propriedade) getArguments().getSerializable(EXTRA_PROPRIEDADE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_dados_animal, container, false);

        inputIdentificador = (EditText) layout.findViewById(R.id.input_identificador);
        inputData = (EditText) layout.findViewById(R.id.input_data_nascimento);
        spinnerPropriedade = (Spinner) layout.findViewById(R.id.spinnerPropriedade);
        switchAtivo = (Switch) layout.findViewById(R.id.switch_ativo);
        Button btnConfirmarDados = (Button) layout.findViewById(R.id.btnConfimarDados);
        Button btnAddPropriedade = (Button) layout.findViewById(R.id.btn_add_propriedade);
        inputIdPropriedade = (EditText) layout.findViewById(R.id.input_id_propriedade);

        btnConfirmarDados.setOnClickListener(this);
        btnAddPropriedade.setOnClickListener(this);
        inputData.setOnClickListener(this);

        preencherSpinnerListaPropriedade();
        inicializaCampoData();

        if (propriedade != null) {
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
            preencherSpinnerListaPropriedade();
        }

        if (animal != null) {
            inputIdentificador.setText(animal.getIndentificador());
            inputData.setText(Conversor.dataFormatada(animal.getDataDeNascimento()));
            switchAtivo.setChecked(animal.isAtivo());
            inputIdPropriedade.setText(String.valueOf(animal.getIdPropriedade()));
            preencherSpinnerListaPropriedade();
        }

        return layout;
    }

    protected void inicializaCampoData() {
        Calendar calendario = Calendar.getInstance();
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (inputData.getText().toString().equals("")) {
            data = Calendar.getInstance();
            data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));
        } else {
            this.data.setTime(Conversor.stringToDate(inputData.getText().toString()));
        }
    }


    public void showDatePickerDialog(View v) {
        Calendar cDefault = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH));

        Calendar cMax = Calendar.getInstance();
        cMax.set(cMax.get(Calendar.YEAR), cMax.get(Calendar.MONTH), cMax.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMaxDate(cMax.getTime().getTime());

        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        data.set(ano, mes, dia);

        inputData.setText(Conversor.dataFormatada(data));
        inputData.setError(null);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.input_data_nascimento) {
            showDatePickerDialog(v);
            return;
        }

        if (v.getId() == R.id.btn_add_propriedade) {
            Intent intent = new Intent(getActivity(), CadastrarPropriedadeActivity.class);
            intent.putExtra(EXTRA_CAD_ANIMAL, 1);
            startActivity(intent);
            return;
        }

        if (!validaDados()) {
            Toast.makeText(getActivity(), R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        if (animal == null) {
            animal = new Animal(
                    inputIdentificador.getText().toString(),
                    ((Propriedade) spinnerPropriedade.getSelectedItem()).getId(),
                    data,
                    switchAtivo.isChecked(),
                    Integer.parseInt(session.getIdUsuario()));
        } else {
            this.animal.setIndentificador(inputIdentificador.getText().toString());
            this.animal.setIdPropriedade(((Propriedade) spinnerPropriedade.getSelectedItem()).getId());
            this.animal.setDataDeNascimento(data);
            this.animal.setAtivo(switchAtivo.isChecked());
            this.animal.setIdUsuario(Integer.parseInt(session.getIdUsuario()));
        }

        Activity activity = getActivity();
        if (activity instanceof AoClicarConfirmaDados) {
            AoClicarConfirmaDados listener = (AoClicarConfirmaDados) activity;
            listener.confirmarDados(this.animal);
        }
    }

    public interface AoClicarConfirmaDados {
        void confirmarDados(Animal animal);
    }

    protected boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputIdentificador);
        camposTexto.add(inputData);

        for (TextView view : camposTexto)
            view.setError(null);

        List<TextView> camposVazios = ValidaFormulario.camposTextosVazios(camposTexto);

        if (!camposVazios.isEmpty()) {
            for (TextView view : camposVazios)
                view.setError(getString(R.string.msg_erro_campo));

            valido = false;
        }

        if (!ValidaFormulario.isSelecaoValida(spinnerPropriedade.getSelectedItemPosition(), 0)) {
            TextView text = (TextView) spinnerPropriedade.getSelectedView();
            text.setTextColor(Color.RED);
            text.setError("");
            valido = false;
        } else {
            TextView text = (TextView) spinnerPropriedade.getSelectedView();
            text.setError(null);
            text.setTextColor(Color.BLACK);
        }

        return valido;
    }

    public void preencherSpinnerListaPropriedade() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getActivity());
        List<Propriedade> todasPropriedades = repositorioPropriedade.buscarTodasPropriedades();

        if (!(todasPropriedades.isEmpty())) {

            // Adiciona a msg de "Selecione..." no spinner da propriedade
            Propriedade posZero = new Propriedade(0, getString(R.string.msg_spinner_propriedade), "", "", "", "", "", "", "", 1, 1);
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
            Propriedade prop = new Propriedade(0, "<< " + getString(R.string.msg_cadastre_propriedade) + " >>", "", "", "", "", "", "", "", 1, 1);
            todasPropriedades.add(prop);
            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

        }
    }

}

package com.nutricampus.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.ListaDadosComplActivity;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Grupo;
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
public class DadosComplementaresFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_ANIMAL = "animal";

    private TextView txtGrupo;
    private EditText inputData;
    private EditText inputPeso;
    private EditText inputCaminhadaVertical;
    private EditText inputCaminhadaHorizontal;
    private RadioGroup radioGroup;
    private EditText inputSemanaLact;

    private Calendar data;
    private Animal animal;
    private DadosComplAnimal dadosComplAnimal;
    private String grupoSelecionado = "";

    private RepositorioGrupo repositorioGrupo;

    public static DadosComplementaresFragment newInstance(Animal animal) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ANIMAL, animal);
        DadosComplementaresFragment fragment = new DadosComplementaresFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animal = (Animal) getArguments().getSerializable(EXTRA_ANIMAL);
        repositorioGrupo = new RepositorioGrupo(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_dados_complementares, container, false);

        inputData = (EditText) layout.findViewById(R.id.input_identificador);

        inputData.setVisibility(View.GONE);

        inputData = (EditText) layout.findViewById(R.id.input_data_complementar);
        inputPeso = (EditText) layout.findViewById(R.id.input_peso_vivo);
        inputCaminhadaVertical = (EditText) layout.findViewById(R.id.input_caminhada_vertical);
        inputCaminhadaHorizontal = (EditText) layout.findViewById(R.id.input_caminhada_horizontal);
        inputSemanaLact = (EditText) layout.findViewById(R.id.input_semana_lactacao);
        radioGroup = (RadioGroup) layout.findViewById(R.id.rgEec);

        Button btnSalvar = (Button) layout.findViewById(R.id.btn_salvar);
        Button btnHistRegistros = (Button) layout.findViewById(R.id.btn_hist_registros);

        txtGrupo = (TextView) layout.findViewById(R.id.txtGrupos);

        txtGrupo.setOnClickListener(this);
        btnSalvar.setOnClickListener(this);
        inputData.setOnClickListener(this);
        inicializaCampoData();

        if (animal != null) {
            RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(getActivity());
            this.dadosComplAnimal = repositorioDadosComplAnimal.buscarDadosComplAnimal(animal.getId());

            if (dadosComplAnimal != null) {
                inputPeso.setText(String.valueOf(dadosComplAnimal.getPesoVivo()));
                inputData.setText(Conversor.dataFormatada(dadosComplAnimal.getData()));
                inputCaminhadaHorizontal.setText(String.valueOf(dadosComplAnimal.getCaminadaHorizontal()));
                inputCaminhadaVertical.setText(String.valueOf(dadosComplAnimal.getCaminhadaVertical()));
                inputSemanaLact.setText(String.valueOf(dadosComplAnimal.getSemanaLactacao()));

                Log.e("FGP", " <> " + dadosComplAnimal.getIdGrupo());

                txtGrupo.setText("Grupo selecionado: " + repositorioGrupo.buscarGrupo(
                        dadosComplAnimal.getIdGrupo()).getIdentificador());

                if (dadosComplAnimal.getEec() == 0)
                    ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEec())).setChecked(true);
                else
                    ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEec() - 1)).setChecked(true);
                btnSalvar.setText(getString(R.string.atualizar));
                btnHistRegistros.setVisibility(View.VISIBLE);
                btnHistRegistros.setOnClickListener(this);
            }
        }

        return layout;
    }

    private void inicializaCampoData() {
        Calendar calendario = Calendar.getInstance();
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (inputData.getText().toString().equals("")) {
            data = Calendar.getInstance();
            data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));

            inputData.setText(Conversor.dataFormatada(data));
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

    public interface SalvarDadosAnimal {
        void salvar(DadosComplAnimal dadosComplAnimal, String grupoSelecionado);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.txtGrupos) {
            escolherGrupo();
            return;
        }
        else if (v.getId() == R.id.input_data_complementar) {
            showDatePickerDialog(v);
            return;
        }
        else if (v.getId() == R.id.btn_hist_registros) {
            Intent it = new Intent(getActivity(), ListaDadosComplActivity.class);
            it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, animal);
            startActivity(it);
            return;
        }

        if (!validaDados()) {
            Toast.makeText(getActivity(), R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        float caminhadaHorizontal = inputCaminhadaHorizontal.getText().toString().equals("") ? 0.0f : Float.parseFloat(inputCaminhadaHorizontal.getText().toString());
        float caminhadaVertical = inputCaminhadaVertical.getText().toString().equals("") ? 0.0f : Float.parseFloat(inputCaminhadaVertical.getText().toString());

        //Atribuir o valor de EEC
        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(idRadioButton);

        int eec;
        if (rb == null)
            eec = 0;
        else
            eec = Integer.parseInt(String.valueOf(rb.getText()));

        if (dadosComplAnimal == null) {
            dadosComplAnimal = new DadosComplAnimal(
                    data,
                    Float.parseFloat(inputPeso.getText().toString()),
                    eec,
                    caminhadaHorizontal,
                    caminhadaVertical,
                    Integer.parseInt(inputSemanaLact.getText().toString())
            );
        } else {
            dadosComplAnimal.setData(data);
            dadosComplAnimal.setPesoVivo(Float.parseFloat(inputPeso.getText().toString()));
            dadosComplAnimal.setEec(eec);
            dadosComplAnimal.setCaminadaHorizontal(caminhadaHorizontal);
            dadosComplAnimal.setCaminhadaVertical(caminhadaVertical);
            dadosComplAnimal.setSemanaLactacao(Integer.parseInt(inputSemanaLact.getText().toString()));
            dadosComplAnimal.setIdGrupo(repositorioGrupo.buscarGrupo(grupoSelecionado).getId());
        }

        Activity activity = getActivity();
        if (activity instanceof SalvarDadosAnimal) {
            SalvarDadosAnimal listener = (SalvarDadosAnimal) activity;
            listener.salvar(dadosComplAnimal, grupoSelecionado);
        }

    }

    private boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputData);
        camposTexto.add(inputPeso);
        camposTexto.add(inputSemanaLact);
        camposTexto.add(inputCaminhadaVertical);
        camposTexto.add(inputCaminhadaHorizontal);


        for (TextView view : camposTexto)
            view.setError(null);

        List<TextView> camposVazios = ValidaFormulario.camposTextosVazios(camposTexto);

        if (!camposVazios.isEmpty()) {
            for (TextView view : camposVazios)
                view.setError(getString(R.string.msg_erro_campo));

            valido = false;
        }

        return valido;
    }

    public void escolherGrupo() {

        ArrayList<Grupo> listGrupos= new ArrayList<>();
        listGrupos.clear();

        //Adicionar grupos vindos do repositorio
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(getActivity()).getIdUsuario());
        if (repositorioGrupo.buscarPorUsuario(idUsuario).isEmpty()) {
            repositorioGrupo.inserirGrupo(new Grupo("Pastando", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Lactação", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Cio", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Gestante", "", idUsuario));
        }
        listGrupos.addAll((ArrayList)repositorioGrupo.buscarPorUsuario(idUsuario));

        final String[] grupos = new String[listGrupos.size()];

        for(int i = 0; i < grupos.length; i++) {
            grupos[i] = listGrupos.get(i).getIdentificador();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecione um grupo");

        int grupoChecked;
        if(animal == null)
            grupoChecked = -1;
        else {
            grupoChecked = dadosComplAnimal.getIdGrupo();
        }

        builder.setSingleChoiceItems(grupos, grupoChecked - 1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getActivity(),
                        grupos[item], Toast.LENGTH_SHORT).show();

                grupoSelecionado = grupos[item];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtGrupo.setText("Grupo selecionado: " + grupoSelecionado);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
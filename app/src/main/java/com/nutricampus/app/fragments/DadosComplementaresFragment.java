package com.nutricampus.app.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.DadosComplAnimal;
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

public class DadosComplementaresFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private EditText inputIdAnimal;
    private EditText inputData;
    private EditText inputPeso;
    private EditText inputCaminhadaVertical;
    private EditText inputCaminhadaHorizontal;
    private CheckBox ckbPastando;
    private CheckBox ckbLactacao;
    private CheckBox ckbCio;
    private CheckBox ckbGestante;
    private RadioGroup radioGroup;
    private Button btnSalvar;
    private EditText inputSemanaLact;
    private RadioButton radio;

    private Calendar data;
    private int eec;

    public static DadosComplementaresFragment newInstance(String param1) {
        DadosComplementaresFragment fragment = new DadosComplementaresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_dados_complementares, container, false);

        inputData = layout.findViewById(R.id.input_identificador);
        inputData.setVisibility(View.GONE);

        inputData = (EditText) layout.findViewById(R.id.input_data_complementar);
        inputPeso = (EditText) layout.findViewById(R.id.input_peso_vivo);
        inputCaminhadaVertical = (EditText) layout.findViewById(R.id.input_caminhada_vertical);
        inputCaminhadaHorizontal = (EditText) layout.findViewById(R.id.input_caminhada_horizontal);
        inputSemanaLact = (EditText) layout.findViewById(R.id.input_semana_lactacao);
        ckbPastando = (CheckBox) layout.findViewById(R.id.ckb_pastando);
        ckbLactacao = (CheckBox) layout.findViewById(R.id.ckb_lactacao);
        ckbCio = (CheckBox) layout.findViewById(R.id.ckb_cio);
        ckbGestante = (CheckBox) layout.findViewById(R.id.ckb_gestante);
        radioGroup = (RadioGroup) layout.findViewById(R.id.rgEec);
        btnSalvar = (Button) layout.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(this);
        inputData.setOnClickListener(this);
        inicializaCampoData();

        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        radio = (RadioButton) layout.findViewById(idRadioButton);

        return layout;
    }

    protected void inicializaCampoData() {
        Calendar calendario = Calendar.getInstance();
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (inputData.getText().toString().equals("")) {
            data = Calendar.getInstance();
            data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));
            //inputData.setText(Conversor.dataFormatada(data));
        } else {
            this.data.setTime(Conversor.StringToDate(inputData.getText().toString()));
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
        void salvar(DadosComplAnimal dadosComplAnimal);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.input_data_complementar) {
            showDatePickerDialog(v);
            return;
        }

        if (!validaDados()) {
            Toast.makeText(getActivity(), R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        float caminhadaHorizontal = inputCaminhadaHorizontal.getText().toString().equals("") ? 0.0f : Float.parseFloat(inputCaminhadaHorizontal.getText().toString());
        float caminhadaVertical = inputCaminhadaVertical.getText().toString().equals("") ? 0.0f : Float.parseFloat(inputCaminhadaVertical.getText().toString());

        DadosComplAnimal dadosComplAnimal = new DadosComplAnimal(
                data,
                Float.parseFloat(inputPeso.getText().toString()),
                this.eec,
                caminhadaHorizontal,
                caminhadaVertical,
                Integer.parseInt(inputSemanaLact.getText().toString()),
                ckbPastando.isChecked(),
                ckbLactacao.isChecked(),
                ckbGestante.isChecked(),
                ckbCio.isChecked()
        );


        Activity activity = getActivity();
        if(activity instanceof SalvarDadosAnimal) {
            SalvarDadosAnimal listener = (SalvarDadosAnimal) activity;
            listener.salvar(dadosComplAnimal);
        }

    }

    protected boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputData);
        camposTexto.add(inputPeso);
        camposTexto.add(inputSemanaLact);
        //camposTexto.add(inputCaminhadaVertical);
        //camposTexto.add(inputCaminhadaHorizontal);


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
}

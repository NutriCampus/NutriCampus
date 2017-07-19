package com.nutricampus.app.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nutricampus.app.R;

import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Locale.*;

public class ProducaoLeiteActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    @BindView(R.id.input_lactose)
    EditText inputLactose;
    @BindView(R.id.input_quantidade_leite)
    EditText inputQuantidadeLeite;
    @BindView(R.id.input_gordura)
    EditText inputGordura;
    @BindView(R.id.input_proteina_bruta)
    EditText inputProteinaBruta;
    @BindView(R.id.input_proteina_verdadeira)
    EditText inputProteinaVerdadeira;
    @BindView(R.id.input_data)
    EditText inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao_leite);

        ButterKnife.bind(this);

        inicializaCampoData();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void inicializaCampoData(){
        Calendar calendario = Calendar.getInstance();

        if (inputData.getText().toString().equals(""))
            inputData.setText(calendario.get(Calendar.DATE) + "/"
                    + calendario.get(Calendar.MONTH) + "/"
                    + calendario.get(Calendar.YEAR));
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void showDatePickerDialog(View v) {
        Calendar cDefault = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)
        );

        Calendar cMax = Calendar.getInstance();
        cMax.set(cMax.get(Calendar.YEAR), cMax.get(Calendar.MONTH),cMax.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMaxDate(cMax.getTime().getTime());

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        Log.w("DatePicker","Date = " + ano + " " + mes);

        inputData.setText(dia + "/"+ mes + "/" + ano);
    }

}

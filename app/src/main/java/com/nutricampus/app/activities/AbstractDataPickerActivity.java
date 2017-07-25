package com.nutricampus.app.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nutricampus.app.utils.Conversor;

import java.util.Calendar;


abstract class AbstractDataPickerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText inputData;
    protected Calendar data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void inicializaCampoData(EditText input) {
        Calendar calendario = Calendar.getInstance();
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (input.getText().toString().equals("")) {
            data = Calendar.getInstance();
            data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));
            inputData.setText(Conversor.dataFormatada(data));
        } else {
            data.setTime(Conversor.StringToDate(inputData.getText().toString()));
        }
    }

    public void showDatePickerDialogOnClick(View v) {
        Calendar cDefault = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)
        );

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
}

package com.nutricampus.app.activities;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.Conversor;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@java.lang.SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class CadastroProleActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    @BindView(R.id.input_id_prole)
    EditText inputId;

    @BindView(R.id.check_natimorto)
    CheckBox checkNatimorto;
    @BindView(R.id.input_peso_prole)
    EditText inputPeso;
    @BindView(R.id.input_data_nascimento)
    EditText inputData;
    @BindView(R.id.btn_salvar_prole)
    Button buttonSalvar;

    protected Calendar data;
    protected int idAnimalMatriz;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_prole);

        ButterKnife.bind(this);

        inicializaCampoData();
    }

    protected void inicializaCampoData() {
        Calendar calendario = Calendar.getInstance();
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (inputData.getText().toString().equals("")) {
            data = Calendar.getInstance();
            data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));
            inputData.setText(Conversor.dataFormatada(data));
        } else {
            this.data.setTime(Conversor.StringToDate(inputData.getText().toString()));
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

    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Prole prole = getObjetoProducao();

        RepositorioProle repositorio = new RepositorioProle(this);
        int result = repositorio.inserirProle(prole);

        if (result > 0) {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }

    public void onCheckNatimortoClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            inputPeso.setText("0");
            inputPeso.setFocusable(false);
            inputPeso.setEnabled(false);
            inputData.setEnabled(false);
        } else {
            inputPeso.setFocusableInTouchMode(true);
            inputPeso.setEnabled(true);
            inputData.setEnabled(true);
        }

    }

    public boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(inputData);

        if (!checkNatimorto.isChecked())
            campos.add(inputPeso);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }

        return valido;
    }

    public void cancelarOnClick(View view) {
        this.onBackPressed();
    }

    public Prole getObjetoProducao() {
        int id = inputId.getText().toString().equals("") ? 0 : Integer.valueOf(inputId.getText().toString());

        return new Prole(id,
                this.idAnimalMatriz,
                this.data,
                Float.valueOf(inputPeso.getText().toString()),
                checkNatimorto.isChecked());
    }

}

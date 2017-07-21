package com.nutricampus.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.nutricampus.app.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AtualiarHistoricoAnimalActivity extends AppCompatActivity {

    @BindView(R.id.input_identificador)
    EditText inputIdentificador;
    @BindView(R.id.spinner_propriedade)
    Spinner spinnerPropriedade;
    @BindView(R.id.input_peso_vivo)
    EditText inputPesoVivo;
    @BindView(R.id.input_data_complementar)
    EditText inputDataComplementar;
    @BindView(R.id.input_caminhada_vertical)
    EditText inputCaminhadaVertical;
    @BindView(R.id.input_caminhada_horizontal)
    EditText inputCaminhadaHorizontal;
    @BindView(R.id.input_semana_lactacao)
    EditText inputSemanaLactacao;
    @BindView(R.id.ckb_pastando)
    CheckBox ckbPastando;
    @BindView(R.id.ckb_lactacao)
    CheckBox ckbLactacao;
    @BindView(R.id.ckb_cio)
    CheckBox ckbCio;
    @BindView(R.id.ckb_gestante)
    CheckBox ckbGestante;
    @BindView(R.id.rgEec)
    RadioGroup radioGroup;
    @BindView(R.id.ckb_pastando)
    Button btnSalvar;

    private Calendar data;
    private int eec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiar_historico_animal);

        ButterKnife.bind(AtualiarHistoricoAnimalActivity.this);

        preencherSpinnerListaPropriedade();
        inicializaCampoData();


    }
}

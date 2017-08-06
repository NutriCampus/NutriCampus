package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

@SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class VisualizarCompostoActivity extends CadastrarCompostosAlimentaresActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    private void inicializaCampos() {
        identificador = (EditText) findViewById(R.id.input_composto_identificador);
        tipo = (EditText) findViewById(R.id.input_composto_tipo);
        ms = (EditText) findViewById(R.id.input_composto_ms);
        fdn = (EditText) findViewById(R.id.input_composto_fdn);
        ee = (EditText) findViewById(R.id.input_composto_ee);
        mm = (EditText) findViewById(R.id.input_composto_mm);
        cnf = (EditText) findViewById(R.id.input_composto_cnf);
        pb = (EditText) findViewById(R.id.input_composto_pb);
        ndt = (EditText) findViewById(R.id.input_composto_ndt);
        fda = (EditText) findViewById(R.id.input_composto_fda);
        descricao = (EditText) findViewById(R.id.input_composto_descricao);

        int id = getIntent().getIntExtra("id", 0);

        identificador.setText(getIntent().getStringExtra("identificador"));
        identificador.setEnabled(false);
        tipo.setText(getIntent().getStringExtra("tipo"));
        tipo.setEnabled(false);
        ms.setText(String.valueOf(getIntent().getDoubleExtra("ms", 0)));
        ms.setEnabled(false);
        fdn.setText(String.valueOf(getIntent().getDoubleExtra("fdn", 0)));
        fdn.setEnabled(false);
        ee.setText(String.valueOf(getIntent().getDoubleExtra("ee", 0)));
        ee.setEnabled(false);
        mm.setText(String.valueOf(getIntent().getDoubleExtra("mm", 0)));
        mm.setEnabled(false);
        cnf.setText(String.valueOf(getIntent().getDoubleExtra("cnf", 0)));
        cnf.setEnabled(false);
        pb.setText(String.valueOf(getIntent().getDoubleExtra("pb", 0)));
        pb.setEnabled(false);
        ndt.setText(String.valueOf(getIntent().getDoubleExtra("ndt", 0)));
        ndt.setEnabled(false);
        fda.setText(String.valueOf(getIntent().getDoubleExtra("fda", 0)));
        fda.setEnabled(false);
        descricao.setText(getIntent().getStringExtra("descricao"));
        descricao.setEnabled(false);

        btn_salvar = (Button) findViewById(R.id.btn_salvar_cadastro);
        btn_salvar.setVisibility(View.GONE);
    }
}

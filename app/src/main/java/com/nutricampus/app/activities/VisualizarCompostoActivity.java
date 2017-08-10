package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nutricampus.app.R;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class VisualizarCompostoActivity extends CadastrarCompostosAlimentaresActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    private void inicializaCampos() {
        super.init();

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

        btnSalvar = (Button) findViewById(R.id.btn_salvar_cadastro);
        btnSalvar.setVisibility(View.GONE);
    }
}

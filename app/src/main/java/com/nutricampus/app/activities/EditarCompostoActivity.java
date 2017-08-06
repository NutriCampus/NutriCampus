package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.Propriedade;

@SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class EditarCompostoActivity extends CadastrarCompostosAlimentaresActivity {

    CompostosAlimentares compostosAlimentares = new CompostosAlimentares();

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
        tipo.setText(getIntent().getStringExtra("tipo"));
        ms.setText(String.valueOf(getIntent().getDoubleExtra("ms", 0)));
        fdn.setText(String.valueOf(getIntent().getDoubleExtra("fdn", 0)));
        ee.setText(String.valueOf(getIntent().getDoubleExtra("ee", 0)));
        mm.setText(String.valueOf(getIntent().getDoubleExtra("mm", 0)));
        cnf.setText(String.valueOf(getIntent().getDoubleExtra("cnf", 0)));
        pb.setText(String.valueOf(getIntent().getDoubleExtra("pb", 0)));
        ndt.setText(String.valueOf(getIntent().getDoubleExtra("ndt", 0)));
        fda.setText(String.valueOf(getIntent().getDoubleExtra("fda", 0)));
        descricao.setText(getIntent().getStringExtra("descricao"));

        btn_salvar = (Button) findViewById(R.id.btn_salvar_cadastro);
        btn_salvar.setText(R.string.atualizar);

        compostosAlimentares.setId(id);
        compostosAlimentares.setIdentificador(identificador.getText().toString());
        compostosAlimentares.setTipo(tipo.getText().toString());
        compostosAlimentares.setMS(Double.parseDouble(ms.getText().toString()));
        compostosAlimentares.setFDN(Double.parseDouble(fdn.getText().toString()));
        compostosAlimentares.setEE(Double.parseDouble(ee.getText().toString()));
        compostosAlimentares.setMM(Double.parseDouble(mm.getText().toString()));
        compostosAlimentares.setCNF(Double.parseDouble(cnf.getText().toString()));
        compostosAlimentares.setPB(Double.parseDouble(pb.getText().toString()));
        compostosAlimentares.setNDT(Double.parseDouble(ndt.getText().toString()));
        compostosAlimentares.setFDA(Double.parseDouble(fda.getText().toString()));
        compostosAlimentares.setDescricao(descricao.getText().toString());
    }

    @Override
    public void criarComposto(View view) {

        if (identificador.getText().toString().isEmpty() ||
                tipo.getText().toString().isEmpty() ||
                ms.getText().toString().isEmpty() ||
                fdn.getText().toString().isEmpty() ||
                ee.getText().toString().isEmpty() ||
                mm.getText().toString().isEmpty() ||
                cnf.getText().toString().isEmpty() ||
                pb.getText().toString().isEmpty() ||
                ndt.getText().toString().isEmpty() ||
                fda.getText().toString().isEmpty()/* ||
                descricao.getText().toString().isEmpty()*/) {
            Toast.makeText(EditarCompostoActivity.this,
                    getString(R.string.msg_compostos_erro_preenchimento), Toast.LENGTH_SHORT).show();
        } else {
            compostosAlimentares.setIdentificador(identificador.getText().toString());
            compostosAlimentares.setTipo(tipo.getText().toString());
            compostosAlimentares.setMS(Double.parseDouble(ms.getText().toString()));
            compostosAlimentares.setFDN(Double.parseDouble(fdn.getText().toString()));
            compostosAlimentares.setEE(Double.parseDouble(ee.getText().toString()));
            compostosAlimentares.setMM(Double.parseDouble(mm.getText().toString()));
            compostosAlimentares.setCNF(Double.parseDouble(cnf.getText().toString()));
            compostosAlimentares.setPB(Double.parseDouble(pb.getText().toString()));
            compostosAlimentares.setNDT(Double.parseDouble(ndt.getText().toString()));
            compostosAlimentares.setFDA(Double.parseDouble(fda.getText().toString()));
            compostosAlimentares.setDescricao(descricao.getText().toString());

            RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
            boolean rs = repositorioPropriedade.atualizarCompostosAlimentares(compostosAlimentares);
            if (rs) {
                Toast.makeText(EditarCompostoActivity.this,
                        getString(R.string.msg_sucesso_atualizar_registro), Toast.LENGTH_SHORT).show();
                EditarCompostoActivity.this.finish();
            } else {
                Toast.makeText(EditarCompostoActivity.this, "Erro ao gravar Composto", Toast.LENGTH_LONG).show();
            }
        }
    }
}

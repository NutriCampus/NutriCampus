package com.nutricampus.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;


public class CadastrarCompostosAlimentaresActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_compostos_alimentares);
    }

    public void criarComposto(View view) {
        EditText identificador = (EditText) findViewById(R.id.input_composto_identificador);
        EditText tipo = (EditText) findViewById(R.id.input_composto_tipo);

        EditText ms = (EditText) findViewById(R.id.input_composto_ms);
        EditText fdn = (EditText) findViewById(R.id.input_composto_fdn);
        EditText ee = (EditText) findViewById(R.id.input_composto_ee);
        EditText mm = (EditText) findViewById(R.id.input_composto_mm);
        EditText cnf = (EditText) findViewById(R.id.input_composto_cnf);
        EditText pb = (EditText) findViewById(R.id.input_composto_pb);
        EditText ndt = (EditText) findViewById(R.id.input_composto_ndt);
        EditText fda = (EditText) findViewById(R.id.input_composto_fda);
        EditText descricao = (EditText) findViewById(R.id.input_composto_descricao);

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
            Toast.makeText(CadastrarCompostosAlimentaresActivity.this,
                    getString(R.string.msg_compostos_erro_preenchimento), Toast.LENGTH_SHORT).show();
        } else {
            CompostosAlimentares compostosAlimentares = new CompostosAlimentares(
                    tipo.getText().toString(),
                    identificador.getText().toString(),
                    Double.parseDouble(ms.getText().toString()),
                    Double.parseDouble(fdn.getText().toString()),
                    Double.parseDouble(ee.getText().toString()),
                    Double.parseDouble(mm.getText().toString()),
                    Double.parseDouble(cnf.getText().toString()),
                    Double.parseDouble(pb.getText().toString()),
                    Double.parseDouble(ndt.getText().toString()),
                    Double.parseDouble(fda.getText().toString()),
                    descricao.getText().toString());

            RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
            int rs = repositorioPropriedade.inserirCompostoAlimentar(compostosAlimentares);
            if (rs > 0) {
                Toast.makeText(CadastrarCompostosAlimentaresActivity.this,
                        getString(R.string.msg_cadastro_salvo), Toast.LENGTH_SHORT).show();
                CadastrarCompostosAlimentaresActivity.this.finish();
            }
        }


    }
}

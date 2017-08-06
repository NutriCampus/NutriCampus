package com.nutricampus.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;


public class CadastrarCompostosAlimentaresActivity extends AppCompatActivity {
    public EditText identificador = null;
    public EditText tipo = null;

    public EditText ms = null;
    public EditText fdn = null;
    public EditText ee = null;
    public EditText mm = null;
    public EditText cnf = null;
    public EditText pb = null;
    public EditText ndt = null;
    public EditText fda = null;
    public EditText descricao = null;
    public Button btn_salvar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_compostos_alimentares);
    }

    public void criarComposto(View view) {
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

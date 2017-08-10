package com.nutricampus.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastrarCompostosAlimentaresActivity extends AppCompatActivity {
    protected EditText identificador = null;
    protected EditText tipo = null;

    protected EditText ms = null;
    protected EditText fdn = null;
    protected EditText ee = null;
    protected EditText mm = null;
    protected EditText cnf = null;
    protected EditText pb = null;
    protected EditText ndt = null;
    protected EditText fda = null;
    protected EditText descricao = null;
    protected Button btnSalvar = null;

    protected void init() {
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
        btnSalvar = (Button) findViewById(R.id.btn_salvar_cadastro);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_compostos_alimentares);

        init();
    }

    public void criarComposto(View view) {
        if (!validarDados()) {
            Toast.makeText(CadastrarCompostosAlimentaresActivity.this,
                    getString(R.string.msg_compostos_erro_preenchimento), Toast.LENGTH_SHORT).show();
            return;
        }

        CompostosAlimentares compostosAlimentares = getCompostoAlimentar();

        RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
        int rs = repositorioPropriedade.inserirCompostoAlimentar(compostosAlimentares);

        if (rs > 0) {
            Toast.makeText(CadastrarCompostosAlimentaresActivity.this,
                    getString(R.string.msg_cadastro_salvo), Toast.LENGTH_SHORT).show();
            CadastrarCompostosAlimentaresActivity.this.finish();
        } else {
            Toast.makeText(CadastrarCompostosAlimentaresActivity.this, getString(R.string.msg_erro_cadastro_composto), Toast.LENGTH_LONG).show();
        }


    }

    protected boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(identificador);
        campos.add(tipo);
        campos.add(ms);
        campos.add(fdn);
        campos.add(ee);
        campos.add(mm);
        campos.add(cnf);
        campos.add(pb);
        campos.add(ndt);
        campos.add(fda);

        for (TextView textview : ValidaFormulario.camposTextosVazios(campos)) {
            textview.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }

        return valido;
    }

    protected CompostosAlimentares getCompostoAlimentar() {
        return new CompostosAlimentares(
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
    }
}

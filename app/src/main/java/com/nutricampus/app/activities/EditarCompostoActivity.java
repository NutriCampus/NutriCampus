package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarCompostoActivity extends CadastrarCompostosAlimentaresActivity {

    private CompostosAlimentares compostosAlimentares = new CompostosAlimentares();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    private void inicializaCampos() {
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

        btnSalvar.setText(R.string.atualizar);

        compostosAlimentares = getCompostoAlimentar();
        compostosAlimentares.setId(id);
    }

    @Override
    public void criarComposto(View view) {
        if (!validarDados()) {
            Toast.makeText(EditarCompostoActivity.this,
                    getString(R.string.msg_compostos_erro_preenchimento), Toast.LENGTH_SHORT).show();
            return;
        }

        int id = compostosAlimentares.getId();
        compostosAlimentares = getCompostoAlimentar();
        compostosAlimentares.setId(id);

        RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
        boolean rs = repositorioPropriedade.atualizarCompostosAlimentares(compostosAlimentares);
        if (rs) {
            Toast.makeText(EditarCompostoActivity.this,
                    getString(R.string.msg_sucesso_atualizar_registro), Toast.LENGTH_LONG).show();
            EditarCompostoActivity.this.finish();
        } else {
            Toast.makeText(EditarCompostoActivity.this, R.string.msg_erro_gravar_composto, Toast.LENGTH_LONG).show();
        }
    }

}

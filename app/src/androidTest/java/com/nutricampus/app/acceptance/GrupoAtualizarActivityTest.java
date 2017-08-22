package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.entities.Grupo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@RunWith(AndroidJUnit4.class)
@LargeTest


public class GrupoAtualizarActivityTest extends AbstractPreparacaoTestes {
    private RepositorioGrupo repositorioGrupo;
    private Grupo grupo1, grupo2;

    public void criarGrupo() {
        repositorioGrupo = new RepositorioGrupo(InstrumentationRegistry.getTargetContext());
        grupo1 = new Grupo("Test Especial", "GrupoCriadoParaTeste", 1);
        grupo2 = new Grupo("Test Tratamento", "GrupoCriadoParaTeste2", 1);
        repositorioGrupo.inserirGrupo(grupo2);
        repositorioGrupo.inserirGrupo(grupo1);
    }

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        criarGrupo();
        abrirMenu();
        clicarItemMenu(4);
    }

    @After
    public void deletaDadosPosTestes() {
        List<Grupo> list = repositorioGrupo.buscarTodosGrupos();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getIdentificador().contains("Test"))
                repositorioGrupo.removerGrupo(list.get(i));
        }

    }

    @Test
    public void atualizarGrupoNaoExistente() throws Exception {
        espera(500);
        longClickElemento("Test Especial");
        espera(1000);
        clicarBotao(android.R.id.title, "Editar");
        espera(500);
        substituiTexto(R.id.input_nome_grupo, "Test Vacinacao");
        substituiTexto(R.id.input_observacao, "Grupos de animais em vacinacao");
        espera(1000);
        clicarBotao(R.id.btn_salvar_grupo, false);
        espera(500);
        validaToast("Registro atualizado com sucesso");
        espera(1000);
    }

    @Test
    public void atualizarParaGrupoJaExistente() throws Exception {
        espera(500);
        longClickElemento("Test Especial");
        espera(1000);
        clicarBotao(android.R.id.title, "Editar");
        espera(500);
        substituiTexto(R.id.input_nome_grupo, "Test Tratamento");
        substituiTexto(R.id.input_observacao, "Grupos de animais em tratamento");
        espera(500);
        clicarBotao(R.id.btn_salvar_grupo, false);
        espera(500);

        validaToast("Nome já cadastrado");
        espera(1000);
    }
}

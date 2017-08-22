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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@RunWith(AndroidJUnit4.class)
@LargeTest


public class GrupoCadastroActivityTest extends AbstractPreparacaoTestes {
    private RepositorioGrupo repositorioGrupo;
    private Grupo grupo;

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        criarGrupo();
        abrirMenu();
        clicarItemMenu(4);
    }

    @After
    public void deletaDadosPosTestes() {
        if(repositorioGrupo.buscarGrupo("Test Especial") != null)
            repositorioGrupo.removerGrupo(repositorioGrupo.buscarGrupo("Test Especial"));
    }

    @Test
    public void cadastrarGrupoJaExistente() throws Exception {
        espera(500);
        onView(withId(R.id.btn_add_grupo)).perform(click());
        preencheCampos();
        espera(1000);
        clicarBotao(R.id.btn_salvar_grupo, false);
        espera(500);

        validaToast("Nome já cadastrado");
        espera(500);
    }

    @Test
    public void cadastrarGrupoNovo() throws Exception {
        espera(500);
        onView(withId(R.id.btn_add_grupo)).perform(click());
        substituiTexto(R.id.input_nome_grupo, "Tratamento");
        substituiTexto(R.id.input_observacao, "Grupos de animais em tratamento");
        espera(1000);
        clicarBotao(R.id.btn_salvar_grupo, false);
        espera(500);
        validaToast("Cadastro realizado com sucesso");
        espera(500);
    }

    private void preencheCampos() throws Exception {
        substituiTexto(R.id.input_nome_grupo, "Especial");
        substituiTexto(R.id.input_observacao, "Grupos de animais em condicoes especiais");
    }

    public void criarGrupo() {
        repositorioGrupo = new RepositorioGrupo(InstrumentationRegistry.getTargetContext());
        grupo = new Grupo("Test Especial", "GrupoCiadoParaTeste", 1);
        repositorioGrupo.inserirGrupo(grupo);
    }
}

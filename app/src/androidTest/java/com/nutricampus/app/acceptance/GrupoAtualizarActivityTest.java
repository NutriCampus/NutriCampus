package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


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
        criarGrupo();
        realizaLogin();
        abrirMenu();
        clicarItemMenu(4);
    }

    @After
    public void deletaDadosPosTestes() {
        repositorioGrupo.removerGrupoUsuario(1);

    }

    @Test
    public void atualizarGrupoNaoExistente() throws Exception {
        espera(500);
        longClickElemento("Especial");
        espera(1000);
        clicarBotao(android.R.id.title, "Editar");
        espera(500);
        substituiTexto(R.id.input_nome_grupo, "Vacinacao");
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
        longClickElemento("Especial");
        espera(1000);
        clicarBotao(android.R.id.title, "Editar");
        espera(500);
        substituiTexto(R.id.input_nome_grupo, "Tratamento");
        substituiTexto(R.id.input_observacao, "Grupos de animais em tratamento");
        espera(500);
        clicarBotao(R.id.btn_salvar_grupo, false);
        espera(500);
        onView(withId(R.id.input_nome_grupo)).check(matches(withText("Tratamento")));
        //validaToast("Nome já cadastrado");
        espera(1000);
    }
}

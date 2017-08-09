package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarEdicaoAcceptanceTest extends AbstractPreparacaoTestes {
    private RepositorioCompostosAlimentares repositorioCompostosAlimentares;
    private String id1;
    private String id2;

    @Before
    public void setUp() throws Exception {
        id1 = "Identificador1";
        id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

        realizaLogin();
        abrirMenu();
        clicarItemMenu(6);
        espera(500);
    }

    @After
    public void deletaDados() {
        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-01: Atualizar o composto alimentar por um nome já cadastrado;
    public void atualizarCompostoPorNomeJaExistenteTA1() {
        onView(withText(id1)).perform(longClick());

        espera(500);

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_composto_identificador, id2);

        clicarBotao(R.id.btn_salvar_cadastro, true);

        validaToast("Erro ao gravar composto");
    }

    @Test
    //TA-02: Atualizar o composto alimentar por um nome ainda não cadastrado;
    public void atualizarCompostoNormalmenteTA2() {

        onView(withText(id1)).perform(longClick());

        espera(500);

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_composto_identificador, "identificador999");

        espera(500);

        substituiTexto(R.id.input_composto_ms, "999999");

        espera(500);

        clicarBotao(R.id.btn_salvar_cadastro, true);

        onView(allOf(withId(R.id.lista_composto_nome), withText("identificador999"))).check(matches(withText("identificador999")));

    }

    @Test
    //TA-03: Atualizar o composto alimentar por um nome vazio;
    public void atualizarCompostoPorNomeVazioTA3() {

        onView(withText(id1)).perform(longClick());

        espera(500);

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_composto_identificador, "");

        clicarBotao(R.id.btn_salvar_cadastro, true);

        validaToast("Preencha todos os campos");
    }

    @Test
    //TA-04: Atualizar o composto alimentar deixando um nutriente vazio;
    public void atualizarCompostoComNutrienteVazioTA4() {
        onView(withText(id1)).perform(longClick());

        espera(500);

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_composto_ms, "");

        clicarBotao(R.id.btn_salvar_cadastro, true);

        validaToast("Preencha todos os campos");
    }


}

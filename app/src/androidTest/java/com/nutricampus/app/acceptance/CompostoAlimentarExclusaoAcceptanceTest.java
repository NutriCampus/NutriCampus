package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarExclusaoAcceptanceTest extends AbstractPreparacaoTestes {

    private RepositorioCompostosAlimentares repositorioCompostosAlimentares;
    private String id1;
    private String id2;

    @Before
    public void setUp() throws Exception {
        id1 = "Identificador1";
        id2 = "Identificador2";
        String tipo = "tipo1234";
        double ms = 999;
        double fdn = 888;
        double ee = 777;
        double mm = 666;
        double cnf = 555;
        double pb = 444;
        double ndt = 333;
        double fda = 222;
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
    //TA-01: Deletar um composto alimentar deixando (no mínimo) um composto na base;
    public void excluirCompostoDeixandoUmTA1() {

        longClickElemento(id1);
        clicarBotao(android.R.id.title, "Excluir");
        clicarBotao(android.R.id.button1, "Sim");

        validaToast("Composto removido com sucesso");

    }

    @Test
    //TA-02: Deletar um composto alimentar da base deixando a mesma sem nenhum composto alimentar
    public void excluirTodosOsCompostosTA2() {

        longClickElemento(id1);
        clicarBotao(android.R.id.title, "Excluir");
        clicarBotao(android.R.id.button1, "Sim");

        longClickElemento(id2);
        clicarBotao(android.R.id.title, "Excluir");
        clicarBotao(android.R.id.button1, "Sim");

        try {
            ViewInteraction linearLayout = onView(
                    allOf(childAtPosition(
                            allOf(withId(R.id.listaCompostosAlimentares),
                                    childAtPosition(
                                            withId(R.id.resultado_busca_propriedades),
                                            2)),
                            0),
                            isDisplayed()));
            linearLayout.check(doesNotExist());
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("ListView com resultados encontrado, contudo não deveria existir essa view.");
        }
    }


}

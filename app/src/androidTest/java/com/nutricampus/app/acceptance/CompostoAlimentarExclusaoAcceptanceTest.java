package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);

        if (getActivityInstance() instanceof MainActivity) {
            espera(4500);
        } else {
            realizaLogin();
        }

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
    public void excluirComposto_TA1() {

        longClickElemento(id1);
        clicarExcluir();
        clicarSim();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Composto removido com sucesso");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaCompostosAlimentares),
                                childAtPosition(
                                        withId(R.id.resultado_busca_propriedades),
                                        2)),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
    }

    @Test
    //TA-02: Deletar um composto alimentar da base deixando a mesma sem nenhum composto alimentar
    public void excluirComposto_TA2() {

        longClickElemento(id1);
        clicarExcluir();
        clicarSim();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Composto removido com sucesso");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaCompostosAlimentares),
                                childAtPosition(
                                        withId(R.id.resultado_busca_propriedades),
                                        2)),
                        0),
                        isDisplayed()));
        linearLayout2.check(doesNotExist());
    }

    public void longClickElemento(String texto) {
        onView(withText(texto)).perform(longClick());
    }

    public void clicarExcluir() {
        espera(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView.perform(click());
    }

    public void clicarSim() {
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton4.perform(scrollTo(), click());
    }

}

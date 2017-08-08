package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarBuscarAcceptanceTest extends AbstractPreparacaoTestes {

    private RepositorioCompostosAlimentares repositorioCompostosAlimentares;

    @Before
    public void setUp() throws Exception {
        String id1 = "identificadorA1";
        String id2 = "identificadorA2";
        String id3 = "identificadorA3";
        String id4 = "identificadorA4";
        String tipo = "tipo1234";
        double ms = 999, fdn = 888, ee = 777;
        double mm = 666, cnf = 555, pb = 444;
        double ndt = 333, fda = 222;
        String descricao = "descricao1";

        repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        CompostosAlimentares ca1 = new CompostosAlimentares(20, tipo, id1, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca2 = new CompostosAlimentares(21, tipo, id2, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca3 = new CompostosAlimentares(21, tipo, id3, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        CompostosAlimentares ca4 = new CompostosAlimentares(21, tipo, id4, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);

        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca1);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca2);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca3);
        repositorioCompostosAlimentares.inserirCompostoAlimentar(ca4);

        realizaLogin();
        abrirMenu();
        clicarItemMenu(6);
        espera(500);
        clicarIconePesquisa();
    }

    @After
    public void deleteDados() {
        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-01: Buscar um composto alimentar informando o nome;
    public void buscarCompostoInformandoNomeTA1() throws Exception {

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText16.perform(replaceText("A1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_pesquisa), withText("A1"), isDisplayed()));
        appCompatEditText17.perform(pressImeActionButton());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaCompostosAlimentares),
                                childAtPosition(
                                        withId(R.id.resultado_busca_propriedades),
                                        2)),
                        0),
                        isDisplayed()));

        ViewInteraction text = onView(withId(R.id.lista_composto_nome));
        text.check(matches(withText("identificadorA1")));

    }

    @Test
    //TA-02: Buscar um composto alimentar sem informar o nome;
    public void buscarCompostoSemInformarNomeTA2() {

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText33.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.input_pesquisa), withText(""), isDisplayed()));
        appCompatEditText34.perform(pressImeActionButton());

        ViewInteraction linearLayout1 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaCompostosAlimentares),
                                childAtPosition(
                                        withId(R.id.resultado_busca_propriedades),
                                        2)),
                        0),
                        isDisplayed()));

        // Se o linearLayout é exibido, significa que houve retorno de dados na busca
        linearLayout1.check(matches(isDisplayed()));
    }


    @Test
    //TA-03: Buscar um composto alimentar que não esteja na base de dados.
    public void buscarCompostoInexistenteTA3() {

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText31.perform(replaceText("X1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_pesquisa), withText("X1"), isDisplayed()));
        appCompatEditText32.perform(pressImeActionButton());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaCompostosAlimentares),
                                childAtPosition(
                                        withId(R.id.resultado_busca_propriedades),
                                        2)),
                        0),
                        isDisplayed()));

        // o linearLayout não existe então não houve retorno na busca
        linearLayout2.check(doesNotExist());
    }
}

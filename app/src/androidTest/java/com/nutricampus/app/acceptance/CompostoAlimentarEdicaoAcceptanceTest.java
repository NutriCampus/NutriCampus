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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

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
    //TA-01: Atualizar o composto alimentar por um nome já cadastrado;
    public void atualizarCompostoPorNomeJaExistente_TA1() {
        onView(withText(id1)).perform(longClick());

        espera(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText1.perform(replaceText(id2), closeSoftKeyboard());

        clicarAtualizar();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Composto já cadastrado!");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    //TA-02: Atualizar o composto alimentar por um nome ainda não cadastrado;
    public void atualizarCompostoNormalmente_TA2() {

        onView(withText(id1)).perform(longClick());

        espera(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());

        espera(500);

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        espera(500);

        appCompatEditText2.perform(replaceText("identificador999"), closeSoftKeyboard());
        appCompatEditText3.perform(replaceText("999999"), closeSoftKeyboard());

        espera(500);

        clicarAtualizar();

        ViewInteraction text = onView(allOf(withId(R.id.lista_composto_nome), withText("identificador999")));
        text.check(matches(withText("identificador999")));

    }

    @Test
    //TA-03: Atualizar o composto alimentar por um nome vazio;
    public void atualizarCompostoPorNomeVazio_TA3() {

        onView(withText(id1)).perform(longClick());

        espera(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());

        clicarAtualizar();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    //TA-04: Atualizar o composto alimentar deixando um nutriente vazio;
    public void atualizarCompostoComNutrienteVazio_TA4() {
        onView(withText(id1)).perform(longClick());

        espera(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        clicarAtualizar();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    public void clicarAtualizar() {
        espera(500);
        ViewInteraction appCompatButton = onView(withId(R.id.btn_salvar_cadastro));
        appCompatButton.perform(scrollTo(), click());
    }

}

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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarCadastroAcceptanceTest extends AbstractPreparacaoTestes {
    private RepositorioCompostosAlimentares repositorioCompostosAlimentares;
    private String id1;

    @Before
    public void setUp() throws Exception {
        id1 = "identificador123";
        repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        realizaLogin();
        abrirMenu();
        clicarItemMenu(6);
        espera(500);

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton.perform(click());
    }

    @After
    public void deletaDados() {
        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-01: Cadastrar novos compostos alimentares sem informar seus nutrientes;
    public void cadastrarCompostoSemNutrientesTA1() {

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText5.perform(replaceText(id1), closeSoftKeyboard());
        espera();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        try {
            espera(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Preencha todos os campos");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    //TA-02: Cadastrar novos compostos alimentares informando todos os seus nutrientes;
    public void cadastrarCompostoComNutrientesTA2() {

        preencheCampos();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        try {
            espera(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Cadastro realizado com sucesso");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    //TA-03: Cadastrar novos compostos alimentares que já estejam na base de dados;
    public void cadastrarCompostoComIdentificadorJaExistenteTA3() {

        //Adicionando primeiro composto
        CompostosAlimentares compostosAlimentares =
                new CompostosAlimentares("22", id1, 11, 22, 33, 44, 55, 55, 66, 77, "descrição");
        repositorioCompostosAlimentares.inserirCompostoAlimentar(compostosAlimentares);

        preencheCampos();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        try {
            espera(500);
            new ToastMatcher().isToastMessageDisplayedWithText("Composto já cadastrado!");
            espera(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void preencheCampos() {

        ViewInteraction appCompatEditText01 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText01.perform(click());

        ViewInteraction appCompatEditText02 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText02.perform(replaceText(id1), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText6.perform(replaceText("22"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText7.perform(replaceText("11"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText8.perform(replaceText("22"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText9.perform(replaceText("33"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText10.perform(replaceText("44"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText11.perform(replaceText("55"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText12.perform(replaceText("55"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText14.perform(replaceText("66"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText15.perform(replaceText("77"), closeSoftKeyboard());
        espera();

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText16.perform(replaceText("descrição"), closeSoftKeyboard());
        espera();
    }
}

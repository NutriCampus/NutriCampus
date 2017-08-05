package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by kellison on 04/08/17.
 */


@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class PropriedadeCadastroAcceptanceTest extends AbstractPreparacaoTestes {

    @Before
    public void addProprietario() {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        if (repositorioProprietario.buscarProprietario("00011122222") == null)
            repositorioProprietario.inserirProprietario(new Proprietario("00011122222", "Proprietario 1", "", ""));
    }

    @After
    public void deletaPropriedades() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        Propriedade prop = repositorioPropriedade.buscarPropriedade("Propriedade OMEGA");

        if (prop != null)
            repositorioPropriedade.removerPropriedade(prop);

    }

    @Test
    public void cadastraComCamposEmBranco() throws Exception {
        prepararTeste();
        clicarMenuPropriedade();
        closeKeyboard();
        clicarFloatingButton();

        Thread.sleep(1200);

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());

        new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");

    }

    @Test
    public void cadastroCompleto() throws Exception {
        prepararTeste();
        clicarMenuPropriedade();
        closeKeyboard();
        clicarFloatingButton();

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        Thread.sleep(1000);

        appCompatSpinner.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Proprietario 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        Thread.sleep(1000);

        appCompatCheckedTextView.perform(click());
        closeKeyboard();

        preencheCampos();

        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());
        closeKeyboard();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Cadastro realizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cadastroSemProprietario() throws Exception {
        prepararTeste();
        clicarMenuPropriedade();
        closeKeyboard();
        clicarFloatingButton();


        preencheCampos();

        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());
        closeKeyboard();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void preencheCampos() throws Exception {

        ViewInteraction appCompatEditText13 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText13.perform(scrollTo(), replaceText("Propriedade OMEGA"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_telefone_propriedade)));
        appCompatEditText17.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText18 = onView(
                withId(R.id.input_rua));
        appCompatEditText18.perform(scrollTo(), replaceText("Rua da Independencia"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText19 = onView(
                withId(R.id.input_bairro));
        appCompatEditText19.perform(scrollTo(), replaceText("Mundaú"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText20.perform(replaceText("213"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText22.perform(replaceText("55290-000"), closeSoftKeyboard());
        closeKeyboard();
        onView(withId(R.id.input_estado))
                .perform(scrollTo())
                .perform(typeText("P"));

        onView(withId(R.id.input_estado))
                .perform(typeTextIntoFocusedView("e"));

        closeKeyboard();
        Thread.sleep(2000);
        onView(withText("Pernambuco"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.input_estado))
                .check(matches(withText("Pernambuco")));
        closeKeyboard();

        onView(withId(R.id.input_cidade))
                .perform(scrollTo())
                .perform(typeText("Ga"));

        onView(withId(R.id.input_cidade))
                .perform(typeTextIntoFocusedView("ran"));

        closeKeyboard();

        Thread.sleep(2000);
        onView(withText("Garanhuns"))
                .inRoot(isPlatformPopup())
                .perform(click());

        Thread.sleep(1000);
    }

    private void clicarMenuPropriedade() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

    }

    private void clicarFloatingButton() throws Exception {
        ViewInteraction floatingActionButton = onView(withId(R.id.fabList));
        floatingActionButton.perform(click());
        Thread.sleep(500);

        ViewInteraction floatingItem = onView(withId(R.id.fabPropriedade));
        floatingItem.perform(click());

        closeKeyboard();
    }

}


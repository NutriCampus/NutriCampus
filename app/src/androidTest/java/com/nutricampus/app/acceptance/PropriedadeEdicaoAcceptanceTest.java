package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class PropriedadeEdicaoAcceptanceTest extends AbstractPreparacaoTestes {

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        espera(500);

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());

        SharedPreferencesManager session = new SharedPreferencesManager(InstrumentationRegistry.getTargetContext());
        int id = session.getIdUsuario().equals("") ? 0 : Integer.parseInt(session.getIdUsuario());

        Proprietario proprietario = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com.com", "(99) 99999 9999");
        int idProprietario = repositorioProprietario.inserirProprietario(proprietario);

        Propriedade propriedade = new Propriedade("Propriedade 1", "87999999999", "Rua da Indepencia",
                "Mundaú", "55290-000", "Garanhuns", "Pernambuco", "213", idProprietario, id);

        if (repositorioPropriedade.buscarPropriedadesPorNome("Propriedade 1", id).size() == 0)
            repositorioPropriedade.inserirPropriedade(propriedade);

        abrirMenu();
        clicarItemMenu(3);
        closeKeyboard();
    }

    @Test
    public void tentaAtualizarCadastroApenasComProprietário() throws Exception {
        espera(1000);
        onView(withText("Propriedade 1"))
                .perform(longClick());

        espera(1200);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText13.perform(scrollTo(), replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_telefone_propriedade)));
        appCompatEditText17.perform(scrollTo(), replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText18 = onView(
                withId(R.id.input_rua));
        appCompatEditText18.perform(scrollTo(), replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText19 = onView(
                withId(R.id.input_bairro));
        appCompatEditText19.perform(scrollTo(), replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText20.perform(replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText22.perform(replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        onView(withId(R.id.input_estado))
                .perform(scrollTo())
                .perform(replaceText(" "));


        onView(withId(R.id.input_cidade))
                .perform(scrollTo())
                .perform(replaceText(" "));

        espera(1200);

        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Atualizar")));
        appCompatButton6.perform(scrollTo(), click());
        closeKeyboard();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void tentaAtualizarCadastroSemProprietario() throws Exception {
        espera(1000);
        onView(withText("Propriedade 1"))
                .perform(longClick());

        espera(1200);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        espera(1000);

        appCompatSpinner.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Selecione um proprietário"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                0),
                        isDisplayed()));
        espera(1000);

        appCompatCheckedTextView.perform(click());
        closeKeyboard();
        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Atualizar")));
        appCompatButton6.perform(scrollTo(), click());
        closeKeyboard();

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}


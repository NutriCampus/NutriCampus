package com.nutricampus.app.acceptance;

import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.core.IsInstanceOf;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by jorge on 04/08/17.
 */
@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioAtualizarActivityTest extends AbstractPreparacaoTestes {
    @Test
    public void atualizarProprietarioCamposVaziosActivityTest() throws Exception {

        doLogout();
        prepararTeste();
        //Inicialização de 10 proprietarios
        cadastrarProprietariosParaTeste();
        //Executa operações iniciais antes da busca
        prepararTesteBuscaProprietario();

        onView(withText("Jorge Veloso")).perform(longClick());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_fone_proprietario), isDisplayed()));
        appCompatEditText28.perform(replaceText("(87) 88888 9900"), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText29.perform(replaceText(""), closeSoftKeyboard());

        Thread.sleep(2000);
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton4.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText32.perform(replaceText("jvsveloso@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton5.perform(click());

        onView(withText("Jorge Veloso")).perform(longClick());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText40.perform(replaceText("049.985.174-00"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049.985.174-00"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("049.985.174-00")));

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText41.perform(replaceText("049.985.174-90"), closeSoftKeyboard());
        Thread.sleep(1000);
        posTeste();
    }

    @Test
    public void atualizarProprietarioCPFJaCadastradoActivityTest() throws Exception {

        doLogout();
        prepararTeste();
        cadastrarProprietariosParaTeste();
        prepararTesteBuscaProprietario();

        onView(withText("Jorge Veloso")).perform(longClick());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText40.perform(replaceText("010.925.525-96"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.btn_salvar_cadastro),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.telaCadastrarProprietarioActivity),
                                        0),
                                10),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        Thread.sleep(10000);
        posTeste();
    }

    private void posTeste() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
    }

    private void prepararTesteBuscaProprietario() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fabList),
                        withParent(allOf(withId(R.id.telaListaPropriedades),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fabProprietario),
                        withParent(allOf(withId(R.id.layoutProprietario),
                                withParent(withId(R.id.telaListaPropriedades)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView.perform(click());

    }

    private void cadastrarProprietariosParaTeste() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();

        Proprietario proprietarios[] = new Proprietario[5];
        int idProprietario[] = new int[5];

        proprietarios[0] = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com", "(87) 99999 9999");
        proprietarios[1] = new Proprietario("09915983425", "ProprietarioTeste1", "propteste1@gmail.com", "(88) 88888 8888");
        proprietarios[2] = new Proprietario("01092552596", "Lariza", "lariess@gmail.com.com", "(99) 99999 9999");
        proprietarios[3] = new Proprietario("10967434424", "ProprietarioTeste2", "propteste2@gmail.com", "(99) 99999 7777");
        proprietarios[4] = new Proprietario("10308591402", "ProprietarioTeste3", "propteste3@gmail.com", "(99) 99999 6666");

        for (int i = 0; i < 5; i++) {
            idProprietario[i] = repositorioProprietario.inserirProprietario(proprietarios[i]);
        }
    }

    public void prepararTeste() throws Exception {
        doLogout();
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

}
package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

/**
 * Created by jorge on 04/08/17.
 */
@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioAtualizarActivityTest extends AbstractPreparacaoTestes {

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        //Inicialização de proprietarios
        cadastrarProprietariosParaTeste();

        //Executa operações iniciais antes da busca
        abrirMenu();
        clicarItemMenu(3);
        clicarFloatingButton(R.id.fabList);
        clicarFloatingButton(R.id.fabProprietario);
    }

    @After
    public void deletaDadosPosTeste() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
    }


    @Test
    public void atualizarProprietarioCamposVaziosActivityTest() throws Exception {

        longClickElemento("Jorge Veloso");

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView.perform(click());


        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
        appCompatEditText28.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText2.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_fone_proprietario), isDisplayed()));
        appCompatEditText3.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText29.perform(replaceText(""), closeSoftKeyboard());

        espera(750);

        clicarBotao(R.id.btn_salvar_cadastro, false);

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void atualizarProprietarioCPFJaCadastradoActivityTest() throws Exception {

        longClickElemento("Jorge Veloso");
        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText40.perform(replaceText("010.925.525-96"), closeSoftKeyboard());

        clicarBotao(R.id.btn_salvar_cadastro, false);

       /* ViewInteraction button = onView(
                allOf(withId(R.id.btn_salvar_cadastro),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.telaCadastrarProprietarioActivity),
                                        0),
                                10),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        Thread.sleep(10000);*/

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("CPF já cadastrado !");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
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

}
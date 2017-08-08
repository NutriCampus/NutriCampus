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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioExcluirActivityTest extends AbstractPreparacaoTestes {

    private RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
    private RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        cadastrarProprietariosParaTeste();

        abrirMenu();
        clicarItemMenu(3);
        clicarFloatingButton(R.id.fabList);
        clicarFloatingButton(R.id.fabProprietario);
    }

    @Test
    public void excluirTodosProprietariosActivityTest() throws Exception {

        closeKeyboard();
        onView(withText("Jorge Veloso")).perform(longClick());
        closeKeyboard();
        ViewInteraction appCompatTextView1 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView1.perform(click());
        closeKeyboard();
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton2.perform(scrollTo(), click());
        closeKeyboard();

        onView(withText("ProprietarioTeste1")).perform(longClick());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView7.perform(click());
        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton10.perform(scrollTo(), click());

        espera(500);

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaProprietarios),
                                childAtPosition(
                                        withId(R.id.resultado_busca_proprietarios),
                                        2)),
                        0),
                        isDisplayed()));

        // o linearLayout não existe então não houve retorno na busca
        linearLayout.check(doesNotExist());
    }

    @Test
    public void excluirQuaseTodosProprietariosActivityTest() throws Exception {

        onView(withText("Jorge Veloso")).perform(longClick());
        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(android.R.id.message), withText("Tem certeza que deseja remover o(a) proprietário(a) \"Jorge Veloso\", isso excluirá as propriedades vinculados a ele(a)?"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView),
                                        0),
                                1),
                        isDisplayed()));
        textView10.check(matches(withText("Tem certeza que deseja remover o(a) proprietário(a) \"Jorge Veloso\", isso excluirá as propriedades vinculados a ele(a)?")));
        closeKeyboard();

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button2), withText("Não")));
        appCompatButton8.perform(scrollTo(), click());
        closeKeyboard();

        onView(withText("ProprietarioTeste1")).perform(longClick());
        closeKeyboard();
        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView7.perform(click());
        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton10.perform(scrollTo(), click());

        espera(1000);


        ViewInteraction text = onView(withId(R.id.lista_proprietario_nome));

        text.check(matches(withText("Jorge Veloso")));
    }

    @After
    public void deletaDadosPosTeste() {
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
    }


    private void cadastrarProprietariosParaTeste() {
        deletaDadosPosTeste();

        Proprietario proprietarios[] = new Proprietario[5];

        proprietarios[0] = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com", "(87) 99999 9999");
        proprietarios[1] = new Proprietario("09915983425", "ProprietarioTeste1", "propteste1@gmail.com", "(88) 88888 8888");

        for (int i = 0; i < 2; i++) {
            repositorioProprietario.inserirProprietario(proprietarios[i]);
        }
    }

}


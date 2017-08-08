package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

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

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioBuscarActivityTest extends AbstractPreparacaoTestes {

    private RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
    private RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());

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
        clicarIconePesquisa();
    }

    @Test
    public void buscaProprietarioSemInformarNome() throws Exception {

        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.resultado_busca_proprietarios),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));


        ViewInteraction text = onView(withId(R.id.text_quantidade_encontrados));

        text.check(matches(withText("5 registros encontrados")));
    }

    @Test
    public void buscaProprietarioPorNome() throws Exception {
        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText("Lariza"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();
        Thread.sleep(2000);

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.resultado_busca_proprietarios),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));

        ViewInteraction text = onView(withId(R.id.lista_proprietario_nome));

        text.check(matches(withText("Lariza")));
    }

    @Test
    public void buscaProprietarioNaoExiste() throws Exception {//Cadastro Total(Inserindo nova propriedade)

        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText("Vitor"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();
        Thread.sleep(2000);
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
        proprietarios[2] = new Proprietario("01092552596", "Lariza", "lariess@gmail.com.com", "(99) 99999 9999");
        proprietarios[3] = new Proprietario("10967434424", "ProprietarioTeste2", "propteste2@gmail.com", "(99) 99999 7777");
        proprietarios[4] = new Proprietario("10308591402", "ProprietarioTeste3", "propteste3@gmail.com", "(99) 99999 6666");

        for (int i = 0; i < 5; i++) {
            repositorioProprietario.inserirProprietario(proprietarios[i]);
        }
    }
}

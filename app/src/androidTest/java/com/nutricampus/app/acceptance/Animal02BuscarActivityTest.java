package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Animal;

import org.hamcrest.core.IsInstanceOf;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

/**
 * Created by jorge on 25/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Animal02BuscarActivityTest extends AbstractPreparacaoTestes {

    @Test
    public void buscarAnimalCadastrado() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        int id = repositorioPropriedade.buscarPropriedade("Propriedade 2").getId();

        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());
        Animal animal = repositorioAnimal.buscarAnimal("Mimosa", id);
        if (animal != null)
            repositorioAnimal.removerAnimal(animal);

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText6.perform(replaceText("Mim"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));

        Thread.sleep(1000);

        appCompatEditText7.perform(pressImeActionButton());

        try {
            Thread.sleep(500);

            onView(withText("Mimosa")).perform(click());
            pressBack();
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("Não existe essa view");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarComTermosDePesquisaEmBranco() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);

        Thread.sleep(500);
        ViewInteraction registrosEncontrados = onView(withId(R.id.text_quantidades_encontrados));
        registrosEncontrados.check(matches(withText("2 animais encontrados")));

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView5.perform(click());

        // Pesquisa por um animal não cadastrado para que o lsitview não exiba nenhum animal, e quando
        // presquisar sem termos de consulta, a lista com todos animais cadastrados apareça
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText6.perform(replaceText("xxx"), closeSoftKeyboard());

        appCompatEditText6.perform(pressImeActionButton());

        Thread.sleep(500);

        appCompatEditText6.perform(replaceText(""), closeSoftKeyboard());

        appCompatEditText6.perform(pressImeActionButton());

        Thread.sleep(500);
        ViewInteraction registrosEncontradosAposPesquisa = onView(withId(R.id.text_quantidades_encontrados));

        registrosEncontradosAposPesquisa.check(matches(withText("2 animais encontrados")));

    }

    @Test
    public void visualizarAnimaisPorPropriedade() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 2"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 2"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Propriedade 2")));


        Thread.sleep(500);
        ViewInteraction registrosEncontradosAposPesquisa = onView(withId(android.R.id.empty));

        registrosEncontradosAposPesquisa.check(matches(withText("Nenhum animal cadastrado")));

    }

    @Ignore // Não tá achando as propriedades
    @Test
    public void visualizarAnimaisPelaListaDePropriedades() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);

        Thread.sleep(500);
        onView(withText("Propriedade 1")).perform(longClick());
        Thread.sleep(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Visualizar animais"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Propriedade 1")));

        try {
            Thread.sleep(500);

            onView(withText("Mimosa")).perform(click());
            pressBack();
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("Não existe essa view");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void buscarAnimalNaoCadastrado() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);

        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView8.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText8.perform(replaceText("Manhosa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Manhosa"), isDisplayed()));
        appCompatEditText10.perform(pressImeActionButton());

        Thread.sleep(500);
        ViewInteraction registrosEncontradosAposPesquisa = onView(withId(android.R.id.empty));

        registrosEncontradosAposPesquisa.check(matches(withText("Nenhum animal cadastrado")));

    }

}

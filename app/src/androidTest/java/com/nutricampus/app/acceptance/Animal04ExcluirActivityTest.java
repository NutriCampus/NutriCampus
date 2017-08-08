package com.nutricampus.app.acceptance;

import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

/**
 * Created by jorge on 23/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Animal04ExcluirActivityTest extends AbstractPreparacaoTestes {

    @Test
    public void excluiAnimalCadastrado() throws Exception {//Excluir Animal ("Sim")
        doLogout();
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);
        espera(500);

        onView(withText("Flor")).perform(longClick());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton22.perform(scrollTo(), click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Animal removido com sucesso");
            espera(3500);
        } catch (Exception e) {
            fail("Toast de confirmação não identificado");
            e.printStackTrace();
        }
    }

}

package com.nutricampus.app.acceptance;

import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class Animal03AtualizarActivityTest extends AbstractPreparacaoTestes {

    @Test
    public void AnimalAtualizarActivityTest1() throws Exception {
        onView(withText("Flor")).perform(longClick());
        closeKeyboard();
        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(longClick());
        pressBack();
        Thread.sleep(1500);

        clicarBotao(R.id.btnConfimarDados, true);

        closeKeyboard();
        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText32.perform(replaceText("600"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText0 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText0.perform(replaceText("22/07/2017"), closeSoftKeyboard());
        closeKeyboard();
        closeKeyboard();
        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText34.perform(replaceText("1"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText35 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText35.perform(replaceText("1"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText36 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText36.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton11.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Procure uma propriedade")));
        closeKeyboard();
    }

    @Test
    public void AnimalAtualizarActivityTest2() throws Exception {//Cadastro Total(Inserindo nova propriedade)
        onView(withText("Flor")).perform(longClick());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                0),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());
        pressBack();

        clicarBotao(R.id.btnConfimarDados, true);

        closeKeyboard();

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Procure uma propriedade")));
    }

    @Test
    public void animalAtuaizarActivityTest3() throws Exception {
        onView(withText("Flor")).perform(longClick());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(click());
        closeKeyboard();
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 2"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                2),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());
        pressBack();

        clicarBotao(R.id.btnConfimarDados, true);

        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        appCompatButton10.perform(scrollTo(), click());

        closeKeyboard();

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Procure uma propriedade")));
    }

    @Test
    public void animalEditarActivityTest4() throws Exception {//Alterando animal para ativo ou inativo
        onView(withText("Flor")).perform(longClick());
        closeKeyboard();
        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(click());
        pressBack();
        onView(withId(R.id.switch_ativo)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.switch_ativo)).perform(click());
        Thread.sleep(2000);

        clicarBotao(R.id.btnConfimarDados, true);

        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        appCompatButton10.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Procure uma propriedade")));
    }

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);
    }

}
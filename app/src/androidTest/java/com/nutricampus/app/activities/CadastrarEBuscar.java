package com.nutricampus.app.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nutricampus.app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CadastrarEBuscar {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void cadastrarEBuscar() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(6, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText5.perform(replaceText("Aaaaa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText6.perform(replaceText("22"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText7.perform(replaceText("11"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText8.perform(replaceText("22"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText9.perform(replaceText("33"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText10.perform(replaceText("44"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText11.perform(replaceText("55"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText12.perform(replaceText("55"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.input_composto_pb), withText("55"), isDisplayed()));
        appCompatEditText13.perform(pressImeActionButton());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText14.perform(replaceText("66"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText15.perform(replaceText("77"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText16.perform(replaceText("descrição"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_composto_alimentar), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText17.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.input_composto_identificador), isDisplayed()));
        appCompatEditText18.perform(replaceText("Bbb"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.input_composto_tipo), isDisplayed()));
        appCompatEditText19.perform(replaceText("55"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_composto_ms), isDisplayed()));
        appCompatEditText20.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.input_composto_fdn), isDisplayed()));
        appCompatEditText21.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_composto_ee), isDisplayed()));
        appCompatEditText22.perform(replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_composto_mm), isDisplayed()));
        appCompatEditText23.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.input_composto_cnf), isDisplayed()));
        appCompatEditText24.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_composto_pb), isDisplayed()));
        appCompatEditText25.perform(replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_composto_ndt), isDisplayed()));
        appCompatEditText26.perform(replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.input_composto_fda), isDisplayed()));
        appCompatEditText27.perform(replaceText("8"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_composto_descricao), isDisplayed()));
        appCompatEditText28.perform(replaceText("ggggggg"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarcompostosalimentares),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText29.perform(replaceText("A"), closeSoftKeyboard());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.input_pesquisa), withText("A"), isDisplayed()));
        appCompatEditText30.perform(pressImeActionButton());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lista_composto_nome), withText("Aaaaa"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Aaaaa")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText31.perform(replaceText("Cccc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Cccc"), isDisplayed()));
        appCompatEditText32.perform(pressImeActionButton());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.empty), withText("Nenhum composto cadastrado"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_propriedades),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Nenhum composto cadastrado")));

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText33.perform(pressImeActionButton());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

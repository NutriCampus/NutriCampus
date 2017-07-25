package com.nutricampus.app.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nutricampus.app.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText4.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_senha), withText("admin"), isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(5, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText7.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox4.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText8.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText9.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton5.perform(scrollTo(), click());

    }

}

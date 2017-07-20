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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginUsuarioCadastradoActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginUsuarioCadastradoActivityTest() {
        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.rlayout_faca_login),
                        withParent(withId(R.id.tela_loginactivity)),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edtNome), isDisplayed()));
        appCompatEditText.perform(replaceText("Edu"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edtCpf), isDisplayed()));
        appCompatEditText2.perform(replaceText("09315359488"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edtRegistro), isDisplayed()));
        appCompatEditText6.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edtEmail), isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edtEmail), isDisplayed()));
        appCompatEditText8.perform(replaceText("a@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edtSenha), isDisplayed()));
        appCompatEditText9.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.edtSenha), isDisplayed()));
        appCompatEditText10.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.edtSenha), withText("123456"), isDisplayed()));
        appCompatEditText11.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText12.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText13.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText14.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.inicio), withText("Bem vindo ao NutriCampus!"),
                        childAtPosition(
                                allOf(withId(R.id.include),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Bem vindo ao NutriCampus!")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.inicio), withText("Bem vindo ao NutriCampus!"),
                        childAtPosition(
                                allOf(withId(R.id.include),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Bem vindo ao NutriCampus!")));

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

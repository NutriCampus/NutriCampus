package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;


@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
abstract class AbstractPreparacaoTestes {

    public Activity currentActivity;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    public static Matcher<View> childAtPosition(
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

    public void doLogout() {
        if (getActivityInstance() instanceof MainActivity) {
            new SharedPreferencesManager(mActivityTestRule.getActivity()).logoutUser();
            currentActivity.finish();
        }
    }

    public void realizaLogin() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            espera(2500);
        } else {

            doLogout();
            espera(500);

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

            espera(500);
        }
    }

    public void abrirMenu() throws InterruptedException {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        espera(500);
    }

    public void clicarItemMenu(int posicao) {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(posicao, click()));
    }

    public void clicarItemMenuComTexto(String texto) {
        onView(withText(texto)).perform(click());
    }

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

    public void closeKeyboard() {
        try {
            Espresso.closeSoftKeyboard();
            Thread.sleep(1300);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void espera() {
        espera(1500);
    }

    public void espera(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void validaToast(String mensagem) {
        try {
            new ToastMatcher().isToastMessageDisplayedWithText(mensagem);
            espera(4000);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Toast não identificado");
        }
    }


    public void clicarFloatingButton(int id) throws Exception {
        ViewInteraction floatingItem = onView(withId(id));
        floatingItem.perform(click());

        espera(600);
    }

    public void clicarIconePesquisa() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView.perform(click());
    }

    public void clicarBotao(int id, boolean scroll) {
        espera(500);
        ViewInteraction appCompatButton = onView(withId(id));

        if (scroll)
            appCompatButton.perform(scrollTo(), click());
        else
            appCompatButton.perform(click());

    }

    public void longClickElemento(String texto) {
        onView(withText(texto)).perform(longClick());
    }

}

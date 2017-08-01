package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by jorge on 25/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalBuscarActivityTest {
        private Activity currentActivity;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void animalBuscarActivityTest1() throws Exception {
        prepararTeste();
        clicarMenuAnimais();
        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText6.perform(replaceText("Mim"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Mimosa")));
    }
    @Test
    public void animalBuscarActivityTest2() throws Exception {

        prepararTeste();
        clicarMenuAnimais();

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        childAtPosition(
                                allOf(withId(R.id.telaListaAnimais),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withText("Animais"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Animais")));

    }
    @Test
    public void animalBuscarActivityTest3() throws Exception {
        prepararTeste();
        clicarMenuAnimais();
        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView7.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Propriedade 1")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Mimosa")));

    }
    @Test
    public void animalBuscarActivityTest4() throws Exception {
            prepararTeste();
            clicarMenuPropriedades();
            onView(withText("Propriedade 1")).perform(longClick());
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

            ViewInteraction textView6 = onView(
                    allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.ItensListaAnimal),
                                            0),
                                    0),
                            isDisplayed()));
            textView6.check(matches(withText("Mimosa")));

        }
    @Test
    public void animalBuscarActivityTest5() throws Exception {
                prepararTeste();
                clicarMenuAnimais();

        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView8.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText8.perform(replaceText("Manhosa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Manhosa"), isDisplayed()));
        appCompatEditText10.perform(pressImeActionButton());

        ViewInteraction textView7 = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("Procure uma propriedade")));
    }
    private void clicarMenuAnimais() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(5, click()));

    }
    private void clicarMenuPropriedades() {
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(3, click()));

    }
    public void prepararTeste()throws Exception{
        doLogout();
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
    public void doLogout() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            new SharedPreferencesManager(mActivityTestRule.getActivity()).logoutUser();
            currentActivity.finish();
        }
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
    public void closeKeyboard() throws Exception {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

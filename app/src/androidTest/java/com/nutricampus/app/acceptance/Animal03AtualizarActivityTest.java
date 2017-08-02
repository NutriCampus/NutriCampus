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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by jorge on 25/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Animal03AtualizarActivityTest {

    private Activity currentActivity;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void AnimalAtualizarActivityTest1() throws Exception {
        closeKeyboard();
        prepararTeste();
        closeKeyboard();
        onView(withText("Flor")).perform(longClick());
        closeKeyboard();
        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(longClick());
        pressBack();
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton9.perform(click());
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
        prepararTeste();
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
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton8.perform(click());
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
        prepararTeste();
        closeKeyboard();
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
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton8.perform(click());
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
        prepararTeste();

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
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton8.perform(click());
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

    public void prepararTeste()throws Exception{
        doLogout();
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());
        closeKeyboard();
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        closeKeyboard();
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));
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

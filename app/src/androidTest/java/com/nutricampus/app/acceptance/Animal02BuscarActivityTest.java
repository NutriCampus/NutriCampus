package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
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
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;

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
import static org.junit.Assert.fail;

/**
 * Created by jorge on 25/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Animal02BuscarActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Activity currentActivity;

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

    @Test
    public void buscarAnimalCadastrado() throws Exception {
        realizaLogin();
        clicarMenuAnimais();

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
        clicarMenuAnimais();

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
        clicarMenuAnimais();

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

    //@Ignore // Não tá achando as propriedades
    @Test
    public void visualizarAnimaisPelaListaDePropriedades() throws Exception {
        realizaLogin();
/*
        SharedPreferencesManager session = new SharedPreferencesManager(InstrumentationRegistry.getTargetContext());
        int id = session.getIdUsuario().equals("") ? 0 : Integer.parseInt(session.getIdUsuario());
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.buscarPropriedadesPorNome("Propriedade 1",id);
*/
        clicarMenuPropriedades();

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

        Thread.sleep(500);
        ViewInteraction registrosEncontradosAposPesquisa = onView(withId(android.R.id.empty));

        registrosEncontradosAposPesquisa.check(matches(withText("Nenhum animal cadastrado")));

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

    public void realizaLogin() throws Exception {
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

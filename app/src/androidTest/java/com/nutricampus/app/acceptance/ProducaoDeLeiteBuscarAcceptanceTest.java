package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
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
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
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
@LargeTest
@RunWith(AndroidJUnit4.class)
@Ignore
public class ProducaoDeLeiteBuscarAcceptanceTest {

    public Activity currentActivity = null;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

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

    private RepositorioProducaoDeLeite repositorioProducaoDeLeite;
    private ProducaoDeLeite producaoDeLeite1;
    private ProducaoDeLeite producaoDeLeite2;

    @Before
    public void preparaDados() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());

        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
        repositorioAnimal.removerTodos();

        String nome1 = "nomeAdmin proprietario",
                nome2 = "nomeAdmin proprietario";
        String email1 = "email@proprietario1.com",
                email2 = "email@proprietario2.com";
        String cpf1 = "000.000.000-000",
                cpf2 = "999.999.999-99";
        String prop1 = "propriedadeAdminUM",
                prop2 = "propriedadeAdminDOIS";
        String animal1 = "animalAdmin UM",
                animal2 = "animalAdmin DOIS";
        String tel1 = "(87) 00000 0000",
                tel2 = "(87) 99999 9999";
        String rua1 = "rua1",
                rua2 = "rua2";
        String bairro1 = "bairro1",
                bairro2 = "bairro2";
        String cep1 = "00000-000",
                cep2 = "99999-999";

        int idAnimal1;
        int idAnimal2;
        int idPropriedade1;
        int idPropriedade2;
        int idProprietario1 = 0;
        int idProprietario2 = 0;

        if (repositorioProprietario.buscarProprietario(cpf1) == null) {
            idProprietario1 = repositorioProprietario.inserirProprietario(new Proprietario(1, cpf1, nome1, email1, tel1));
            idProprietario2 = repositorioProprietario.inserirProprietario(new Proprietario(2, cpf2, nome2, email2, tel2));
        }
        if (repositorioPropriedade.buscarPropriedade(prop1) == null) {
            idPropriedade1 = repositorioPropriedade.inserirPropriedade(new Propriedade(1, prop1, tel1, rua1, bairro1, cep1, "Garanhuns", "Pernambuco", "000", idProprietario1, 1));
            idPropriedade2 = repositorioPropriedade.inserirPropriedade(new Propriedade(2, prop2, tel2, rua2, bairro2, cep2, "Caruaru", "Pernambuco", "999", idProprietario2, 1));
        } else {
            idPropriedade1 = repositorioPropriedade.buscarPropriedade(prop1).getId();
            idPropriedade2 = repositorioPropriedade.buscarPropriedade(prop2).getId();
        }


        RepositorioAnimal repoAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(InstrumentationRegistry.getTargetContext()).getIdUsuario() + "0");

        if (repoAnimal.buscarAnimal(animal1, idPropriedade1) == null) {

            idAnimal1 = repoAnimal.inserirAnimal(new Animal(1, animal1, idPropriedade1, Calendar.getInstance(), true, idUsuario));
            idAnimal2 = repoAnimal.inserirAnimal(new Animal(2, animal2, idPropriedade2, Calendar.getInstance(), true, idUsuario));

            RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(InstrumentationRegistry.getTargetContext());
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), idAnimal1, 100, 150, 50, 60, 5, true, true, true, true
            ));
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), idAnimal2, 100, 150, 50, 60, 5, true, true, true, true
            ));
        }
        idAnimal1 = repoAnimal.buscarAnimal(animal1, idPropriedade1).getId();
        Calendar calendarJunho = Calendar.getInstance();
        calendarJunho.set(2017, Calendar.JUNE, 1);
        Calendar calendarJulho = Calendar.getInstance();
        calendarJulho.set(2017, Calendar.JULY, 1);

        repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(InstrumentationRegistry.getTargetContext());
        producaoDeLeite1 = new ProducaoDeLeite(1, calendarJunho, idAnimal1, 99, 99, 99, 99, 99);
        producaoDeLeite2 = new ProducaoDeLeite(2, calendarJulho, idAnimal1, 88, 88, 88, 88, 88);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite2);
    }

    @After
    public void deletaDados() {
        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite2);
    }

    @Test
    public void producaoDeLeiteBuscarTA1() {

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());


        }
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
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("animalAdmin UM")).perform(longClick());
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Ver produção de leite"), isDisplayed()));
        appCompatTextView.perform(click());
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton9.perform(click());
        try {
            Thread.sleep(1000);
            onView(withText("01/07/2017")).perform(longClick());
            pressBack();
            // View is in hierarchy
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner5.perform(click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("Junho"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton10.perform(click());

        try {
            Thread.sleep(500);
            onView(withText("01/06/2017")).perform(click());
            pressBack();
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("Não existe essa view");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite1);
        //repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite2);
    }

    @Test
    public void producaoDeLeiteBuscarTA2() {

        if (getActivityInstance() instanceof MainActivity) {
            //dummy if
            try {
                Thread.sleep(4500);//toast de boas vindas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {

            ViewInteraction appCompatEditText = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText.perform(click());
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.input_usuario), isDisplayed()));
            appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_senha), isDisplayed()));
            appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
            appCompatButton.perform(click());

        }
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
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("animalAdmin UM"))
                .perform(longClick());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Ver produção de leite"), isDisplayed()));
        appCompatTextView.perform(click());
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("TODOS"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton9.perform(click());

        try {
            Thread.sleep(500);
            onView(withText("01/07/2017")).perform(longClick());
            pressBack();
            onView(withText("01/06/2017")).perform(longClick());
            pressBack();
            // View is in hierarchy
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

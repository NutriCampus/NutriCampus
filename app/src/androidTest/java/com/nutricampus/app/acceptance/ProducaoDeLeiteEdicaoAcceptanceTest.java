package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProducaoDeLeiteEdicaoAcceptanceTest {
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


    @Test
    public void producaoDeLeiteEdicaoTA1() {

        Calendar calendarJunho = Calendar.getInstance();
        calendarJunho.set(2017, Calendar.JUNE, 4);
        Calendar calendarJulho = Calendar.getInstance();
        calendarJulho.set(2017, Calendar.JULY, 4);
        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(InstrumentationRegistry.getTargetContext());
        ProducaoDeLeite producaoDeLeite1 = new ProducaoDeLeite(1, calendarJunho, 1, 99, 99, 99, 99, 99);
        ProducaoDeLeite producaoDeLeite2 = new ProducaoDeLeite(2, calendarJulho, 1, 88, 88, 88, 88, 88);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite2);

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
                allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton10.perform(click());


        onView(withText("04/07/2017"))//Peso 99,00 kg
                .perform(longClick());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.input_quantidade_leite), isDisplayed()));
        appCompatEditText21.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_lactose), isDisplayed()));
        appCompatEditText22.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_gordura), isDisplayed()));
        appCompatEditText23.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_proteina_bruta), isDisplayed()));
        appCompatEditText25.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_proteina_verdadeira), isDisplayed()));
        appCompatEditText26.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_producao), withText("Atualizar")));
        appCompatButton4.perform(scrollTo(), click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite2);
    }

    @Test
    public void producaoDeLeiteEdicaoTA2() {

        Calendar calendarJunho = Calendar.getInstance();
        calendarJunho.set(2017, Calendar.JUNE, 5);
        Calendar calendarJulho = Calendar.getInstance();
        calendarJulho.set(2017, Calendar.JULY, 5);
        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(InstrumentationRegistry.getTargetContext());
        ProducaoDeLeite producaoDeLeite1 = new ProducaoDeLeite(1, calendarJunho, 1, 99, 99, 99, 99, 99);
        ProducaoDeLeite producaoDeLeite2 = new ProducaoDeLeite(2, calendarJulho, 1, 88, 88, 88, 88, 88);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite2);

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
                allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton10.perform(click());


        onView(withText("05/07/2017"))//Peso 99,00 kg
                .perform(longClick());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_producao), withText("Atualizar")));
        appCompatButton4.perform(scrollTo(), click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Registro atualizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite2);
    }

    @Test
    public void producaoDeLeiteEdicaoTA3() {

        Calendar calendarJunho = Calendar.getInstance();
        calendarJunho.set(2017, Calendar.JUNE, 2);
        Calendar calendarJulho = Calendar.getInstance();
        calendarJulho.set(2017, Calendar.JULY, 2);
        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(InstrumentationRegistry.getTargetContext());
        ProducaoDeLeite producaoDeLeite1 = new ProducaoDeLeite(1, calendarJunho, 1, 99, 99, 99, 99, 99);
        ProducaoDeLeite producaoDeLeite2 = new ProducaoDeLeite(2, calendarJulho, 1, 88, 88, 88, 88, 88);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite2);

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
                allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_filtrar_producao), isDisplayed()));
        appCompatButton10.perform(click());


        onView(withText("02/07/2017"))//Peso 99,00 kg
                .perform(longClick());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.input_quantidade_leite), isDisplayed()));
        appCompatEditText21.perform(replaceText("1200"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_lactose), isDisplayed()));
        appCompatEditText22.perform(replaceText("100"), closeSoftKeyboard());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_gordura), isDisplayed()));
        appCompatEditText23.perform(replaceText("100"), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_proteina_bruta), isDisplayed()));
        appCompatEditText25.perform(replaceText("100"), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_proteina_verdadeira), isDisplayed()));
        appCompatEditText26.perform(replaceText("100"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_producao), withText("Atualizar")));
        appCompatButton4.perform(scrollTo(), click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Registro atualizado com sucesso");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.removerProducaoDeLeite(producaoDeLeite2);
    }


}

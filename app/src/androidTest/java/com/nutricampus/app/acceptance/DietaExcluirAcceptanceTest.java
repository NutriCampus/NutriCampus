package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioDieta;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Dieta;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

//@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@LargeTest
@RunWith(AndroidJUnit4.class)
public class DietaExcluirAcceptanceTest {

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
    public void dietaExclusao_TA1() {
        //Context targetContext = InstrumentationRegistry.getTargetContext();
        //RepositorioUsuario repo = new RepositorioUsuario(targetContext);
        RepositorioProprietario REPproprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        RepositorioPropriedade REPpropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioAnimal REPAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());
        RepositorioCompostosAlimentares REPCompAliment = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());
        RepositorioDadosComplAnimal REPdados = new RepositorioDadosComplAnimal(InstrumentationRegistry.getTargetContext());
        RepositorioGrupo REPgrupo = new RepositorioGrupo(InstrumentationRegistry.getTargetContext());
        RepositorioDieta REPdieta = new RepositorioDieta(InstrumentationRegistry.getTargetContext());

        /*Usuario usuario = new Usuario(2, "adminteste", "", "adminteste", "admin@mail.com", "adminteste");
        repo.inserirUsuario(usuario);*/
        Proprietario proprietario1 = new Proprietario(1, "111.111.111-11", "Proprietario1", "email1", "1111");
        REPproprietario.inserirProprietario(proprietario1);
        Propriedade propriedade1 = new Propriedade(1, "Propriedade1", "1111-1111", "Rua 111", "Bairo111", "Cep111", "Garanhuns", "Pernambuco", "11.111", 1, 1);
        REPpropriedade.inserirPropriedade(propriedade1);
        Animal animal1 = new Animal(1, "vaca1", 1, Calendar.getInstance(), true, 1);
        Animal animal2 = new Animal(2, "vaca2", 1, Calendar.getInstance(), true, 1);
        REPAnimal.inserirAnimal(animal1);
        REPAnimal.inserirAnimal(animal2);
        DadosComplAnimal dadosComplAnimal1 = new DadosComplAnimal(1, Calendar.getInstance(), 1, 1000, 1, 10, 100, 4, 1);
        DadosComplAnimal dadosComplAnimal2 = new DadosComplAnimal(2, Calendar.getInstance(), 2, 2000, 1, 20, 200, 4, 1);
        REPdados.inserirDadosComplAnimal(dadosComplAnimal1);
        REPdados.inserirDadosComplAnimal(dadosComplAnimal2);
        CompostosAlimentares compostosAlimentares1 = new CompostosAlimentares(1, "tipo1", "Farelo Soja", 1, 1, 1, 1, 1, 46.0, 1, 1, "descricao1");
        CompostosAlimentares compostosAlimentares2 = new CompostosAlimentares(2, "tipo2", "Fubá", 1, 1, 1, 1, 1, 9.0, 1, 1, "descricao2");
        CompostosAlimentares compostosAlimentares3 = new CompostosAlimentares(3, "tipo3", "Farinha de Trigo", 1, 1, 1, 1, 1, 16.0, 1, 1, "descricao3");
        CompostosAlimentares compostosAlimentares4 = new CompostosAlimentares(4, "tipo4", "Cama de Frango", 1, 1, 1, 1, 1, 25.0, 1, 1, "descricao4");
        REPCompAliment.inserirCompostoAlimentar(compostosAlimentares1);
        REPCompAliment.inserirCompostoAlimentar(compostosAlimentares2);
        REPCompAliment.inserirCompostoAlimentar(compostosAlimentares3);
        REPCompAliment.inserirCompostoAlimentar(compostosAlimentares4);
        Grupo grupo1 = new Grupo(1, "Pastando", "", 1);
        Grupo grupo2 = new Grupo(2, "Lactação", "", 1);
        Grupo grupo3 = new Grupo(3, "Cio", "", 1);
        Grupo grupo4 = new Grupo(4, "Gestante", "", 1);
        REPgrupo.inserirGrupo(grupo1);
        REPgrupo.inserirGrupo(grupo2);
        REPgrupo.inserirGrupo(grupo3);
        REPgrupo.inserirGrupo(grupo4);

        ArrayList<Animal> animais = new ArrayList<>();
        animais.add(animal1);
        animais.add(animal2);
        ArrayList<CompostosAlimentares> compostos = new ArrayList<>();
        compostos.add(compostosAlimentares1);
        compostos.add(compostosAlimentares2);
        Dieta dieta = new Dieta("Rrr", 20, animais, compostos);
        dieta.propriedade = propriedade1;
        REPdieta.inserirDieta(dieta);


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

            /*ViewInteraction textView = onView(
                    allOf(withText("NutriCampus"),
                            childAtPosition(
                                    allOf(withId(R.id.toolbar),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    0)),
                                    1),
                            isDisplayed()));
            textView.check(matches(withText("NutriCampus")));*/

        }
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
        recyclerView.perform(actionOnItemAtPosition(9, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.listaDietas),
                                withParent(withId(R.id.resultado_busca_propriedades))),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        textView.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton10.perform(scrollTo(), click());

        //repo.removerUsuario(usuario);
        REPproprietario.removerProprietario(proprietario1);
        REPpropriedade.removerPropriedade(propriedade1);
        REPAnimal.removerAnimal(animal1);
        REPAnimal.removerAnimal(animal2);
        REPdados.removerDadosCompl(dadosComplAnimal1);
        REPdados.removerDadosCompl(dadosComplAnimal2);
        REPCompAliment.removerCompostoAlimentar(compostosAlimentares1);
        REPCompAliment.removerCompostoAlimentar(compostosAlimentares2);
        REPCompAliment.removerCompostoAlimentar(compostosAlimentares3);
        REPCompAliment.removerCompostoAlimentar(compostosAlimentares4);
        REPgrupo.removerGrupo(grupo1);
        REPgrupo.removerGrupo(grupo2);
        REPgrupo.removerGrupo(grupo3);
        REPgrupo.removerGrupo(grupo4);
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

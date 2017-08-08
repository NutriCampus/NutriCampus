package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.core.IsInstanceOf;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
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

/**
 * Created by jorge on 04/08/17.
 */
@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioBuscarActivityTest extends AbstractPreparacaoTestes {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void proprietarioBuscaProprietarioCadastradoActivityTest() throws Exception {
        doLogout();
        prepararTeste();
        //Inicialização de proprietarios
        cadastrarProprietariosParaTeste();
        //Executa operações iniciais antes da busca
        prepararTesteBuscaProprietario();

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText40.perform(replaceText("Jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());
//        Thread.sleep(10000);
//        ViewInteraction textView7 = onView(
//                allOf(withId(R.id.lista_proprietario_nome), withText("Jorge Veloso"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.ItensListaProprietarios),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textView7.check(matches(withText("Jorge Veloso")));
        Thread.sleep(10000);
        closeKeyboard();

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.resultado_busca_propriedades),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));
        posTeste();
        Thread.sleep(1000);
    }

    @Test
    public void proprietarioBuscaProprietarioSemInformarNomeActivityTest() throws Exception {
        doLogout();
        prepararTeste();
        //Inicialização de proprietarios
        cadastrarProprietariosParaTeste();
        prepararTesteBuscaProprietario();
        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();
//        ViewInteraction editText5 = onView(
//                allOf(withId(R.id.input_pesquisa),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        editText5.check(matches(withText("")));

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.resultado_busca_propriedades),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));
        Thread.sleep(1000);
//        linearLayout3.check(matches(withText("9 registros encontrados")));
//        Thread.sleep(1000);
        posTeste();
        doLogout();
    }

    @Test
    public void proprietarioBuscaProprietarioPorNomeActivityTest() throws Exception {
        doLogout();
        prepararTeste();

        //Inicialização de proprietarios
        cadastrarProprietariosParaTeste();
        prepararTesteBuscaProprietario();
        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText("Lariza"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();
        Thread.sleep(5000);

//        ViewInteraction editText5 = onView(
//                allOf(withId(R.id.input_pesquisa),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        editText5.check(matches(withText("Lariza")));

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.resultado_busca_propriedades),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));
        posTeste();
        Thread.sleep(1000);
        doLogout();
    }

    @Test
    public void proprietarioBuscaProprietarioNaoExisteActvityTest() throws Exception {//Cadastro Total(Inserindo nova propriedade)
        doLogout();
        prepararTeste();
        cadastrarProprietariosParaTeste();
        prepararTesteBuscaProprietario();
        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText42.perform(replaceText("Vitor"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        closeKeyboard();
        Thread.sleep(5000);
//
//        ViewInteraction textView9 = onView(
//                allOf(withId(android.R.id.empty), withText("Nenhum proprietário cadastrado"),
//                        childAtPosition(
//                                allOf(withId(R.id.resultado_busca_propriedades),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
//                                                0)),
//                                0),
//                        isDisplayed()));
//        textView9.check(matches(withText("Nenhum proprietário cadastrado")));

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.resultado_busca_propriedades),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));
        Thread.sleep(1000);
    }

    private void posTeste() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
    }

    private void prepararTesteBuscaProprietario() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fabList),
                        withParent(allOf(withId(R.id.telaListaPropriedades),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fabProprietario),
                        withParent(allOf(withId(R.id.layoutProprietario),
                                withParent(withId(R.id.telaListaPropriedades)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView.perform(click());

    }

    private void cadastrarProprietariosParaTeste() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();

        Proprietario proprietarios[] = new Proprietario[5];
        int idProprietario[] = new int[5];

        proprietarios[0] = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com", "(87) 99999 9999");
        proprietarios[1] = new Proprietario("09915983425", "ProprietarioTeste1", "propteste1@gmail.com", "(88) 88888 8888");
        proprietarios[2] = new Proprietario("01092552596", "Lariza", "lariess@gmail.com.com", "(99) 99999 9999");
        proprietarios[3] = new Proprietario("10967434424", "ProprietarioTeste2", "propteste2@gmail.com", "(99) 99999 7777");
        proprietarios[4] = new Proprietario("10308591402", "ProprietarioTeste3", "propteste3@gmail.com", "(99) 99999 6666");

        for (int i = 0; i < 5; i++) {
            idProprietario[i] = repositorioProprietario.inserirProprietario(proprietarios[i]);
        }
    }

    public void prepararTeste() throws Exception {
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
//
//    public void doLogout() throws Exception {
//        if (getActivityInstance() instanceof MainActivity) {
//            new SharedPreferencesManager(mActivityTestRule.getActivity()).logoutUser();
//            currentActivity.finish();
//        }
//    }
//    public Activity getActivityInstance() {
//        getInstrumentation().runOnMainSync(new Runnable() {
//            public void run() {
//                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
//                if (resumedActivities.iterator().hasNext()) {
//                    currentActivity = (Activity) resumedActivities.iterator().next();
//                }
//            }
//        });
//
//        return currentActivity;
//    }
//    public void closeKeyboard() throws Exception {
//        try {
//            Thread.sleep(1000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    protected static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
}

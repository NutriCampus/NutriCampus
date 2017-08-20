package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
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
import com.nutricampus.app.database.SharedPreferencesManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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


@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class ProleActivityTest {
    private Activity currentActivity;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void cadastroActivityTest() throws Exception {//CADASTRO DE DIVERSOS DE FORMAS DIFERENTES

        prepararTeste();
        addAnimal();
        closeKeyboard();
        onView(withText("Florinda")).perform(longClick());
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView.perform(click());

//ADD PROLE 01
        closeKeyboard();
        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton5.perform(click());
        closeKeyboard();

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText40.perform(replaceText("30"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText55.perform(replaceText("25/07/2017"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton15.perform(click());
        closeKeyboard();
//ADD PROLE 02
        ViewInteraction floatingActionButton10 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton10.perform(click());
        closeKeyboard();
        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());
        closeKeyboard();
        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton14.perform(click());

//ADD PROLE 03
        closeKeyboard();
        ViewInteraction floatingActionButton55 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton55.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText9.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText4.perform(replaceText("30"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText37 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText37.perform(replaceText("25/07/2017"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton17.perform(click());


        //ADD PROLE 04
        closeKeyboard();
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText.perform(replaceText("30"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText1.perform(replaceText("25/07/2017"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton18.perform(click());

        //ADD PROLE 05
        closeKeyboard();
        ViewInteraction floatingActionButton1 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton1.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText42.perform(replaceText("25"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText2.perform(replaceText("25/06/2017"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton8.perform(click());
    }

    @Test
    public void atualizaActivityTest() throws Exception {
        prepararTeste();
        closeKeyboard();
        onView(withText("Florinda")).perform(longClick());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView6.perform(click());

        //Atualiza situação e insere peso
        onView(withText("Natimorto")).perform(longClick());

        ViewInteraction appCompatTextView10 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView10.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());
        closeKeyboard();

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText4.perform(replaceText("45"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText12.perform(replaceText("12/06/2017"), closeSoftKeyboard());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton17.perform(click());

        //Atualiza situação e considera natimorto

        onView(withText("Peso: 45.00 kg")).perform(longClick());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView7.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton11.perform(click());

    }

    @Test
    public void buscarActivityTest() throws Exception {
        prepararTeste();
        closeKeyboard();
        onView(withText("Florinda")).perform(longClick());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
        appCompatButton19.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(android.R.id.text1), withText("Julho"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_meses),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("Julho")));

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner6.perform(click());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.text1), withText("Junho"), isDisplayed()));
        appCompatTextView7.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
        appCompatButton22.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(android.R.id.text1), withText("Junho"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_meses),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("Junho")));

    }

    @Test
    public void excluirActivityTest() throws Exception {
        prepararTeste();
        closeKeyboard();

        onView(withText("Florinda")).perform(longClick());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView7.perform(click());

        //Atualiza situação e insere peso
        onView(withText("Peso: 25.00 kg")).perform(longClick());

        ViewInteraction appCompatTextView10 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView10.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton22.perform(scrollTo(), click());

        //Atualiza situação e considera natimorto

        onView(withText("Natimorto")).perform(longClick());

        ViewInteraction appCompatTextView9 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView9.perform(click());

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton33.perform(scrollTo(), click());
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

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));
    }

    public void addAnimal() throws Exception {
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText23.perform(replaceText("Florinda"), closeSoftKeyboard());

        closeKeyboard();
        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText55.perform(replaceText("17/07/2013"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton8.perform(click());
        closeKeyboard();

        ViewInteraction editText = onView(
                allOf(withId(R.id.input_peso_vivo),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("")));
        closeKeyboard();
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.input_semana_lactacao),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("")));
        closeKeyboard();
        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText25.perform(replaceText("500"), closeSoftKeyboard());
        closeSoftKeyboard();
        ViewInteraction appCompatEditText01 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText01.perform(replaceText("22/07/2017"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText27.perform(replaceText("1"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText28.perform(replaceText("1"), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText29 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText29.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());
        closeKeyboard();

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        appCompatButton10.perform(scrollTo(), click());
        closeKeyboard();
//
//                ViewInteraction textView4 = onView(
//                        allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
//                                childAtPosition(
//                                        allOf(withId(R.id.spinnerPropriedade),
//                                                childAtPosition(
//                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                                        0)),
//                                        0),
//                                isDisplayed()));
//                textView4.check(matches(withText("Procure uma propriedade")));
//                fecharTeclado();

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
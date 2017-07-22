package com.nutricampus.app.acceptance;


import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.test.suitebuilder.annotation.LargeTest;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalCadastroActivityTest {
    private Activity currentActivity;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void animalCadastroActivityTest1() throws Exception {//Cadastro Total(Inserindo nova propriedade)
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

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_add_propriedade), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                withId(R.id.btn_add_proprietario));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
        appCompatEditText3.perform(replaceText("Jorge Veloso"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText7.perform(replaceText("04998517490"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText8.perform(replaceText("jvsveloso@gmail.com.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_fone_proprietario),isDisplayed()));
        appCompatEditText12.perform(replaceText("(99) 99999 9999"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarproprietarioactvity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton5.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        appCompatSpinner.perform(scrollTo(), click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Jorge Veloso"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView.perform(click());
        closeKeyboard();

        ViewInteraction appCompatEditText13 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText13.perform(scrollTo(), replaceText("Propriedade 1"), closeSoftKeyboard());

       ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_telefone_propriedade)));
        appCompatEditText17.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                withId(R.id.input_rua));
        appCompatEditText18.perform(scrollTo(), replaceText("Rua da Independencia"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                withId(R.id.input_bairro));
        appCompatEditText19.perform(scrollTo(), replaceText("Mundaú"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText20.perform(replaceText("213"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText22.perform(replaceText("55290-000"), closeSoftKeyboard());

        onView(withId(R.id.input_estado))
                .perform(scrollTo())
                .perform(typeText("P"));

        onView(withId(R.id.input_estado))
                .perform(typeTextIntoFocusedView("e"));

        closeKeyboard();
        onView(withText("Pernambuco"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.input_estado))
                .check(matches(withText("Pernambuco")));
            closeKeyboard();
        onView(withId(R.id.input_cidade))
                .perform(scrollTo())
                .perform(typeText("Ga"));

        onView(withId(R.id.input_cidade))
                .perform(typeTextIntoFocusedView("ran"));

        closeKeyboard();
        onView(withText("Garanhuns"))
                .inRoot(isPlatformPopup())
                .perform(click());

        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(5, click()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText23.perform(replaceText("Mimosa"), closeSoftKeyboard());


        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText55.perform(replaceText("17/07/2017"), closeSoftKeyboard());

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
        appCompatEditText25.perform(replaceText("600"), closeSoftKeyboard());
        closeSoftKeyboard();
        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText26.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText27.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText28.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText29.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        appCompatButton10.perform(scrollTo(), click());

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
    public void animalCadastroActivityTest2() throws Exception {//Cadastro selecionando propriedade existente

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

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText23.perform(replaceText("Flor"), closeSoftKeyboard());


        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText55.perform(replaceText("17/07/2013"), closeSoftKeyboard());

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
        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText26.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText27.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText28.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText29.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        appCompatButton10.perform(scrollTo(), click());

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
/*
    @Test
    public void animalCadastroActivityTest() {
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

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_add_propriedade), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        appCompatSpinner.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                withId(R.id.btn_add_proprietario));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
        appCompatEditText3.perform(replaceText("Jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText4.perform(replaceText("049"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049."), isDisplayed()));
        appCompatEditText5.perform(replaceText("049.985"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049.985."), isDisplayed()));
        appCompatEditText6.perform(replaceText("049.985.174"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049.985.174-"), isDisplayed()));
        appCompatEditText7.perform(replaceText("049.985.174-90"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText8.perform(replaceText("a@g.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_fone_proprietario), isDisplayed()));
        appCompatEditText9.perform(replaceText("9"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(9"), isDisplayed()));
        appCompatEditText10.perform(replaceText("(99"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(99) "), isDisplayed()));
        appCompatEditText11.perform(replaceText("(99) 99999"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(99) 99999 "), isDisplayed()));
        appCompatEditText12.perform(replaceText("(99) 99999 9999"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarproprietarioactvity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner2 = onView(
                withId(R.id.spinner_proprietario));
        appCompatSpinner2.perform(scrollTo(), click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Jorge"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText13.perform(scrollTo(), replaceText("JoProp"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                withId(R.id.input_telefone_propriedade));
        appCompatEditText14.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(8")));
        appCompatEditText15.perform(scrollTo(), replaceText("(87"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) ")));
        appCompatEditText16.perform(scrollTo(), replaceText("(87) 87878"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) 87878 ")));
        appCompatEditText17.perform(scrollTo(), replaceText("(87) 87878 7878"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                withId(R.id.input_rua));
        appCompatEditText18.perform(scrollTo(), replaceText("I"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                withId(R.id.input_bairro));
        appCompatEditText19.perform(scrollTo(), replaceText("Rua"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText20.perform(replaceText("213"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText21.perform(replaceText("55290"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_cep), withText("55290-"), isDisplayed()));
        appCompatEditText22.perform(replaceText("55290-000"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                withId(R.id.input_estado));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("Pe"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                withId(R.id.input_cidade));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gar"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Garanhuns"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(5, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("JoProp"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText23.perform(replaceText("Mimosa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText24.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.input_peso_vivo),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.input_semana_lactacao),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("")));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("EEC"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        6),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("EEC")));

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText25.perform(replaceText("600"), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText26.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText27.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText28.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText29 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText29.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton10.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Mimosa")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.text_quantidades_encontrados), withText("1 animal"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("1 animal")));

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

        ViewInteraction textView5 = onView(
                allOf(withText("Animais"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("Animais")));

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner5.perform(click());

        ViewInteraction checkedTextView = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction checkedTextView2 = onView(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        checkedTextView2.check(matches(isDisplayed()));

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("JoProp"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner6.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("JoProp"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText30.perform(replaceText("Flor"), closeSoftKeyboard());

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText31.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton11.perform(scrollTo(), click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText32.perform(replaceText("500"), closeSoftKeyboard());

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText33.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton13.perform(scrollTo(), click());

        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText34.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText35 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText35.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText36 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText36.perform(scrollTo(), replaceText("0"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.ckb_pastando), withText("Pastando")));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.rb2), withText("2"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton14.perform(scrollTo(), click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Flor"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Flor")));

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

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.text_quantidades_encontrados), withText("2 animais"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("2 animais")));

        ViewInteraction textView9 = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        textView9.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.btn_add_prole),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction appCompatEditText37 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText37.perform(replaceText("40"), closeSoftKeyboard());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.lista_prole_peso), withText("Peso: 40.00 kg"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView10.check(matches(withText("Peso: 40.00 kg")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.text_quantidade_encontrados), withText("1 registro encontrado"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_prole),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("1 registro encontrado")));

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction appCompatEditText38 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText38.perform(replaceText("50"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.check_natimorto),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.tela_cadastro_prole),
                                                0)),
                                0),
                        isDisplayed()));
        checkBox.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("0")));

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.lista_prole_data), withText("21/07/2017"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("21/07/2017")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.lista_prole_peso), withText("Natimorto"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView13.check(matches(withText("Natimorto")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.text_quantidades_encontrados), withText("2 animais"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView14.check(matches(withText("2 animais")));

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btn_add_propriedade), isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction appCompatSpinner7 = onView(
                withId(R.id.spinner_proprietario));
        appCompatSpinner7.perform(scrollTo(), click());

        ViewInteraction appCompatCheckedTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("Jorge"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView5.perform(click());

        ViewInteraction appCompatEditText39 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText39.perform(scrollTo(), replaceText("Prop2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText40 = onView(
                withId(R.id.input_telefone_propriedade));
        appCompatEditText40.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(8")));
        appCompatEditText41.perform(scrollTo(), replaceText("(87"), closeSoftKeyboard());

        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) ")));
        appCompatEditText42.perform(scrollTo(), replaceText("(87) 87878"), closeSoftKeyboard());

        ViewInteraction appCompatEditText43 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) 87878 ")));
        appCompatEditText43.perform(scrollTo(), replaceText("(87) 87878 7878"), closeSoftKeyboard());

        ViewInteraction appCompatEditText44 = onView(
                withId(R.id.input_rua));
        appCompatEditText44.perform(scrollTo(), replaceText("U"), closeSoftKeyboard());

        ViewInteraction appCompatEditText45 = onView(
                withId(R.id.input_bairro));
        appCompatEditText45.perform(scrollTo(), replaceText("Hel"), closeSoftKeyboard());

        ViewInteraction appCompatEditText46 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText46.perform(replaceText("34"), closeSoftKeyboard());

        ViewInteraction appCompatEditText47 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText47.perform(replaceText("55555"), closeSoftKeyboard());

        ViewInteraction appCompatEditText48 = onView(
                allOf(withId(R.id.input_cep), withText("55555-"), isDisplayed()));
        appCompatEditText48.perform(replaceText("55555-5555"), closeSoftKeyboard());

        ViewInteraction appCompatEditText49 = onView(
                allOf(withId(R.id.input_cep), withText("55555-555"), isDisplayed()));
        appCompatEditText49.perform(replaceText("55555-5555"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                withId(R.id.input_estado));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("Pe"), closeSoftKeyboard());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatAutoCompleteTextView4 = onView(
                withId(R.id.input_cidade));
        appCompatAutoCompleteTextView4.perform(scrollTo(), replaceText("Gara"), closeSoftKeyboard());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Garanhuns"), isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton18.perform(scrollTo(), click());

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.lista_propriedade_nome), withText("Prop2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView15.check(matches(withText("Prop2")));

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(5, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction appCompatSpinner8 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner8.perform(click());

        ViewInteraction appCompatCheckedTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("Prop2"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                2),
                        isDisplayed()));
        appCompatCheckedTextView6.perform(click());

        ViewInteraction appCompatEditText50 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText50.perform(replaceText("Mimosa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText51 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText51.perform(click());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton19.perform(scrollTo(), click());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton20.perform(click());

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.rb1),
                        childAtPosition(
                                allOf(withId(R.id.rgEec),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        radioButton.check(matches(isDisplayed()));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.input_peso_vivo),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("")));

        ViewInteraction appCompatEditText52 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText52.perform(replaceText("550"), closeSoftKeyboard());

        ViewInteraction appCompatEditText53 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText53.perform(click());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton21.perform(scrollTo(), click());

        ViewInteraction appCompatEditText54 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText54.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText55.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText56 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText56.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox4.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.rb3), withText("3"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton22.perform(scrollTo(), click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.text_quantidades_encontrados), withText("3 animais"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView16.check(matches(withText("3 animais")));

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView17.check(matches(withText("Mimosa")));

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Mimosa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView18.check(matches(withText("Mimosa")));

        ViewInteraction appCompatSpinner9 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner9.perform(click());

        ViewInteraction appCompatCheckedTextView7 = onView(
                allOf(withId(android.R.id.text1), withText("JoProp"), isDisplayed()));
        appCompatCheckedTextView7.perform(click());

        ViewInteraction appCompatSpinner10 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner10.perform(click());

        ViewInteraction appCompatCheckedTextView8 = onView(
                allOf(withId(android.R.id.text1), withText("Prop2"), isDisplayed()));
        appCompatCheckedTextView8.perform(click());

        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton8.perform(click());

        ViewInteraction appCompatSpinner11 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner11.perform(click());

        ViewInteraction appCompatCheckedTextView9 = onView(
                allOf(withId(android.R.id.text1), withText("JoProp"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView9.perform(click());

        ViewInteraction appCompatEditText57 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText57.perform(replaceText("Flor"), closeSoftKeyboard());

        ViewInteraction appCompatEditText58 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText58.perform(click());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton23.perform(scrollTo(), click());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton24.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.input_peso_vivo),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.input_caminhada_vertical),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText6.check(matches(withText("")));

        ViewInteraction appCompatEditText59 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText59.perform(replaceText("400"), closeSoftKeyboard());

        ViewInteraction appCompatEditText60 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText60.perform(click());

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton25.perform(scrollTo(), click());

        ViewInteraction appCompatEditText61 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText61.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText62 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText62.perform(replaceText("4"), closeSoftKeyboard());

        ViewInteraction appCompatEditText63 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText63.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox5 = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox5.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox6 = onView(
                allOf(withId(R.id.ckb_pastando), withText("Pastando")));
        appCompatCheckBox6.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox7 = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox7.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.rb4), withText("4"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton26.perform(scrollTo(), click());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.input_peso_vivo), withText("400"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText7.check(matches(withText("400")));

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.input_caminhada_vertical), withText("4"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText8.check(matches(withText("4")));

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.fragmentDadosComplementar),
                                withParent(withId(R.id.pager))),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton27.perform(scrollTo(), click());

    }

*/
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

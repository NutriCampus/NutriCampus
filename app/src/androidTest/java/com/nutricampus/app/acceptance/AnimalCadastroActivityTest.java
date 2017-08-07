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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
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


/**
 * Created by jorge on 21/07/17.
 */

@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnimalCadastroActivityTest {
    private Activity currentActivity;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

        @Test
        public void animalCadastroActivityTest1() throws Exception {//Cadastro Total(Inserindo nova propriedade)
           prepararTeste();

            closeKeyboard();
            ViewInteraction floatingActionButton = onView(
                    allOf(withId(R.id.btn_add_proprietario),
                            withParent(allOf(withId(R.id.telaListaAnimais),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton.perform(click());
            closeKeyboard();
            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.btn_add_propriedade), isDisplayed()));
            appCompatButton2.perform(click());
            closeKeyboard();
            ViewInteraction appCompatButton3 = onView(
                    withId(R.id.btn_add_proprietario));
            appCompatButton3.perform(scrollTo(), click());
            closeKeyboard();

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
            appCompatEditText3.perform(replaceText("Jorge Veloso"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText7 = onView(
                    allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
            appCompatEditText7.perform(replaceText("04998517490"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText8 = onView(
                    allOf(withId(R.id.input_email_proprietario), isDisplayed()));
            appCompatEditText8.perform(replaceText("jvsveloso@gmail.com.com"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText12 = onView(
                    allOf(withId(R.id.input_fone_proprietario),isDisplayed()));
            appCompatEditText12.perform(replaceText("(99) 99999 9999"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                            withParent(allOf(withId(R.id.telaCadastrarProprietarioActivity),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            appCompatButton4.perform(click());
            closeKeyboard();
            ViewInteraction appCompatButton5 = onView(
                    allOf(withId(android.R.id.button1), withText("OK")));
            appCompatButton5.perform(scrollTo(), click());
            closeKeyboard();
            ViewInteraction appCompatEditText13 = onView(
                    withId(R.id.input_nome_propriedade));
            appCompatEditText13.perform(scrollTo(), replaceText("Propriedade 1"), closeSoftKeyboard());
            closeKeyboard();
           ViewInteraction appCompatEditText17 = onView(
                    allOf(withId(R.id.input_telefone_propriedade)));
            appCompatEditText17.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText18 = onView(
                    withId(R.id.input_rua));
            appCompatEditText18.perform(scrollTo(), replaceText("Rua da Independencia"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText19 = onView(
                    withId(R.id.input_bairro));
            appCompatEditText19.perform(scrollTo(), replaceText("Mundaú"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText20 = onView(
                    allOf(withId(R.id.input_numero), isDisplayed()));
            appCompatEditText20.perform(replaceText("213"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText22 = onView(
                    allOf(withId(R.id.input_cep), isDisplayed()));
            appCompatEditText22.perform(replaceText("55290-000"), closeSoftKeyboard());
            closeKeyboard();
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
            closeKeyboard();

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
            appCompatEditText23.perform(replaceText("Mimosa"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText55 = onView(
                    allOf(withId(R.id.input_data_nascimento), isDisplayed()));
            appCompatEditText55.perform(replaceText("17/07/2017"), closeSoftKeyboard());
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
            appCompatEditText25.perform(replaceText("600"), closeSoftKeyboard());
            closeSoftKeyboard();

            ViewInteraction appCompatEditText0 = onView(
                    allOf(withId(R.id.input_data_complementar), isDisplayed()));
            appCompatEditText0.perform(replaceText("22/07/2017"), closeSoftKeyboard());
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
        public void animalCadastroActivityTest2() throws Exception {//Cadastro selecionando propriedade existente
            closeKeyboard();
            prepararTeste();
            closeKeyboard();
            ViewInteraction floatingActionButton = onView(
                    allOf(withId(R.id.btn_add_proprietario),
                            withParent(allOf(withId(R.id.telaListaAnimais),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton.perform(click());
            closeKeyboard();
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
            appCompatEditText23.perform(replaceText("Flor"), closeSoftKeyboard());

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
        public void animalCadastroActivityTest3() throws Exception {//Cadastro e animal de mesmo id em propriedade diferente
          prepararTeste();
            closeKeyboard();
            ViewInteraction floatingActionButton = onView(
                    allOf(withId(R.id.btn_add_proprietario),
                            withParent(allOf(withId(R.id.telaListaAnimais),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton.perform(click());
            closeKeyboard();
            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.btn_add_propriedade), isDisplayed()));
            appCompatButton2.perform(click());
            closeKeyboard();
            ViewInteraction appCompatSpinner = onView(
                    withId(R.id.spinner_proprietario));
            appCompatSpinner.perform(scrollTo(), click());
            closeKeyboard();
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
            appCompatEditText13.perform(scrollTo(), replaceText("Propriedade 2"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText17 = onView(
                    allOf(withId(R.id.input_telefone_propriedade)));
            appCompatEditText17.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText18 = onView(
                    withId(R.id.input_rua));
            appCompatEditText18.perform(scrollTo(), replaceText("Rua da Esperança"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText19 = onView(
                    withId(R.id.input_bairro));
            appCompatEditText19.perform(scrollTo(), replaceText("Heliopolis"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText20 = onView(
                    allOf(withId(R.id.input_numero), isDisplayed()));
            appCompatEditText20.perform(replaceText("213"), closeSoftKeyboard());
            closeKeyboard();
            ViewInteraction appCompatEditText22 = onView(
                    allOf(withId(R.id.input_cep), isDisplayed()));
            appCompatEditText22.perform(replaceText("55295-390"), closeSoftKeyboard());
            closeKeyboard();
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
            closeKeyboard();
            ViewInteraction appCompatEditText23 = onView(
                    allOf(withId(R.id.input_identificador), isDisplayed()));
            appCompatEditText23.perform(replaceText("Mimosa"), closeSoftKeyboard());

            closeKeyboard();
            ViewInteraction appCompatEditText55 = onView(
                    allOf(withId(R.id.input_data_nascimento), isDisplayed()));
            appCompatEditText55.perform(replaceText("17/12/2015"), closeSoftKeyboard());
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
            appCompatEditText27.perform(replaceText("2"), closeSoftKeyboard());
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
        public void animalCadastroActivityTest4() throws Exception {//Cadastro e animal sem informar os dados
        prepararTeste();
        closeKeyboard();
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());
        closeKeyboard();
        pressBack();
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        closeKeyboard();
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Procure uma propriedade"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Procure uma propriedade")));
            closeKeyboard();
    }
        @Test
        public void animalCadastroActivityTest5() throws Exception {//Cadastro e animal sem informar a propriedade
        prepararTeste();
            closeKeyboard();
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());
            closeKeyboard();
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
        pressBack();
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton5.perform(click());
            closeKeyboard();
            swipeUp();

            closeKeyboard();
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Propriedade 1")));

    }
        @Test
        public void animalCadastroActivityTest6() throws Exception {//Cadastro de animal em duplicidade
        closeKeyboard();
            prepararTeste();
            closeKeyboard();
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());
            closeKeyboard();
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
            closeKeyboard();
        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText23.perform(replaceText("Mimosa"), closeSoftKeyboard());
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
        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText25.perform(replaceText("500"), closeSoftKeyboard());
        closeSoftKeyboard();

        ViewInteraction appCompatEditText01 = onView(
                  allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText01.perform(replaceText("22/07/2017"), closeSoftKeyboard());
          closeKeyboard();

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
        ViewInteraction editText5 = onView(
                allOf(withId(R.id.input_peso_vivo),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("500")));
            closeKeyboard();
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

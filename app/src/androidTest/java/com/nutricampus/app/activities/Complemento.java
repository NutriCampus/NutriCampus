package com.nutricampus.app.activities;


import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nutricampus.app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Complemento {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void complemento() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText2.perform(replaceText("admin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton.perform(click());

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
        recyclerView.perform(actionOnItemAtPosition(5, click()));

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

        ViewInteraction appCompatButton3 = onView(
                withId(R.id.btn_add_proprietario));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
        appCompatEditText5.perform(replaceText("Jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
        appCompatEditText6.perform(replaceText("049"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049."), isDisplayed()));
        appCompatEditText7.perform(replaceText("049.985"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049.985."), isDisplayed()));
        appCompatEditText8.perform(replaceText("049.985.174"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.input_cpf_proprietario), withText("049.985.174-"), isDisplayed()));
        appCompatEditText9.perform(replaceText("049.985.174-90"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.input_email_proprietario), isDisplayed()));
        appCompatEditText10.perform(replaceText("j@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_fone_proprietario), isDisplayed()));
        appCompatEditText11.perform(replaceText("8"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(8"), isDisplayed()));
        appCompatEditText12.perform(replaceText("(87"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(87) "), isDisplayed()));
        appCompatEditText13.perform(replaceText("(87) 99624"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_fone_proprietario), withText("(87) 99624 "), isDisplayed()));
        appCompatEditText14.perform(replaceText("(87) 99624 8834"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                        withParent(allOf(withId(R.id.tela_cadastrarproprietarioactvity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatEditText15 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText15.perform(scrollTo(), click());

        ViewInteraction appCompatEditText16 = onView(
                withId(R.id.input_nome_propriedade));
        appCompatEditText16.perform(scrollTo(), replaceText("Jo"), closeSoftKeyboard());

        ViewInteraction appCompatEditText17 = onView(
                withId(R.id.input_telefone_propriedade));
        appCompatEditText17.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(8")));
        appCompatEditText18.perform(scrollTo(), replaceText("(87"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) ")));
        appCompatEditText19.perform(scrollTo(), replaceText("(87) 99999"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_telefone_propriedade), withText("(87) 99999 ")));
        appCompatEditText20.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                withId(R.id.input_rua));
        appCompatEditText21.perform(scrollTo(), replaceText("I"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_rua), withText("I")));
        appCompatEditText22.perform(pressImeActionButton());

        ViewInteraction appCompatEditText23 = onView(
                withId(R.id.input_bairro));
        appCompatEditText23.perform(scrollTo(), replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.input_numero), isDisplayed()));
        appCompatEditText24.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.input_cep), isDisplayed()));
        appCompatEditText25.perform(replaceText("55555"), closeSoftKeyboard());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.input_cep), withText("55555-"), isDisplayed()));
        appCompatEditText26.perform(replaceText("55555-555"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                withId(R.id.input_estado));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("Pe"), closeSoftKeyboard());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                withId(R.id.input_cidade));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gara"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Garanhuns"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText27 = onView(
                withId(R.id.input_bairro));
        appCompatEditText27.perform(scrollTo(), replaceText("Hoje"), closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText28.perform(click());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText29.perform(replaceText("Jaxk"), closeSoftKeyboard());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.input_data_nascimento), isDisplayed()));
        appCompatEditText30.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton8.perform(click());

        pressBack();

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatEditText31 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText31.perform(click());

        ViewInteraction appCompatEditText32 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText32.perform(replaceText("50"), closeSoftKeyboard());

        ViewInteraction appCompatEditText33 = onView(
                allOf(withId(R.id.input_data_complementar), isDisplayed()));
        appCompatEditText33.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatEditText34 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText34.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText35 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText35.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText36 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText36.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb3), withText("3"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btn_salvar), withText("Salvar")));
        appCompatButton11.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Jo"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Jo")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Jaxk")));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.btn_add_proprietario),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Jo"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText37 = onView(
                allOf(withId(R.id.input_identificador), isDisplayed()));
        appCompatEditText37.perform(click());

        pressBack();

        pressBack();

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText38 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText38.perform(replaceText("Jax"), closeSoftKeyboard());

        ViewInteraction appCompatEditText39 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Jax"), isDisplayed()));
        appCompatEditText39.perform(pressImeActionButton());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Jaxk")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.input_pesquisa), withText("Jax"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("Jax")));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction appCompatEditText40 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText40.perform(replaceText("Mim"), closeSoftKeyboard());

        ViewInteraction appCompatEditText41 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));
        appCompatEditText41.perform(pressImeActionButton());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatEditText42 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));
        appCompatEditText42.perform(pressImeActionButton());

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Jo"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Jo")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Nenhum animal cadastrado")));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction appCompatEditText43 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText43.perform(click());

        ViewInteraction appCompatEditText44 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText44.perform(click());

        ViewInteraction appCompatEditText45 = onView(
                allOf(withId(R.id.input_peso_prole), isDisplayed()));
        appCompatEditText45.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatEditText46 = onView(
                allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
        appCompatEditText46.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.btn_add_prole), isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatCheckBox4 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox4.perform(click());

        ViewInteraction appCompatEditText47 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText47.perform(click());

        ViewInteraction appCompatEditText48 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText48.perform(replaceText("35"), closeSoftKeyboard());

        ViewInteraction appCompatEditText49 = onView(
                allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
        appCompatEditText49.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Mês passado"),
                        withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Junho"), isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
        appCompatButton18.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.text1), withText("Junho"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_meses),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Junho")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.lista_prole_data), withText("15/06/2017"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("15/06/2017")));

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spinner_meses), isDisplayed()));
        appCompatSpinner5.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
        appCompatTextView5.perform(click());

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

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.lista_prole_data), withText("25/07/2017"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("25/07/2017")));

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatCheckBox5 = onView(
                allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                        withParent(allOf(withId(R.id.linearLayout),
                                withParent(withId(R.id.tela_cadastro_prole)))),
                        isDisplayed()));
        appCompatCheckBox5.perform(click());

        ViewInteraction appCompatEditText50 = onView(
                allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
        appCompatEditText50.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Mês passado"),
                        withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton20.perform(click());

        ViewInteraction appCompatEditText51 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText51.perform(click());

        ViewInteraction appCompatEditText52 = onView(
                allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
        appCompatEditText52.perform(replaceText("15"), closeSoftKeyboard());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
        appCompatButton21.perform(click());

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

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.lista_prole_data), withText("22/06/2017"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("22/06/2017")));

        ViewInteraction appCompatTextView8 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView8.perform(click());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton23.perform(scrollTo(), click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.text_quantidade_encontrados), withText("2 registros encontrados"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_prole),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("2 registros encontrados")));

        pressBack();

        ViewInteraction appCompatTextView9 = onView(
                allOf(withId(android.R.id.title), withText("Ver histórico de crescimento"), isDisplayed()));
        appCompatTextView9.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.lista_dados_data), withText("13/07/2017"),
                        childAtPosition(
                                allOf(withId(R.id.ItensListaAnimal),
                                        childAtPosition(
                                                withId(R.id.listDadosCompl),
                                                0)),
                                0),
                        isDisplayed()));
        textView13.check(matches(withText("13/07/2017")));

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.btn_add_dados_compl),
                        withParent(allOf(withId(R.id.telaListaDadosCompl),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction appCompatEditText53 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText53.perform(click());

        ViewInteraction appCompatEditText54 = onView(
                allOf(withId(R.id.input_peso_vivo), isDisplayed()));
        appCompatEditText54.perform(replaceText("500"), closeSoftKeyboard());

        ViewInteraction appCompatEditText55 = onView(
                allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
        appCompatEditText55.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText56 = onView(
                allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
        appCompatEditText56.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText57 = onView(
                withId(R.id.input_semana_lactacao));
        appCompatEditText57.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatCheckBox6 = onView(
                allOf(withId(R.id.ckb_pastando), withText("Pastando")));
        appCompatCheckBox6.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.rb3), withText("3"),
                        withParent(withId(R.id.rgEec))));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.btn_salvar), withText("Salvar")));
        appCompatButton24.perform(scrollTo(), click());

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.lista_dados_data), withText("13/07/2017"),
                        childAtPosition(
                                allOf(withId(R.id.ItensListaAnimal),
                                        childAtPosition(
                                                withId(R.id.listDadosCompl),
                                                0)),
                                0),
                        isDisplayed()));
        textView14.check(matches(withText("13/07/2017")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.lista_dados_data), withText("25/07/2017"),
                        childAtPosition(
                                allOf(withId(R.id.ItensListaAnimal),
                                        childAtPosition(
                                                withId(R.id.listDadosCompl),
                                                1)),
                                0),
                        isDisplayed()));
        textView15.check(matches(withText("25/07/2017")));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.ItensListaAnimal),
                        childAtPosition(
                                allOf(withId(R.id.listDadosCompl),
                                        withParent(withId(R.id.telaListaDadosCompl))),
                                0),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.input_peso_vivo), withText("50.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("50.0")));

        pressBack();

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(R.id.btn_salvar), withText("Corrigir")));
        appCompatButton25.perform(scrollTo(), click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.lista_dados_data), withText("25/07/2017"),
                        childAtPosition(
                                allOf(withId(R.id.ItensListaAnimal),
                                        childAtPosition(
                                                withId(R.id.listDadosCompl),
                                                0)),
                                0),
                        isDisplayed()));
        textView16.check(matches(withText("25/07/2017")));

        pressBack();

        ViewInteraction relativeLayout2 = onView(
                allOf(withId(R.id.ItensListaAnimal),
                        childAtPosition(
                                allOf(withId(R.id.listaAnimais),
                                        withParent(withId(R.id.resultado_busca_animais))),
                                0),
                        isDisplayed()));
        relativeLayout2.perform(click());

        ViewInteraction appCompatTextView10 = onView(
                allOf(withText("Dados Gerais"), isDisplayed()));
        appCompatTextView10.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.input_peso_vivo), withText("500.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("500.0")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.input_data_complementar), withText("25/07/2017"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("25/07/2017")));

        pressBack();

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.btn_salvar), withText("Atualizar")));
        appCompatButton26.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatTextView11 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView11.perform(click());

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(android.R.id.button2), withText("Não")));
        appCompatButton27.perform(scrollTo(), click());

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ItensListaAnimal),
                                        0),
                                0),
                        isDisplayed()));
        textView17.check(matches(withText("Jaxk")));

        ViewInteraction appCompatTextView12 = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView12.perform(click());

        ViewInteraction appCompatButton28 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton28.perform(scrollTo(), click());

        ViewInteraction textView18 = onView(
                allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView18.check(matches(withText("Nenhum animal cadastrado")));

        ViewInteraction appCompatSpinner7 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner7.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction textView19 = onView(
                allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_animais),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView19.check(matches(withText("Nenhum animal cadastrado")));

        ViewInteraction textView20 = onView(
                allOf(withId(android.R.id.text1), withText("Jo"),
                        childAtPosition(
                                allOf(withId(R.id.spinnerPropriedade),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView20.check(matches(withText("Jo")));

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction appCompatEditText58 = onView(
                allOf(withId(R.id.input_pesquisa), isDisplayed()));
        appCompatEditText58.perform(replaceText("Mi"), closeSoftKeyboard());

        ViewInteraction appCompatEditText59 = onView(
                allOf(withId(R.id.input_pesquisa), withText("Mi"), isDisplayed()));
        appCompatEditText59.perform(pressImeActionButton());

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        ViewInteraction appCompatButton29 = onView(
                allOf(withId(R.id.btn_salvar), withText("Corrigir")));
        appCompatButton29.perform(scrollTo(), click());

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        pressBack();

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton6.perform(click());


    }

        @Test
        public void AnimalAtualizar2() throws Exception {
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

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
            appCompatEditText4.perform(click());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.input_nome_proprietario), isDisplayed()));
            appCompatEditText5.perform(replaceText("Jorge"), closeSoftKeyboard());

            ViewInteraction appCompatEditText6 = onView(
                    allOf(withId(R.id.input_cpf_proprietario), isDisplayed()));
            appCompatEditText6.perform(replaceText("049"), closeSoftKeyboard());

            ViewInteraction appCompatEditText7 = onView(
                    allOf(withId(R.id.input_cpf_proprietario), withText("049."), isDisplayed()));
            appCompatEditText7.perform(replaceText("049.985"), closeSoftKeyboard());

            ViewInteraction appCompatEditText8 = onView(
                    allOf(withId(R.id.input_cpf_proprietario), withText("049.985."), isDisplayed()));
            appCompatEditText8.perform(replaceText("049.985.174"), closeSoftKeyboard());

            ViewInteraction appCompatEditText9 = onView(
                    allOf(withId(R.id.input_cpf_proprietario), withText("049.985.174-"), isDisplayed()));
            appCompatEditText9.perform(replaceText("049.985.174-90"), closeSoftKeyboard());

            ViewInteraction appCompatEditText10 = onView(
                    allOf(withId(R.id.input_email_proprietario), isDisplayed()));
            appCompatEditText10.perform(replaceText("j@gmail.com"), closeSoftKeyboard());

            ViewInteraction appCompatEditText11 = onView(
                    allOf(withId(R.id.input_fone_proprietario), isDisplayed()));
            appCompatEditText11.perform(replaceText("8"), closeSoftKeyboard());

            ViewInteraction appCompatEditText12 = onView(
                    allOf(withId(R.id.input_fone_proprietario), withText("(8"), isDisplayed()));
            appCompatEditText12.perform(replaceText("(87"), closeSoftKeyboard());

            ViewInteraction appCompatEditText13 = onView(
                    allOf(withId(R.id.input_fone_proprietario), withText("(87) "), isDisplayed()));
            appCompatEditText13.perform(replaceText("(87) 99624"), closeSoftKeyboard());

            ViewInteraction appCompatEditText14 = onView(
                    allOf(withId(R.id.input_fone_proprietario), withText("(87) 99624 "), isDisplayed()));
            appCompatEditText14.perform(replaceText("(87) 99624 8834"), closeSoftKeyboard());

            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"),
                            withParent(allOf(withId(R.id.tela_cadastrarproprietarioactvity),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            appCompatButton4.perform(click());

            ViewInteraction appCompatButton5 = onView(
                    allOf(withId(android.R.id.button1), withText("OK")));
            appCompatButton5.perform(scrollTo(), click());

            ViewInteraction appCompatEditText15 = onView(
                    withId(R.id.input_nome_propriedade));
            appCompatEditText15.perform(scrollTo(), click());

            ViewInteraction appCompatEditText16 = onView(
                    withId(R.id.input_nome_propriedade));
            appCompatEditText16.perform(scrollTo(), replaceText("Jo"), closeSoftKeyboard());

            ViewInteraction appCompatEditText17 = onView(
                    withId(R.id.input_telefone_propriedade));
            appCompatEditText17.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

            ViewInteraction appCompatEditText18 = onView(
                    allOf(withId(R.id.input_telefone_propriedade), withText("(8")));
            appCompatEditText18.perform(scrollTo(), replaceText("(87"), closeSoftKeyboard());

            ViewInteraction appCompatEditText19 = onView(
                    allOf(withId(R.id.input_telefone_propriedade), withText("(87) ")));
            appCompatEditText19.perform(scrollTo(), replaceText("(87) 99999"), closeSoftKeyboard());

            ViewInteraction appCompatEditText20 = onView(
                    allOf(withId(R.id.input_telefone_propriedade), withText("(87) 99999 ")));
            appCompatEditText20.perform(scrollTo(), replaceText("(87) 99999 9999"), closeSoftKeyboard());

            ViewInteraction appCompatEditText21 = onView(
                    withId(R.id.input_rua));
            appCompatEditText21.perform(scrollTo(), replaceText("I"), closeSoftKeyboard());

            ViewInteraction appCompatEditText22 = onView(
                    allOf(withId(R.id.input_rua), withText("I")));
            appCompatEditText22.perform(pressImeActionButton());

            ViewInteraction appCompatEditText23 = onView(
                    withId(R.id.input_bairro));
            appCompatEditText23.perform(scrollTo(), replaceText(""), closeSoftKeyboard());

            ViewInteraction appCompatEditText24 = onView(
                    allOf(withId(R.id.input_numero), isDisplayed()));
            appCompatEditText24.perform(replaceText("2"), closeSoftKeyboard());

            ViewInteraction appCompatEditText25 = onView(
                    allOf(withId(R.id.input_cep), isDisplayed()));
            appCompatEditText25.perform(replaceText("55555"), closeSoftKeyboard());

            ViewInteraction appCompatEditText26 = onView(
                    allOf(withId(R.id.input_cep), withText("55555-"), isDisplayed()));
            appCompatEditText26.perform(replaceText("55555-555"), closeSoftKeyboard());

            ViewInteraction appCompatAutoCompleteTextView = onView(
                    withId(R.id.input_estado));
            appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("Pe"), closeSoftKeyboard());

            ViewInteraction appCompatTextView = onView(
                    allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
            appCompatTextView.perform(click());

            ViewInteraction appCompatAutoCompleteTextView2 = onView(
                    withId(R.id.input_cidade));
            appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gara"), closeSoftKeyboard());

            ViewInteraction appCompatTextView2 = onView(
                    allOf(withId(android.R.id.text1), withText("Garanhuns"), isDisplayed()));
            appCompatTextView2.perform(click());

            ViewInteraction appCompatButton6 = onView(
                    allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
            appCompatButton6.perform(scrollTo(), click());

            ViewInteraction appCompatEditText27 = onView(
                    withId(R.id.input_bairro));
            appCompatEditText27.perform(scrollTo(), replaceText("Hoje"), closeSoftKeyboard());

            ViewInteraction appCompatButton7 = onView(
                    allOf(withId(R.id.btn_salvar_propriedade), withText("Salvar")));
            appCompatButton7.perform(scrollTo(), click());

            ViewInteraction appCompatEditText28 = onView(
                    allOf(withId(R.id.input_identificador), isDisplayed()));
            appCompatEditText28.perform(click());

            ViewInteraction appCompatEditText29 = onView(
                    allOf(withId(R.id.input_identificador), isDisplayed()));
            appCompatEditText29.perform(replaceText("Jaxk"), closeSoftKeyboard());

            ViewInteraction appCompatEditText30 = onView(
                    allOf(withId(R.id.input_data_nascimento), isDisplayed()));
            appCompatEditText30.perform(click());

            ViewInteraction appCompatButton8 = onView(
                    allOf(withId(android.R.id.button1), withText("OK"),
                            withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                    withParent(withClassName(is("android.widget.LinearLayout"))))),
                            isDisplayed()));
            appCompatButton8.perform(click());

            pressBack();

            ViewInteraction appCompatButton9 = onView(
                    allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                            withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                    withParent(withId(R.id.pager)))),
                            isDisplayed()));
            appCompatButton9.perform(click());

            ViewInteraction appCompatEditText31 = onView(
                    allOf(withId(R.id.input_peso_vivo), isDisplayed()));
            appCompatEditText31.perform(click());

            ViewInteraction appCompatEditText32 = onView(
                    allOf(withId(R.id.input_peso_vivo), isDisplayed()));
            appCompatEditText32.perform(replaceText("50"), closeSoftKeyboard());

            ViewInteraction appCompatEditText33 = onView(
                    allOf(withId(R.id.input_data_complementar), isDisplayed()));
            appCompatEditText33.perform(click());

            ViewInteraction appCompatButton10 = onView(
                    allOf(withId(android.R.id.button1), withText("OK"),
                            withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                    withParent(withClassName(is("android.widget.LinearLayout"))))),
                            isDisplayed()));
            appCompatButton10.perform(click());

            ViewInteraction appCompatEditText34 = onView(
                    allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
            appCompatEditText34.perform(replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatEditText35 = onView(
                    allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
            appCompatEditText35.perform(replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatEditText36 = onView(
                    withId(R.id.input_semana_lactacao));
            appCompatEditText36.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatCheckBox = onView(
                    allOf(withId(R.id.ckb_lactacao), withText("Lactação")));
            appCompatCheckBox.perform(scrollTo(), click());

            ViewInteraction appCompatRadioButton = onView(
                    allOf(withId(R.id.rb3), withText("3"),
                            withParent(withId(R.id.rgEec))));
            appCompatRadioButton.perform(scrollTo(), click());

            ViewInteraction appCompatButton11 = onView(
                    allOf(withId(R.id.btn_salvar), withText("Salvar")));
            appCompatButton11.perform(scrollTo(), click());

            ViewInteraction appCompatSpinner = onView(
                    allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
            appCompatSpinner.perform(click());

            ViewInteraction appCompatCheckedTextView = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
            appCompatCheckedTextView.perform(click());

            ViewInteraction textView = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"),
                            childAtPosition(
                                    allOf(withId(R.id.spinnerPropriedade),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView.check(matches(withText("Jo")));

            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.ItensListaAnimal),
                                            0),
                                    0),
                            isDisplayed()));
            textView2.check(matches(withText("Jaxk")));

            ViewInteraction floatingActionButton2 = onView(
                    allOf(withId(R.id.btn_add_proprietario),
                            withParent(allOf(withId(R.id.telaListaAnimais),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton2.perform(click());

            ViewInteraction floatingActionButton3 = onView(
                    allOf(withId(R.id.btn_add_proprietario),
                            withParent(allOf(withId(R.id.telaListaAnimais),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton3.perform(click());

            ViewInteraction appCompatSpinner2 = onView(
                    allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
            appCompatSpinner2.perform(click());

            ViewInteraction appCompatCheckedTextView2 = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"),
                            childAtPosition(
                                    allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                            withParent(withClassName(is("android.widget.FrameLayout")))),
                                    1),
                            isDisplayed()));
            appCompatCheckedTextView2.perform(click());

            ViewInteraction appCompatEditText37 = onView(
                    allOf(withId(R.id.input_identificador), isDisplayed()));
            appCompatEditText37.perform(click());

            pressBack();

            pressBack();

            ViewInteraction actionMenuItemView = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView.perform(click());

            ViewInteraction appCompatEditText38 = onView(
                    allOf(withId(R.id.input_pesquisa), isDisplayed()));
            appCompatEditText38.perform(replaceText("Jax"), closeSoftKeyboard());

            ViewInteraction appCompatEditText39 = onView(
                    allOf(withId(R.id.input_pesquisa), withText("Jax"), isDisplayed()));
            appCompatEditText39.perform(pressImeActionButton());

            ViewInteraction textView3 = onView(
                    allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.ItensListaAnimal),
                                            0),
                                    0),
                            isDisplayed()));
            textView3.check(matches(withText("Jaxk")));

            ViewInteraction editText = onView(
                    allOf(withId(R.id.input_pesquisa), withText("Jax"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.action_bar),
                                            1),
                                    0),
                            isDisplayed()));
            editText.check(matches(withText("Jax")));

            ViewInteraction actionMenuItemView2 = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView2.perform(click());

            ViewInteraction actionMenuItemView3 = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView3.perform(click());

            ViewInteraction actionMenuItemView4 = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView4.perform(click());

            ViewInteraction actionMenuItemView5 = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView5.perform(click());

            ViewInteraction appCompatEditText40 = onView(
                    allOf(withId(R.id.input_pesquisa), isDisplayed()));
            appCompatEditText40.perform(replaceText("Mim"), closeSoftKeyboard());

            ViewInteraction appCompatEditText41 = onView(
                    allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));
            appCompatEditText41.perform(pressImeActionButton());

            ViewInteraction appCompatSpinner3 = onView(
                    allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
            appCompatSpinner3.perform(click());

            ViewInteraction appCompatCheckedTextView3 = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
            appCompatCheckedTextView3.perform(click());

            ViewInteraction appCompatEditText42 = onView(
                    allOf(withId(R.id.input_pesquisa), withText("Mim"), isDisplayed()));
            appCompatEditText42.perform(pressImeActionButton());

            ViewInteraction textView4 = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"),
                            childAtPosition(
                                    allOf(withId(R.id.spinnerPropriedade),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView4.check(matches(withText("Jo")));

            ViewInteraction textView5 = onView(
                    allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                            childAtPosition(
                                    allOf(withId(R.id.resultado_busca_animais),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView5.check(matches(withText("Nenhum animal cadastrado")));

            ViewInteraction appCompatTextView3 = onView(
                    allOf(withId(android.R.id.title), withText("Ver prole"), isDisplayed()));
            appCompatTextView3.perform(click());

            ViewInteraction floatingActionButton4 = onView(
                    allOf(withId(R.id.btn_add_prole), isDisplayed()));
            floatingActionButton4.perform(click());

            ViewInteraction appCompatCheckBox2 = onView(
                    allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                            withParent(allOf(withId(R.id.linearLayout),
                                    withParent(withId(R.id.tela_cadastro_prole)))),
                            isDisplayed()));
            appCompatCheckBox2.perform(click());

            ViewInteraction appCompatButton12 = onView(
                    allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
            appCompatButton12.perform(click());

            ViewInteraction floatingActionButton5 = onView(
                    allOf(withId(R.id.btn_add_prole), isDisplayed()));
            floatingActionButton5.perform(click());

            ViewInteraction appCompatEditText43 = onView(
                    allOf(withId(R.id.input_peso_prole), isDisplayed()));
            appCompatEditText43.perform(click());

            ViewInteraction appCompatEditText44 = onView(
                    allOf(withId(R.id.input_peso_prole), isDisplayed()));
            appCompatEditText44.perform(click());

            ViewInteraction appCompatEditText45 = onView(
                    allOf(withId(R.id.input_peso_prole), isDisplayed()));
            appCompatEditText45.perform(replaceText("30"), closeSoftKeyboard());

            ViewInteraction appCompatEditText46 = onView(
                    allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
            appCompatEditText46.perform(click());

            ViewInteraction appCompatButton13 = onView(
                    allOf(withId(android.R.id.button1), withText("OK"),
                            withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                    withParent(withClassName(is("android.widget.LinearLayout"))))),
                            isDisplayed()));
            appCompatButton13.perform(click());

            ViewInteraction appCompatButton14 = onView(
                    allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
            appCompatButton14.perform(click());

            ViewInteraction appCompatButton15 = onView(
                    allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
            appCompatButton15.perform(click());

            ViewInteraction floatingActionButton6 = onView(
                    allOf(withId(R.id.btn_add_prole), isDisplayed()));
            floatingActionButton6.perform(click());

            ViewInteraction appCompatCheckBox3 = onView(
                    allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                            withParent(allOf(withId(R.id.linearLayout),
                                    withParent(withId(R.id.tela_cadastro_prole)))),
                            isDisplayed()));
            appCompatCheckBox3.perform(click());

            ViewInteraction appCompatCheckBox4 = onView(
                    allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                            withParent(allOf(withId(R.id.linearLayout),
                                    withParent(withId(R.id.tela_cadastro_prole)))),
                            isDisplayed()));
            appCompatCheckBox4.perform(click());

            ViewInteraction appCompatEditText47 = onView(
                    allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
            appCompatEditText47.perform(click());

            ViewInteraction appCompatEditText48 = onView(
                    allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
            appCompatEditText48.perform(replaceText("35"), closeSoftKeyboard());

            ViewInteraction appCompatEditText49 = onView(
                    allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
            appCompatEditText49.perform(click());

            ViewInteraction appCompatImageButton2 = onView(
                    allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Mês passado"),
                            withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                    withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                            isDisplayed()));
            appCompatImageButton2.perform(click());

            ViewInteraction appCompatButton16 = onView(
                    allOf(withId(android.R.id.button1), withText("OK"),
                            withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                    withParent(withClassName(is("android.widget.LinearLayout"))))),
                            isDisplayed()));
            appCompatButton16.perform(click());

            ViewInteraction appCompatButton17 = onView(
                    allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
            appCompatButton17.perform(click());

            ViewInteraction appCompatSpinner4 = onView(
                    allOf(withId(R.id.spinner_meses), isDisplayed()));
            appCompatSpinner4.perform(click());

            ViewInteraction appCompatTextView4 = onView(
                    allOf(withId(android.R.id.text1), withText("Junho"), isDisplayed()));
            appCompatTextView4.perform(click());

            ViewInteraction appCompatButton18 = onView(
                    allOf(withId(R.id.btn_filtrar_prole), isDisplayed()));
            appCompatButton18.perform(click());

            ViewInteraction textView6 = onView(
                    allOf(withId(android.R.id.text1), withText("Junho"),
                            childAtPosition(
                                    allOf(withId(R.id.spinner_meses),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView6.check(matches(withText("Junho")));

            ViewInteraction textView7 = onView(
                    allOf(withId(R.id.lista_prole_data), withText("15/06/2017"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            textView7.check(matches(withText("15/06/2017")));

            ViewInteraction appCompatSpinner5 = onView(
                    allOf(withId(R.id.spinner_meses), isDisplayed()));
            appCompatSpinner5.perform(click());

            ViewInteraction appCompatTextView5 = onView(
                    allOf(withId(android.R.id.text1), withText("Julho"), isDisplayed()));
            appCompatTextView5.perform(click());

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

            ViewInteraction textView9 = onView(
                    allOf(withId(R.id.lista_prole_data), withText("25/07/2017"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            textView9.check(matches(withText("25/07/2017")));

            ViewInteraction appCompatTextView6 = onView(
                    allOf(withId(android.R.id.title), withText("Editar"), isDisplayed()));
            appCompatTextView6.perform(click());

            ViewInteraction appCompatCheckBox5 = onView(
                    allOf(withId(R.id.check_natimorto), withText("Natimorto"),
                            withParent(allOf(withId(R.id.linearLayout),
                                    withParent(withId(R.id.tela_cadastro_prole)))),
                            isDisplayed()));
            appCompatCheckBox5.perform(click());

            ViewInteraction appCompatEditText50 = onView(
                    allOf(withId(R.id.input_data_nascimento), withText("25/07/2017"), isDisplayed()));
            appCompatEditText50.perform(click());

            ViewInteraction appCompatImageButton3 = onView(
                    allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Mês passado"),
                            withParent(allOf(withClassName(is("android.widget.DayPickerView")),
                                    withParent(withClassName(is("com.android.internal.widget.DialogViewAnimator"))))),
                            isDisplayed()));
            appCompatImageButton3.perform(click());

            ViewInteraction appCompatButton20 = onView(
                    allOf(withId(android.R.id.button1), withText("OK"),
                            withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                    withParent(withClassName(is("android.widget.LinearLayout"))))),
                            isDisplayed()));
            appCompatButton20.perform(click());

            ViewInteraction appCompatEditText51 = onView(
                    allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
            appCompatEditText51.perform(click());

            ViewInteraction appCompatEditText52 = onView(
                    allOf(withId(R.id.input_peso_prole), withText("0"), isDisplayed()));
            appCompatEditText52.perform(replaceText("15"), closeSoftKeyboard());

            ViewInteraction appCompatButton21 = onView(
                    allOf(withId(R.id.btn_salvar_prole), withText("Salvar"), isDisplayed()));
            appCompatButton21.perform(click());

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

            ViewInteraction textView11 = onView(
                    allOf(withId(R.id.lista_prole_data), withText("22/06/2017"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            textView11.check(matches(withText("22/06/2017")));

            ViewInteraction appCompatTextView8 = onView(
                    allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
            appCompatTextView8.perform(click());

            ViewInteraction appCompatButton23 = onView(
                    allOf(withId(android.R.id.button1), withText("Sim")));
            appCompatButton23.perform(scrollTo(), click());

            ViewInteraction textView12 = onView(
                    allOf(withId(R.id.text_quantidade_encontrados), withText("2 registros encontrados"),
                            childAtPosition(
                                    allOf(withId(R.id.resultado_busca_prole),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView12.check(matches(withText("2 registros encontrados")));

            pressBack();

            ViewInteraction appCompatTextView9 = onView(
                    allOf(withId(android.R.id.title), withText("Ver histórico de crescimento"), isDisplayed()));
            appCompatTextView9.perform(click());

            ViewInteraction textView13 = onView(
                    allOf(withId(R.id.lista_dados_data), withText("13/07/2017"),
                            childAtPosition(
                                    allOf(withId(R.id.ItensListaAnimal),
                                            childAtPosition(
                                                    withId(R.id.listDadosCompl),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView13.check(matches(withText("13/07/2017")));

            ViewInteraction floatingActionButton7 = onView(
                    allOf(withId(R.id.btn_add_dados_compl),
                            withParent(allOf(withId(R.id.telaListaDadosCompl),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            floatingActionButton7.perform(click());

            ViewInteraction appCompatEditText53 = onView(
                    allOf(withId(R.id.input_peso_vivo), isDisplayed()));
            appCompatEditText53.perform(click());

            ViewInteraction appCompatEditText54 = onView(
                    allOf(withId(R.id.input_peso_vivo), isDisplayed()));
            appCompatEditText54.perform(replaceText("500"), closeSoftKeyboard());

            ViewInteraction appCompatEditText55 = onView(
                    allOf(withId(R.id.input_caminhada_vertical), isDisplayed()));
            appCompatEditText55.perform(replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatEditText56 = onView(
                    allOf(withId(R.id.input_caminhada_horizontal), isDisplayed()));
            appCompatEditText56.perform(replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatEditText57 = onView(
                    withId(R.id.input_semana_lactacao));
            appCompatEditText57.perform(scrollTo(), replaceText("1"), closeSoftKeyboard());

            ViewInteraction appCompatCheckBox6 = onView(
                    allOf(withId(R.id.ckb_pastando), withText("Pastando")));
            appCompatCheckBox6.perform(scrollTo(), click());

            ViewInteraction appCompatRadioButton2 = onView(
                    allOf(withId(R.id.rb3), withText("3"),
                            withParent(withId(R.id.rgEec))));
            appCompatRadioButton2.perform(scrollTo(), click());

            ViewInteraction appCompatButton24 = onView(
                    allOf(withId(R.id.btn_salvar), withText("Salvar")));
            appCompatButton24.perform(scrollTo(), click());

            ViewInteraction textView14 = onView(
                    allOf(withId(R.id.lista_dados_data), withText("13/07/2017"),
                            childAtPosition(
                                    allOf(withId(R.id.ItensListaAnimal),
                                            childAtPosition(
                                                    withId(R.id.listDadosCompl),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView14.check(matches(withText("13/07/2017")));

            ViewInteraction textView15 = onView(
                    allOf(withId(R.id.lista_dados_data), withText("25/07/2017"),
                            childAtPosition(
                                    allOf(withId(R.id.ItensListaAnimal),
                                            childAtPosition(
                                                    withId(R.id.listDadosCompl),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView15.check(matches(withText("25/07/2017")));

            ViewInteraction relativeLayout = onView(
                    allOf(withId(R.id.ItensListaAnimal),
                            childAtPosition(
                                    allOf(withId(R.id.listDadosCompl),
                                            withParent(withId(R.id.telaListaDadosCompl))),
                                    0),
                            isDisplayed()));
            relativeLayout.perform(click());

            ViewInteraction editText2 = onView(
                    allOf(withId(R.id.input_peso_vivo), withText("50.0"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            editText2.check(matches(withText("50.0")));

            pressBack();

            ViewInteraction appCompatButton25 = onView(
                    allOf(withId(R.id.btn_salvar), withText("Corrigir")));
            appCompatButton25.perform(scrollTo(), click());

            ViewInteraction textView16 = onView(
                    allOf(withId(R.id.lista_dados_data), withText("25/07/2017"),
                            childAtPosition(
                                    allOf(withId(R.id.ItensListaAnimal),
                                            childAtPosition(
                                                    withId(R.id.listDadosCompl),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView16.check(matches(withText("25/07/2017")));

            pressBack();

            ViewInteraction relativeLayout2 = onView(
                    allOf(withId(R.id.ItensListaAnimal),
                            childAtPosition(
                                    allOf(withId(R.id.listaAnimais),
                                            withParent(withId(R.id.resultado_busca_animais))),
                                    0),
                            isDisplayed()));
            relativeLayout2.perform(click());

            ViewInteraction appCompatTextView10 = onView(
                    allOf(withText("Dados Gerais"), isDisplayed()));
            appCompatTextView10.perform(click());

            ViewInteraction editText3 = onView(
                    allOf(withId(R.id.input_peso_vivo), withText("500.0"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            editText3.check(matches(withText("500.0")));

            ViewInteraction editText4 = onView(
                    allOf(withId(R.id.input_data_complementar), withText("25/07/2017"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
            editText4.check(matches(withText("25/07/2017")));

            pressBack();

            ViewInteraction appCompatButton26 = onView(
                    allOf(withId(R.id.btn_salvar), withText("Atualizar")));
            appCompatButton26.perform(scrollTo(), click());

            pressBack();

            ViewInteraction appCompatTextView11 = onView(
                    allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
            appCompatTextView11.perform(click());

            ViewInteraction appCompatButton27 = onView(
                    allOf(withId(android.R.id.button2), withText("Não")));
            appCompatButton27.perform(scrollTo(), click());

            ViewInteraction textView17 = onView(
                    allOf(withId(R.id.lista_animal_nome), withText("Jaxk"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.ItensListaAnimal),
                                            0),
                                    0),
                            isDisplayed()));
            textView17.check(matches(withText("Jaxk")));

            ViewInteraction appCompatTextView12 = onView(
                    allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
            appCompatTextView12.perform(click());

            ViewInteraction appCompatButton28 = onView(
                    allOf(withId(android.R.id.button1), withText("Sim")));
            appCompatButton28.perform(scrollTo(), click());

            ViewInteraction textView18 = onView(
                    allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                            childAtPosition(
                                    allOf(withId(R.id.resultado_busca_animais),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView18.check(matches(withText("Nenhum animal cadastrado")));

            ViewInteraction appCompatSpinner7 = onView(
                    allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
            appCompatSpinner7.perform(click());

            ViewInteraction appCompatCheckedTextView4 = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"), isDisplayed()));
            appCompatCheckedTextView4.perform(click());

            ViewInteraction textView19 = onView(
                    allOf(withId(android.R.id.empty), withText("Nenhum animal cadastrado"),
                            childAtPosition(
                                    allOf(withId(R.id.resultado_busca_animais),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView19.check(matches(withText("Nenhum animal cadastrado")));

            ViewInteraction textView20 = onView(
                    allOf(withId(android.R.id.text1), withText("Jo"),
                            childAtPosition(
                                    allOf(withId(R.id.spinnerPropriedade),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView20.check(matches(withText("Jo")));

            ViewInteraction actionMenuItemView6 = onView(
                    allOf(withId(R.id.action_search), withContentDescription("faw_search"), isDisplayed()));
            actionMenuItemView6.perform(click());

            ViewInteraction appCompatEditText58 = onView(
                    allOf(withId(R.id.input_pesquisa), isDisplayed()));
            appCompatEditText58.perform(replaceText("Mi"), closeSoftKeyboard());

            ViewInteraction appCompatEditText59 = onView(
                    allOf(withId(R.id.input_pesquisa), withText("Mi"), isDisplayed()));
            appCompatEditText59.perform(pressImeActionButton());

            ViewInteraction appCompatButton29 = onView(
                    allOf(withId(R.id.btn_salvar), withText("Corrigir")));
            appCompatButton29.perform(scrollTo(), click());

            ViewInteraction appCompatImageButton4 = onView(
                    allOf(withContentDescription("Navegar para cima"),
                            withParent(allOf(withId(R.id.action_bar),
                                    withParent(withId(R.id.action_bar_container)))),
                            isDisplayed()));
            appCompatImageButton4.perform(click());

            ViewInteraction appCompatImageButton5 = onView(
                    allOf(withContentDescription("Navegar para cima"),
                            withParent(allOf(withId(R.id.action_bar),
                                    withParent(withId(R.id.action_bar_container)))),
                            isDisplayed()));
            appCompatImageButton5.perform(click());

            ViewInteraction appCompatImageButton6 = onView(
                    allOf(withContentDescription("Navegar para cima"),
                            withParent(allOf(withId(R.id.action_bar),
                                    withParent(withId(R.id.action_bar_container)))),
                            isDisplayed()));
            appCompatImageButton6.perform(click());
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

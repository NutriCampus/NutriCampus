package com.nutricampus.app.acceptance;


import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/**
 * Created by jorge on 21/07/17.
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Animal01CadastroActivityTest extends AbstractPreparacaoTestes {

    @Test
    public void animalCadastroActivityTest1() throws Exception {//Cadastro Total(Inserindo nova propriedade)
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());

        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
        repositorioAnimal.removerTodos();


        // Necessário utilizar uma adição direta já que pela tela de cadastro dá erro, pois a tela
        // é pequena no emulador e o botão de add não é encontrado.
        Proprietario proprietario = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com.com", "(99) 99999 9999");
        int idProprietario = repositorioProprietario.inserirProprietario(proprietario);

        Propriedade propriedade = new Propriedade("Propriedade 1", "87999999999", "Rua da Indepencia",
                "Mundaú", "55290-000", "Garanhuns", "Pernambuco", "213", idProprietario, 1);

        repositorioPropriedade.inserirPropriedade(propriedade);

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());
        closeKeyboard();

        Thread.sleep(1000);

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
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton.perform(click());
        closeKeyboard();
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        appCompatSpinner4.perform(click());
        closeKeyboard();
        Thread.sleep(2000);
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
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

        Thread.sleep(1500);
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

        Thread.sleep(1000);

        appCompatCheckBox.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        Thread.sleep(1000);

        appCompatRadioButton.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        Thread.sleep(1000);

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
        Thread.sleep(1000);

        textView4.check(matches(withText("Procure uma propriedade")));
        closeKeyboard();
    }

    @Test
    public void animalCadastroActivityTest3() throws Exception {//Cadastro e animal de mesmo id em propriedade diferente
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        int idProprietario = repositorioProprietario.buscarProprietario("04998517490").getId();

        Propriedade propriedade = new Propriedade("Propriedade 2", "87999999999", "Rua da Esperança",
                "Heliopolis", "55295-390", "Garanhuns", "Pernambuco", "213", idProprietario, 1);

        repositorioPropriedade.inserirPropriedade(propriedade);

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        Thread.sleep(1000);

        floatingActionButton.perform(click());

        closeKeyboard();

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        Thread.sleep(1000);

        appCompatSpinner4.perform(click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 2"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                2),
                        isDisplayed()));
        Thread.sleep(1000);

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

        Thread.sleep(1500);
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        Thread.sleep(1000);

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
        Thread.sleep(1000);

        appCompatCheckBox.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.rb1), withText("1"),
                        withParent(withId(R.id.rgEec))));
        Thread.sleep(1000);

        appCompatRadioButton.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar)));
        Thread.sleep(1000);

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
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        Thread.sleep(1000);

        floatingActionButton.perform(click());
        closeKeyboard();
        pressBack();

        Thread.sleep(1500);
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        closeKeyboard();
        Thread.sleep(1000);

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
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
                        withParent(allOf(withId(R.id.telaListaAnimais),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        Thread.sleep(1000);

        floatingActionButton.perform(click());
        closeKeyboard();
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinnerPropriedade), isDisplayed()));
        Thread.sleep(1000);

        appCompatSpinner4.perform(click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Propriedade 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        Thread.sleep(1000);

        appCompatCheckedTextView2.perform(click());
        closeKeyboard();
        pressBack();
        Thread.sleep(1500);

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnConfimarDados), withText("Confirmar dados"),
                        withParent(allOf(withId(R.id.fragmentDadosAnimal),
                                withParent(withId(R.id.pager)))),
                        isDisplayed()));
        closeKeyboard();
        Thread.sleep(1000);

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
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_add_animais),
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

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        abrirMenu();
        clicarItemMenu(5);
    }

}
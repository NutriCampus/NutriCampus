package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by jorge on 08/08/17.
 */
@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioExcluirActivityTest extends AbstractPreparacaoTestes {

    public Activity currentActivity;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void deletarUsuarioSemRegistrosActivityTest() {

        prepararTesteUsuario();

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(R.id.fragment_config))),
                        0),
                        isDisplayed()));
        linearLayout4.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.acao_delete), withContentDescription("Excluir"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.message), withText("Excluir usuário"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Excluir usuário")));

        ViewInteraction button = onView(
                allOf(withId(android.R.id.button1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.input_usuario),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.input_senha),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText6.check(matches(withText("")));
    }

    @Test
    public void deletarUsuarioComRegistrosActivityTest() throws Exception {

        prepararTesteUsuario();
        cadastrarProprietariosParaTeste();
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        ViewInteraction linearLayout = onView(
                allOf(withClassName(is("android.widget.LinearLayout")),
                        withParent(allOf(withId(R.id.material_drawer_sticky_footer),
                                withParent(withId(R.id.material_drawer_slider_layout)))),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(R.id.fragment_config))),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.acao_delete), withContentDescription("Excluir"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.message), withText("Excluir usuário"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Excluir usuário")));

        ViewInteraction button = onView(
                allOf(withId(android.R.id.button1),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.input_usuario),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.input_senha),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText6.check(matches(withText("")));
    }

    private void prepararTesteUsuario() {

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.rlayout_faca_login),
                        withParent(withId(R.id.tela_loginactivity)),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edtNome), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.edtNome), isDisplayed()));
        appCompatEditText2.perform(replaceText("Jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edtCpf), isDisplayed()));
        appCompatEditText6.perform(replaceText("049.985.174-90"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edtRegistro), isDisplayed()));
        appCompatEditText7.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edtEmail), isDisplayed()));
        appCompatEditText8.perform(replaceText("jvsveloso@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edtSenha), isDisplayed()));
        appCompatEditText9.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.edtSenha), withText("12345"), isDisplayed()));
        appCompatEditText10.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Salvar"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText12.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText13.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.input_senha), withText("12345"), isDisplayed()));
        appCompatEditText14.perform(pressImeActionButton());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private void cadastrarProprietariosParaTeste() throws Exception {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(allOf(withId(R.id.material_drawer_slider_layout),
                                withParent(withId(R.id.material_drawer_layout)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));

        Proprietario proprietario = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com.com", "(99) 99999 9999");
        repositorioProprietario.inserirProprietario(proprietario);

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


        Thread.sleep(1400);
        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        Thread.sleep(1000);

        appCompatSpinner.perform(scrollTo(), click());
        closeKeyboard();
        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Jorge Veloso"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        Thread.sleep(1000);

        appCompatCheckedTextView.perform(click());
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
        Thread.sleep(2000);
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
        Thread.sleep(2000);
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
        Thread.sleep(3000);
        onView(withContentDescription("Navigate up")).perform(click());

    }
}

package com.nutricampus.app.acceptance;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
public class UsuarioAtualizarActivityTest extends AbstractPreparacaoTestes {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void atualizarUsuarioCamposVaziosActivityTest() throws InterruptedException {

        prepararTesteUsuario();
        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.edtNome), withText("Jorge"), isDisplayed()));
        appCompatEditText15.perform(click());
        closeKeyboard();
        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.edtNome), withText("Jorge"), isDisplayed()));
        appCompatEditText16.perform(replaceText(""), closeSoftKeyboard());
        closeKeyboard();
        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.edtNome), isDisplayed()));
        appCompatEditText17.perform(pressImeActionButton());
        closeKeyboard();
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton3.perform(click());
        closeKeyboard();
        ViewInteraction editText = onView(
                allOf(withId(R.id.edtNome),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("")));
        closeKeyboard();
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.edtCpf), withText("049.985.174-90"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("049.985.174-90")));
        closeKeyboard();
        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.edtNome), isDisplayed()));
        appCompatEditText18.perform(replaceText("Jorge"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton10.perform(click());
        posTesteUsuarioAtualizar();
    }

    @Test
    public void atualizarUsuarioAlterarSenhaActivityTest() {

        prepararTesteUsuario();
        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.edtSenha), withText("12345"), isDisplayed()));
        appCompatEditText19.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_salvar_cadastro), withText("Atualizar"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.input_usuario),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.input_senha),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("")));

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText20.perform(click());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.input_usuario), isDisplayed()));
        appCompatEditText21.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.input_senha), isDisplayed()));
        appCompatEditText22.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btn_login), withText("Entrar"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.inicio), withText("Bem vindo ao NutriCampus!"),
                        childAtPosition(
                                allOf(withId(R.id.include),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Bem vindo ao NutriCampus!")));

        ViewInteraction textView2 = onView(
                allOf(withText("NutriCampus"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("NutriCampus")));

        posTesteUsuarioAtualizar();

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

        ViewInteraction linearLayout = onView(
                allOf(withClassName(is("android.widget.LinearLayout")),
                        withParent(allOf(withId(R.id.material_drawer_sticky_footer),
                                withParent(withId(R.id.material_drawer_slider_layout)))),
                        isDisplayed()));
        linearLayout.perform(click());
        closeKeyboard();
        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(R.id.fragment_config))),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());
    }

    private void posTesteUsuarioAtualizar() {

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(withClassName(is("android.widget.LinearLayout")),
                        withParent(allOf(withId(R.id.material_drawer_sticky_footer),
                                withParent(withId(R.id.material_drawer_slider_layout)))),
                        isDisplayed()));
        linearLayout3.perform(click());

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
        closeKeyboard();
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton6.perform(scrollTo(), click());
        closeKeyboard();
    }
}

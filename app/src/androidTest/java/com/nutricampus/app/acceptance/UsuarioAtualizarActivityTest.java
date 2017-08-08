package com.nutricampus.app.acceptance;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Usuario;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class UsuarioAtualizarActivityTest extends AbstractPreparacaoTestes {

    private int idUsuario;
    private RepositorioUsuario repositorioUsuario;
    private Usuario usuario;

    public void criarUsuario() {
        repositorioUsuario = new RepositorioUsuario(InstrumentationRegistry.getTargetContext());
        usuario = new Usuario("123456", "56671187851", "Jorge", "jorge@mail.com", "123456");
        idUsuario = repositorioUsuario.inserirUsuario(usuario);
    }

    @Before
    public void setUp() throws Exception {
        doLogout();
        criarUsuario();
        SharedPreferencesManager session = new SharedPreferencesManager(InstrumentationRegistry.getTargetContext());
        session.createLoginSession(idUsuario, "Jorge", "jorge@mail.com", "123456", "123456");

        currentActivity.startActivity(new Intent(currentActivity, MainActivity.class));

        abrirMenu();

        clicarItemMenuComTexto("Configurações");

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(R.id.fragment_config))),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());
    }

    @After
    public void deletaDadosPosTestes() {
        repositorioUsuario.removerUsuario(usuario);
    }

    @Test
    public void atualizarSemModificarDados() throws InterruptedException {
        closeKeyboard();
        espera(500);

        clicarBotao(R.id.btn_salvar_cadastro, true);

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Usuário atualizado com sucesso");
            espera(2000);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void atualizarComCamposVazios() throws InterruptedException {

        closeKeyboard();
        onView(allOf(withId(R.id.edtNome))).perform(replaceText(""), closeSoftKeyboard());

        onView(allOf(withId(R.id.edtEmail))).perform(replaceText(""), closeSoftKeyboard());

        onView(allOf(withId(R.id.edtSenha))).perform(replaceText(""), closeSoftKeyboard());

        closeKeyboard();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Campos inválidos");
            espera(2000);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void atualizarSenhaCadastrada() {

        onView(allOf(withId(R.id.edtSenha))).perform(replaceText("123457"), closeSoftKeyboard());
        usuario.setSenha("123457");
        espera(500);
        clicarBotao(R.id.btn_salvar_cadastro, true);

        /*
         Realiza novo login com o usuário e a nova senha para confirmar que a senha foi alterada
         */

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.input_usuario),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(TextInputLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("")));

        onView(allOf(withId(R.id.input_usuario))).perform(replaceText("123456"), closeSoftKeyboard());

        onView(allOf(withId(R.id.input_senha))).perform(replaceText("123457"), closeSoftKeyboard());

        onView(allOf(withId(R.id.btn_login), withText("Entrar"))).perform(click());

        espera(1500);

        onView(withId(R.id.inicio)).check(matches(withText("Bem-vindo ao NutriCampus")));

    }

}

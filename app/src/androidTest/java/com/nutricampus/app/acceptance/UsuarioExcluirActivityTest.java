package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioExcluirActivityTest extends AbstractPreparacaoTestes {

    private int idUsuario;

    @Before
    public void prepararTesteUsuario() throws Exception {
        doLogout();
        criarUsuario();
        realizaLogin();
        abrirMenu();
        clicarItemMenuComTexto("Configurações");

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(R.id.fragment_config))),
                        0),
                        isDisplayed()));
        linearLayout4.perform(click());
    }

    @Test
    public void deletarUsuarioSemRegistrosActivityTest() throws Exception {
        clicarExcluir();
        espera(1000);

        // tenta fazer login e checa se conseguiu
        testaLoginDepoisExclusão();
    }

    @Test
    public void deletarUsuarioComRegistrosActivityTest() throws Exception {
        // Objetos linkados ao usuário
        cadastrarObjetosParaTeste();
        espera(500);

        clicarExcluir();

        // tenta fazer login e checa se conseguiu
        testaLoginDepoisExclusão();

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        assertTrue(repositorioPropriedade.buscarPropriedadesPorUsuario(idUsuario).isEmpty());
    }

    private void clicarExcluir() {

        closeKeyboard();

        clicarBotao(R.id.acao_delete, false);
        espera(500);

        clicarBotao(android.R.id.button1, true);
    }

    // tenta fazer login e checa se conseguiu
    private void testaLoginDepoisExclusão() throws Exception {
        realizaLogin();

        validaToast("Falha no login, usuário ou senha inválidos");
    }

    @Override
    public void realizaLogin() throws Exception {
        substituiTexto(R.id.input_usuario, "123456");
        substituiTexto(R.id.input_senha, "123456");
        closeKeyboard();
        clicarBotao(R.id.btn_login, false);
    }

    public void criarUsuario() {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(InstrumentationRegistry.getTargetContext());
        Usuario usuario = new Usuario("123456", "56671187851", "Jorge", "jorge@mail.com", "123456");
        idUsuario = repositorioUsuario.inserirUsuario(usuario);
    }

    private void cadastrarObjetosParaTeste() {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());

        int idProprietario = repositorioProprietario.inserirProprietario(new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com", "(87) 99999 9999"));
        int idPropriedade = repositorioPropriedade.inserirPropriedade(new Propriedade("Propriedade 1", "87999999999", "Rua da Indepencia",
                "Mundaú", "55290-000", "Garanhuns", "Pernambuco", "213", idProprietario, idUsuario));

        repositorioAnimal.inserirAnimal(new Animal("Mimosa", idPropriedade, Calendar.getInstance(), true, idUsuario));
    }
}

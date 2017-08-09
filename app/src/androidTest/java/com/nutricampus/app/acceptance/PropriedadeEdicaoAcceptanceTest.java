package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class PropriedadeEdicaoAcceptanceTest extends AbstractPreparacaoTestes {

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        espera(500);

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());

        SharedPreferencesManager session = new SharedPreferencesManager(InstrumentationRegistry.getTargetContext());
        int id = session.getIdUsuario().equals("") ? 0 : Integer.parseInt(session.getIdUsuario());

        Proprietario proprietario = new Proprietario("04998517490", "Jorge Veloso", "jvsveloso@gmail.com.com", "(99) 99999 9999");
        int idProprietario = repositorioProprietario.inserirProprietario(proprietario);

        Propriedade propriedade = new Propriedade("Propriedade 1", "87999999999", "Rua da Indepencia",
                "Mundaú", "55290-000", "Garanhuns", "Pernambuco", "213", idProprietario, id);

        if (repositorioPropriedade.buscarPropriedadesPorNome("Propriedade 1", id).size() == 0)
            repositorioPropriedade.inserirPropriedade(propriedade);

        abrirMenu();
        clicarItemMenu(3);
        closeKeyboard();
    }

    @Test
    public void tentaAtualizarCadastroApenasComProprietário() throws Exception {
        espera(1000);

        longClickElemento("Propriedade 1");
        clicarBotao(android.R.id.title, "Editar");

        espera(500);

        substituiTexto(R.id.input_nome_propriedade, "");
        substituiTexto(R.id.input_telefone_propriedade, "");
        substituiTexto(R.id.input_rua, "");
        substituiTexto(R.id.input_bairro, "");
        substituiTexto(R.id.input_numero, "");
        substituiTexto(R.id.input_cep, "");
        substituiTexto(R.id.input_bairro, "");

        onView(withId(R.id.input_cidade)).perform(scrollTo());
        substituiTexto(R.id.input_cidade, "");

        onView(withId(R.id.input_estado)).perform(scrollTo());
        substituiTexto(R.id.input_estado, "");

        espera(500);

        closeKeyboard();

        clicarBotao(R.id.btn_salvar_propriedade, true);

        validaToast("Campos inválidos");
    }

    @Test
    public void tentaAtualizarCadastroSemProprietario() throws Exception {
        espera(1000);
        longClickElemento("Propriedade 1");

        clicarBotao(android.R.id.title, "Editar");

        clicarBotao(R.id.spinner_proprietario, true);

        onView(allOf(withId(android.R.id.text1), withText("Selecione um proprietário"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                0),
                isDisplayed
                        ()))
                .perform(click());
        closeKeyboard();

        espera(1000);

        closeKeyboard();

        clicarBotao(R.id.btn_salvar_propriedade, true);

        validaToast("Campos inválidos");
    }


}


package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;


@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class PropriedadeCadastroAcceptanceTest extends AbstractPreparacaoTestes {

    @Before
    public void setUp() throws Exception {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        if (repositorioProprietario.buscarProprietario("00011122222") == null)
            repositorioProprietario.inserirProprietario(new Proprietario("00011122222", "Proprietario 1", "", ""));

        realizaLogin();
        abrirMenu();
        clicarItemMenu(3);
        fecharTeclado();
        clicarFloatingButtonPropriedade();
    }

    @After
    public void deletaPropriedades() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        Propriedade prop = repositorioPropriedade.buscarPropriedade("Propriedade OMEGA");

        if (prop != null)
            repositorioPropriedade.removerPropriedade(prop);

    }

    @Test
    public void cadastrarComCamposEmBranco() throws Exception {

        espera(500);

        clicarBotao(R.id.btn_salvar_propriedade, true);
        validaToast("Campos inválidos");
    }

    @Test
    public void cadastrarComCamposPreenchidos() throws Exception {

        clicarBotao(R.id.spinner_proprietario, true);

        espera(500);

        onView(
                allOf(withId(android.R.id.text1), withText("Proprietario 1"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()))
                .perform(click());

        fecharTeclado();

        preencheCampos();

        clicarBotao(R.id.btn_salvar_propriedade, true);

        // Usando o meio abaixo já que o Toast não estava sendo identificado pelo check
        try {
            espera(500);

            onView(withText("Propriedade OMEGA")).perform(click());
            pressBack();
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("Não existe essa view");
        }
    }

    @Test
    public void cadastrarSemProprietario() throws Exception {

        preencheCampos();

        fecharTeclado();
        clicarBotao(R.id.btn_salvar_propriedade, true);

        validaToast("Campos inválidos");
    }

    private void preencheCampos() throws Exception {

        substituiTexto(R.id.input_nome_propriedade, "Propriedade OMEGA");
        substituiTexto(R.id.input_telefone_propriedade, "(87) 99999 9999");
        substituiTexto(R.id.input_rua, "Rua da Independencia");
        substituiTexto(R.id.input_bairro, "Mundaú");
        substituiTexto(R.id.input_numero, "213");
        substituiTexto(R.id.input_cep, "55290-000");
        substituiTexto(R.id.input_bairro, "Mundaú");

        espera(300);

        onView(withId(R.id.input_estado))
                .perform(scrollTo())
                .perform(typeText("P"));

        onView(withId(R.id.input_estado))
                .perform(typeTextIntoFocusedView("e"));

        fecharTeclado();
        espera(1000);

        onView(withText("Pernambuco"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.input_estado))
                .check(matches(withText("Pernambuco")));
        fecharTeclado();

        onView(withId(R.id.input_cidade))
                .perform(scrollTo())
                .perform(typeText("Ga"));

        onView(withId(R.id.input_cidade))
                .perform(typeTextIntoFocusedView("ran"));

        fecharTeclado();

        espera(1000);
        onView(withText("Garanhuns"))
                .inRoot(isPlatformPopup())
                .perform(click());
    }

    public void clicarFloatingButtonPropriedade() throws Exception {
        clicarFloatingButton(R.id.fabList);
        clicarFloatingButton(R.id.fabPropriedade);
    }
}


package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Grupo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.action.ViewActions.scrollTo;

@SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@RunWith(AndroidJUnit4.class)
@LargeTest


public class GrupoExcluirActivityTest extends AbstractPreparacaoTestes{
    private RepositorioGrupo repositorioGrupo;
    private RepositorioAnimal repositorioAnimal;
    private Grupo grupo1;
    private Animal animal;
    public void criarGrupo() {
        repositorioGrupo = new RepositorioGrupo(InstrumentationRegistry.getTargetContext());
        grupo1 = new Grupo("Especial", "GrupoCriadoParaTeste", 1);
        repositorioGrupo.inserirGrupo(grupo1);
        animal = new Animal();
    }
    @Before
    public void setUp() throws Exception {
        criarGrupo();
        realizaLogin();
        abrirMenu();
        clicarItemMenu(4);
    }
    @After
    public void deletaDadosPosTestes() {
        repositorioGrupo.removerGrupo(grupo1);
        //repositorioGrupo.removerGrupo(grupo2);
    }
    @Test
    public void excluirGrupoComAnimais() throws Exception {
        espera(500);
        longClickElemento("Especial");
        espera(1000);
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView.perform(click());
        ViewInteraction appCompatButton12 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton12.perform(scrollTo(), click());
        espera(500);
        validaToast("Registro removido com sucesso");
        espera(1000);
        checaView();
        espera(500);
    }
    @Test
    public void excluirGrupoSemAnimais() throws Exception {
        espera(500);
        longClickElemento("Especial");
        espera(1000);
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Excluir"), isDisplayed()));
        appCompatTextView.perform(click());
        ViewInteraction appCompatButton12 = onView(
                allOf(withId(android.R.id.button1), withText("Sim")));
        appCompatButton12.perform(scrollTo(), click());
        espera(500);
        validaToast("Registro removido com sucesso");
        espera(1000);
        checaView();
        espera(500);
    }
    public void checaView(){
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.text_quantidade_encontrados), withText("4 registros encontrados"),
                        childAtPosition(
                                allOf(withId(R.id.resultado_busca_prole),
                                        childAtPosition(
                                                withId(R.id.telaListaDadosCompl),
                                                0)),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("4 registros encontrados")));

    }
}


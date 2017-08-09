package com.nutricampus.app.acceptance;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.entities.CompostosAlimentares;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompostoAlimentarCadastroAcceptanceTest extends AbstractPreparacaoTestes {
    private RepositorioCompostosAlimentares repositorioCompostosAlimentares;
    private String id1;

    @Before
    public void setUp() throws Exception {
        id1 = "identificador123";
        repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(InstrumentationRegistry.getTargetContext());

        realizaLogin();
        abrirMenu();
        clicarItemMenu(6);
        espera(500);

        clicarFloatingButton(R.id.btn_add_composto_alimentar);
    }

    @After
    public void deletaDados() {
        List<CompostosAlimentares> arr = repositorioCompostosAlimentares.buscarTodosCompostos("identificador");
        for (CompostosAlimentares in : arr) {
            repositorioCompostosAlimentares.removerCompostoAlimentar(in);
        }
    }

    @Test
    //TA-01: Cadastrar novos compostos alimentares sem informar seus nutrientes;
    public void cadastrarCompostoSemNutrientesTA1() {

        substituiTexto(R.id.input_composto_identificador, id1);

        espera(500);

        clicarBotao(R.id.btn_salvar_cadastro, true);

        validaToast("Preencha todos os campos");
    }

    @Test
    //TA-02: Cadastrar novos compostos alimentares informando todos os seus nutrientes;
    public void cadastrarCompostoComNutrientesTA2() {

        preencheCampos();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        espera(500);

        // Usando o meio abaixo já que o Toast não estava sendo identificado pelo check
        try {
            espera(500);
            onView(withText(id1)).perform(click());
            pressBack();
            // View is in hierarchy
        } catch (NoMatchingViewException e) {
            // View is not in hierarchy
            fail("Não existe essa view");
        }
    }

    @Test
    //TA-03: Cadastrar novos compostos alimentares que já estejam na base de dados;
    public void cadastrarCompostoComIdentificadorJaExistenteTA3() {

        //Adicionando primeiro composto
        CompostosAlimentares compostosAlimentares =
                new CompostosAlimentares("22", id1, 11, 22, 33, 44, 55, 55, 66, 77, "descrição");
        repositorioCompostosAlimentares.inserirCompostoAlimentar(compostosAlimentares);

        preencheCampos();

        clicarBotao(R.id.btn_salvar_cadastro, true);

        validaToast("Composto já cadastrado!");
    }

    public void preencheCampos() {

        substituiTexto(R.id.input_composto_identificador, id1);
        substituiTexto(R.id.input_composto_tipo, "22");
        substituiTexto(R.id.input_composto_ms, "11");
        substituiTexto(R.id.input_composto_fdn, "22");
        substituiTexto(R.id.input_composto_ee, "33");
        substituiTexto(R.id.input_composto_mm, "44");
        substituiTexto(R.id.input_composto_cnf, "55");
        substituiTexto(R.id.input_composto_pb, "55");
        substituiTexto(R.id.input_composto_ndt, "66");
        substituiTexto(R.id.input_composto_fda, "77");
        substituiTexto(R.id.input_composto_descricao, "descrição");

        espera();
    }
}

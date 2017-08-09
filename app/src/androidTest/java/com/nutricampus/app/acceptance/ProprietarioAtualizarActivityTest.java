package com.nutricampus.app.acceptance;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProprietarioAtualizarActivityTest extends AbstractPreparacaoTestes {

    @Before
    public void setUp() throws Exception {
        realizaLogin();
        //Inicialização de proprietarios
        cadastrarProprietariosParaTeste();

        //Executa operações iniciais antes da busca
        abrirMenu();
        clicarItemMenu(3);
        clicarFloatingButton(R.id.fabList);
        clicarFloatingButton(R.id.fabProprietario);
    }

    @After
    public void deletaDadosPosTeste() {
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
    }


    @Test
    public void atualizarProprietarioCamposVaziosActivityTest() throws Exception {

        longClickElemento("Jorge Veloso");

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_nome_proprietario, "");
        substituiTexto(R.id.input_cpf_proprietario, "");
        substituiTexto(R.id.input_fone_proprietario, "");
        substituiTexto(R.id.input_email_proprietario, "");


        espera(750);

        clicarBotao(R.id.btn_salvar_cadastro, false);

        validaToast("Campos inválidos");
    }

    @Test
    public void atualizarProprietarioCPFJaCadastradoActivityTest() throws Exception {
        longClickElemento("Jorge Veloso");

        clicarBotao(android.R.id.title, "Editar");

        substituiTexto(R.id.input_cpf_proprietario, ("010.925.525-96"));

        clicarBotao(R.id.btn_salvar_cadastro, false);

        validaToast("CPF já cadastrado !");
    }


    private void cadastrarProprietariosParaTeste() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(InstrumentationRegistry.getTargetContext());
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(InstrumentationRegistry.getTargetContext());
        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();

        Proprietario proprietarios[] = new Proprietario[5];

        proprietarios[0] = new Proprietario("049.985.174-90", "Jorge Veloso", "jvsveloso@gmail.com", "(87) 99999 9999");
        proprietarios[1] = new Proprietario("099.159.834-25", "ProprietarioTeste1", "propteste1@gmail.com", "(88) 88888 8888");
        proprietarios[2] = new Proprietario("010.925.525-96", "Lariza", "lariess@gmail.com.com", "(99) 99999 9999");
        proprietarios[3] = new Proprietario("109.674.344-24", "ProprietarioTeste2", "propteste2@gmail.com", "(99) 99999 7777");
        proprietarios[4] = new Proprietario("103.085.914-02", "ProprietarioTeste3", "propteste3@gmail.com", "(99) 99999 6666");

        for (int i = 0; i < 5; i++) {
            repositorioProprietario.inserirProprietario(proprietarios[i]);
        }
    }

}
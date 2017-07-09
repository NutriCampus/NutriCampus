package com.nutricampus.app.instrumented;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Propriedade;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Felipe on 08/07/2017.
 */

public class InstrumentedTestRepositorioPropriedade {

    private Context appContext = InstrumentationRegistry.getTargetContext();
    private RepositorioPropriedade repositorio = new RepositorioPropriedade(appContext);


    @Test
    public void testarInserePropriedadeInexistente() {

        Propriedade propriedade = new Propriedade("propriedade", "1234-5678", "Rua XYZ", "Heliópolis", "55555-555", "Gus", "Pernambuco", "numero", 2);
        int resultNovaPropriedade = repositorio.inserirPropriedade(propriedade);

        boolean resultado = resultNovaPropriedade > 0;

        assertTrue(resultado);
    }

    @Test
    public void testarBuscaDePropriedadePeloNome() {

        String nome = "Fazenda ZZZ";

        Propriedade propriedade = new Propriedade(nome, "1234-5678", "Rua XYZ", "Heliópolis", "55555-555", "Gus", "Pernambuco", "numero", 2);
        int resultNovaPropriedade = repositorio.inserirPropriedade(propriedade);

        Propriedade propriedadeEncontrada = repositorio.buscarPropriedade(nome);

        assertNotNull(propriedadeEncontrada);
    }

    @Test
    public void testarAtualizacaoDeDadosSemIDPropriedade() {
        boolean result;
        Propriedade propriedade = new Propriedade("propriedade", "1234-5678", "Rua XYZ", "Cohab I", "55555-555", "Gus", "Pernambuco", "numero", 2);
        repositorio.inserirPropriedade(propriedade);

        // Testa a atualização em um registro sem passar o id
        Propriedade propriedadeDadosAlterados = new Propriedade("propriedade", "1234-5678", "Rua XYZ", "Cohab II", "99999-999", "Garanhuns", "Pernambuco", "numero", 2);
        result = repositorio.atualizarPropriedade(propriedadeDadosAlterados);

        assertFalse(result);
    }

    @Test
    public void testarAtualizacaoDeDados() {

        boolean result;
        Propriedade propriedade;

        propriedade = new Propriedade("Fazenda", "1234-5678", "Rua XYZ", "Heliópolis", "55555-555", "Gus", "Pernambuco", "numero", 2);
        repositorio.inserirPropriedade(propriedade);

        propriedade.setTelefone("9876-5432");

        Propriedade p = repositorio.buscarPropriedade("Fazenda");
        p.setCidade("Neves");

        result = repositorio.atualizarPropriedade(p);
        assertTrue(result);

        Propriedade propriedadeEncontrada = repositorio.buscarPropriedade("Fazenda");

        // Testa se houver uma atualização no registro no banco
        assertEquals(propriedadeEncontrada.getCidade(), "Neves");
    }

    @Test
    public void testarAtualizacaoDeDadosInexistentes() {

        boolean result;
        Propriedade propriedadeAlterada;

        propriedadeAlterada = new Propriedade("propriedade Q", "1234-5678", "Rua XYZ", "Heliópolis", "55555-555", "Gus", "Pernambuco", "numero", 2);
        result = repositorio.atualizarPropriedade(propriedadeAlterada);

        assertFalse(result);
    }

    @Test
    public void testarBuscarPropriedadesPorNome() {
        String nome = "Faz";
        List<Propriedade> propriedadesList = repositorio.buscarPropriedadesPorNome(nome);

        boolean contem = true;
        for(Propriedade p : propriedadesList) {
            if(!p.getNome().contains("Faz")) {
                contem = false;
                break;
            }
        }

        assertTrue(contem);
    }

    @Test
    public void testarbuscarTodosPropriedades() {
        Propriedade propriedade = new Propriedade("Sítio Vasc", "3761-4040", "Rua Sete", "Cohab III", "77777-777", "Gus", "Pernambuco", "numero", 1);
        repositorio.inserirPropriedade(propriedade);

        repositorio.inserirPropriedade(propriedade);

        List<Propriedade> propriedadeList = repositorio.buscarTodasPropriedades();

        boolean found = false;
        for (Propriedade p : propriedadeList) {
            if (p.getNome().equals("Sítio Vasc") &&
                    p.getTelefone().equals("3761-4040") &&
                    p.getBairro().equals("Cohab III")) {
                found = true;
            }
        }
        assertTrue(found);
    }
}

package com.nutricampus.app.instrumented.repositorios;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.database.RepositorioCidade;
import com.nutricampus.app.entities.Cidade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RepositorioCidadeInstrumentedTest {
    private Context appContext;

    private RepositorioCidade repo;

    @Before
    public void init() {

        this.appContext = InstrumentationRegistry.getTargetContext();

        this.repo = new RepositorioCidade(appContext);
    }

    @Test
    public void getIdCidadePeloNome() throws Exception {

        assertEquals(repo.getIdPeloNome("Água Doce do Norte", 8), 2);
    }

    @Test
    public void getLista() throws Exception {
        List<Cidade> lista = repo.getLista();

        assertNotNull(lista);
        assertTrue(lista.size() > 5000);

    }

    @Test
    public void getListaNome() throws Exception {
        List<String> lista = repo.getListaDeNomes();

        assertNotNull(lista);
        assertTrue(lista.size() > 5000);

    }

    @Test
    public void getListaPorEstado() throws Exception {

        List<Cidade> lista = repo.getListaPorEstado(8);

        assertNotNull(lista);
        assertEquals(repo.getIdPeloNome("Água Doce do Norte", 8), 2);

    }

}
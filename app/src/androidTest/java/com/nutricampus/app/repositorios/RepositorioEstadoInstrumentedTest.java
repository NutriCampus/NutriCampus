package com.nutricampus.app.repositorios;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.database.RepositorioEstado;
import com.nutricampus.app.entities.Estado;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RepositorioEstadoInstrumentedTest {
    private RepositorioEstado repo;

    @Before
    public void init() {

        this.repo = new RepositorioEstado( InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void getIdEstadoPeloNome() throws Exception {

        assertEquals(repo.getIdPeloNome("Pernambuco"), 16);
        assertEquals(repo.getIdPeloNome("Minas Gerais"), 11);

    }

    @Test
    public void getLista() throws Exception {
        List<Estado> lista = repo.getLista();

        assertNotNull(lista);
        assertTrue(lista.size() == 27);
    }

    @Test
    public void getListaNomes() throws Exception {
        List<String> lista = repo.getListaDeNomes();

        assertNotNull(lista);
        assertTrue(lista.size() == 27);
    }

}
package com.nutricampus.app.instrumented.repositorios;

import android.support.test.InstrumentationRegistry;

import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Prole;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kellison on 04/08/17.
 */
public class RepositorioProleTesteIntegracao {
    private RepositorioAnimal repositorioAnimal;
    private RepositorioProle repositorioProle;

    @Before
    public void setUp() throws Exception {
        repositorioProle = new RepositorioProle(InstrumentationRegistry.getTargetContext());
        repositorioAnimal = new RepositorioAnimal(InstrumentationRegistry.getTargetContext());
        repositorioAnimal.inserirAnimal(new Animal("mimosa", 1, Calendar.getInstance(), true, 1));
    }

    @Test
    public void inserirProle() throws Exception {
        int result = repositorioProle.inserirProle(new Prole(1, Calendar.getInstance(), 0.1f, true));
        assertTrue(result > 1);
    }

    @Test
    public void buscarPorId() throws Exception {
        int result = repositorioProle.inserirProle(new Prole(1, Calendar.getInstance(), 0.1f, true));
        Prole prole = repositorioProle.buscarPorId(result);
        assertTrue(0.1f == prole.getPesoDeNascimento());
    }

    @Test
    public void buscarPorMatriz() throws Exception {
        repositorioProle.inserirProle(new Prole(1, Calendar.getInstance(), 0.1f, true));
        List<Prole> prole = repositorioProle.buscarPorMatriz(1);
        assertNotNull(prole);

        List<Prole> prole2 = repositorioProle.buscarPorMatriz(11);
        assertTrue(prole2.isEmpty());

    }

    @Test
    public void buscarPorAnimalPeriodo() throws Exception {
        repositorioProle.inserirProle(new Prole(1, Calendar.getInstance(), 0.1f, true));
        List<Prole> prole = repositorioProle.buscarPorAnimalPeriodo(1, Calendar.MONTH, Calendar.YEAR);
        assertNotNull(prole);
        prole = repositorioProle.buscarPorAnimalPeriodo(1, Calendar.MONTH, Calendar.YEAR + 2);
        assertTrue(prole.isEmpty());

    }

    @Test
    public void atualizarProle() throws Exception {
        Prole prole = new Prole(1, Calendar.getInstance(), 0.1f, true);
        int id = repositorioProle.inserirProle(prole);

        Prole prole2 = repositorioProle.buscarPorId(id);
        prole2.setNatimorto(false);
        repositorioProle.atualizarProle(prole2);

        assertEquals(false, repositorioProle.buscarPorId(id).isNatimorto());
        assertNotEquals(true, repositorioProle.buscarPorId(id).isNatimorto());

    }

    @Test
    public void removerProle() throws Exception {
        int id = repositorioProle.inserirProle(new Prole(1, Calendar.getInstance(), 0.1f, true));

        Prole prole2 = repositorioProle.buscarPorId(id);
        assertNotNull(prole2);

        repositorioProle.removerProle(prole2);

        Prole prole = repositorioProle.buscarPorId(id);
        assertNull(prole);

    }

}
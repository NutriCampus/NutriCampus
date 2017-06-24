package com.nutricampus.app;

import com.nutricampus.app.entities.Dieta;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioDieta {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Dieta p1 = new Dieta();
        assertNotNull(p1);
    }
}
package com.nutricampus.app;

import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioGrupo {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Grupo p1 = new Grupo();
        assertNotNull(p1);
    }
}
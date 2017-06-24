package com.nutricampus.app;

import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioPropriedade {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Propriedade p2 = new Propriedade();
        assertNotNull(p2);

        p2 = new Propriedade();
        assertNotNull(p2);

    }

}
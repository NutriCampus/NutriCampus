package com.nutricampus.app;

import com.nutricampus.app.entities.CompostoAlimentar;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioCompostoAlimentar {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        CompostoAlimentar p1 = new CompostoAlimentar();
        assertNotNull(p1);

        p1 = new CompostoAlimentar();
        assertNotNull(p1);
    }
}
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
public class UnitTestCompostoAlimentar {
    @Test
    public void constructorCompostoAlimentar2Arguments() throws Exception {
        CompostoAlimentar p1 = new CompostoAlimentar("nome", "cpf");
        assertNotNull(p1);

        p1 = new CompostoAlimentar(null, null);
        assertNotNull(p1);
    }

    @Test
    public void constructorCompostoAlimentar3Arguments() throws Exception {
        CompostoAlimentar p1 = new CompostoAlimentar(0, "nome", "cpf");
        assertNotNull(p1);

        p1 = new CompostoAlimentar(0, null, null);
        assertNotNull(p1);
    }
}
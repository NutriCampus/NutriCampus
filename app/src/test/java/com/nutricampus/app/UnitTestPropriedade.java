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
public class UnitTestPropriedade {
    @Test
    public void constructorPropriedade7Arguments() throws Exception {
        Proprietario p1 = new Proprietario(0, "nome", "cpf");
        Propriedade p2 = new Propriedade("cpf", "nome", "cpf", "nome", "cpf", "nome", p1);
        assertNotNull(p2);

        p2 = new Propriedade(null, null, null, null, null, null, p1);
        assertNotNull(p2);

    }

    @Test
    public void constructorPropriedade8Arguments() throws Exception {
        Proprietario p1 = new Proprietario(0, "nome", "cpf");
        Propriedade p2 = new Propriedade(0, "cpf", "nome", "cpf", "nome", "cpf", "nome", p1);
        assertNotNull(p2);

        p2 = new Propriedade(0, null, null, null, null, null, null, p1);
        assertNotNull(p2);
    }
}
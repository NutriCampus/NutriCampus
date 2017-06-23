package com.nutricampus.app;

import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTestProprietario {
    @Test
    public void constructorProprietario2Arguments() throws Exception {
        Proprietario p1 = new Proprietario("nome", "cpf");
        assertNotNull(p1);

        Proprietario p2 = new Proprietario(null, null);
        assertNotNull(p2);
    }

    @Test
    public void constructorProprietario3Arguments() throws Exception {
        Proprietario p1 = new Proprietario(0, "nome","cpf");
        assertNotNull(p1);

        Proprietario p2 = new Proprietario(0, null, null);
        assertNotNull(p2);
    }
}
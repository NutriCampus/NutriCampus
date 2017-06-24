package com.nutricampus.app;

import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioProprietario {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Proprietario p1 = new Proprietario();
        assertNotNull(p1);

        Proprietario p2 = new Proprietario();
        assertNotNull(p2);
    }
}
package com.nutricampus.app;

import com.nutricampus.app.entities.Dieta;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTestAdicao {
    @Test
    public void adicao_estaCorreto1() throws Exception {
        Dieta p = new Dieta();
        assertEquals(4, 2 + 2);
    }

    @Test
    public void adicao_estaCorreto2() throws Exception {
        Dieta p = new Dieta();
        assertEquals(3, 2 + 1);
    }
}
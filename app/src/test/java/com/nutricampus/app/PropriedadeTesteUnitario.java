package com.nutricampus.app;

import com.nutricampus.app.entities.Propriedade;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PropriedadeTesteUnitario {

    private Propriedade p1;
    private Propriedade p2;
    private Propriedade p3;

    @Before
    public void init() {

        p1 = new Propriedade(1, "Nome Um", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        p2 = new Propriedade(1, "Nome Um", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        p3 = new Propriedade(1, "three", "", "nem", "nem",
                "55555-000", "12", "Goiania", "Goias", 2, 3);
    }

    @Test
    public void testaEqualsPropriedadesIguais() throws Exception {

        assertTrue(p1.equals(p2));
    }

    @Test
    public void testaEqualsPropriedadesDiferentes() throws Exception {
        assertFalse(p1.equals(p3));
    }

    @Test
    public void testaEqualsComPropriedadeNull() throws Exception {
        Propriedade propriedade = null;
        assertFalse(p1.equals(propriedade));
    }

    @Test
    public void testaHashCodeIguais() {
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testaHashCodeDiferentes() {
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void testaEqualsOutraInstacia() throws Exception {
        assertFalse(p1.equals(new Object()));
    }

    @Test
    public void testaToString() throws Exception {
        assertTrue("Nome Um".equals(p1.toString()));
    }
}
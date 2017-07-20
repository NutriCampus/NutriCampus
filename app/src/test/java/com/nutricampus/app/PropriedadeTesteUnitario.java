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

    @Before
    public void init() {

        p1 = new Propriedade(1, "Nome Um", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        p2 = new Propriedade(1, "Nome Dois", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
    }

    @Test
    public void testaEqualsPropriedadesIguais() throws Exception {
        String aux = p2.getNome();

        p2.setNome(p1.getNome());

        assertTrue(p1.equals(p2));

        p2.setNome(aux);
    }

    @Test
    public void testaEqualsPropriedadesDiferentes() throws Exception {
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testaEqualsComPropriedadeNull() throws Exception {
        Propriedade p = null;

        assertFalse(p2.equals(p));
    }

    @Test
    public void testaHashCodeIguais() {
        String aux = p2.getNome();

        p2.setNome(p1.getNome());

        assertEquals(p1.hashCode(), p2.hashCode());

        p2.setNome(aux);

    }

    @Test
    public void testaHashCodeDiferentes() {
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

}
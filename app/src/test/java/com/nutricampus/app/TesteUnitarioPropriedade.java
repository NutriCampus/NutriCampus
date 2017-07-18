package com.nutricampus.app;

import com.nutricampus.app.entities.Propriedade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioPropriedade {

    @Test
    public void testarConstrutorComId() throws Exception {

        Propriedade p2 = new Propriedade(1, "Joao", "000000", "Avenida X", "Bairro Y", "00000000", "Cidade Z", "UF", "000A", 1, 10);
        assertNotNull(p2);

        p2 = new Propriedade(2, "Joao", "000000", "Avenida X", "Bairro Y", "00000000", "Cidade Z", "UF", "000A", 1, 10);
        assertNotNull(p2);

    }
    @Test
    public void testarConstrutorSemId() throws Exception {

        Propriedade p2 = new Propriedade("Joao", "000000", "Avenida X", "Bairro Y", "00000000", "Cidade Z", "UF", "000A", 1, 10);
        assertNotNull(p2);

        p2 = new Propriedade("Joao", "000000", "Avenida X", "Bairro Y", "00000000", "Cidade Z", "UF", "000A", 1, 10);
        assertNotNull(p2);

    }


    @Test
    public void testaPropriedadesIguais() throws Exception {
        Propriedade p1 = new Propriedade(1, "Nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        Propriedade p2 = new Propriedade(1, "Nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);

        assertTrue(p1.equals(p2));
    }

    @Test
    public void testaPropriedadesDiferentes() throws Exception {
        Propriedade p1 = new Propriedade(1, "Sem nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        Propriedade p2 = new Propriedade(1, "Nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);

        assertFalse(p1.equals(p2));
    }

    @Test
    public void testaHashCode() {
        Propriedade p1 = new Propriedade(1, "Nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);
        Propriedade p2 = new Propriedade(1, "Nome", "000.000.000-00", "Alguma", "Algum",
                "55555-555", "10", "Garanhuns", "Pernambuco", 1, 1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

}
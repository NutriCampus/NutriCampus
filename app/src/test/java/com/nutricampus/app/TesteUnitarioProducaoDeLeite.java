package com.nutricampus.app;

import com.nutricampus.app.entities.CompostoAlimentar;
import com.nutricampus.app.entities.ProducaoDeLeite;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Diego on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class TesteUnitarioProducaoDeLeite {
    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        ProducaoDeLeite p1 = new ProducaoDeLeite();
        assertNotNull(p1);

        p1 = new ProducaoDeLeite();
        assertNotNull(p1);
    }
}

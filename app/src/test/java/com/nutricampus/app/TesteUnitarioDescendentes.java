package com.nutricampus.app;

import com.nutricampus.app.entities.Descendentes;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Diego on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class TesteUnitarioDescendentes {

    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Descendentes p1 = new Descendentes();
        assertNotNull(p1);

        p1 = new Descendentes();
        assertNotNull(p1);
    }
}

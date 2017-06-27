package com.nutricampus.app;

import com.nutricampus.app.entities.CompostoAlimentar;
import com.nutricampus.app.entities.DadosComplAnimais;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Diego on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class TesteUnitarioDadosComplAnimais {

    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        DadosComplAnimais p1 = new DadosComplAnimais();
        assertNotNull(p1);

        p1 = new DadosComplAnimais();
        assertNotNull(p1);
    }
}

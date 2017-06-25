package com.nutricampus.app;

import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import org.junit.Test;

/**
 * Created by Diego Bezerra on 21/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

import static org.junit.Assert.assertNotNull;

 /**
 * Created by Diego Bezerra on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class TesteUnitarioUsuario {

    @Test
    public void testarConstrutorSemArgumentos() throws Exception {
        Usuario p1 = new Usuario();
        assertNotNull(p1);

        p1 = new Usuario();
        assertNotNull(p1);
    }

    @Test
    public void testarConstrutorCom5Argumentos() throws Exception {
        Usuario p1 = new Usuario("000000-PE", "000000000-00", "nome", "email", "as776jgashf");
        assertNotNull(p1);

        p1 = new Usuario(null, null, null, null, null);
        assertNotNull(p1);
    }

    @Test
    public void testarConstrutorCom6Argumentos() throws Exception {
        Usuario p1 = new Usuario(0, "000000-PE", "000000000-00", "nome", "email", "as776jgashf");
        assertNotNull(p1);

        p1 = new Usuario(0, null, null, null, null, null);
        assertNotNull(p1);
    }
}
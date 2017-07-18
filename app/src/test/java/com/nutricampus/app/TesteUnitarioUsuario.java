package com.nutricampus.app;

import com.nutricampus.app.entities.Usuario;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testaEqualsUsuarioIguais() throws Exception {
        Usuario user1 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");
        Usuario user2 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");

        assertTrue(user1.equals(user2));
    }

    @Test
    public void testaEqualsUsuarioDiferentes() throws Exception {
        Usuario user1 = new Usuario(1, "12345", "000.000.000-00", "SemNome", "nome@email.com", "senha");
        Usuario user2 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");

        assertFalse(user1.equals(user2));
    }

    @Test
    public void testaEqualsComUsuarioNull() throws Exception {
        Usuario user1 = new Usuario(1, "12345", "000.000.000-00", "SemNome", "nome@email.com", "senha");
        Usuario user2 = null;

        assertFalse(user1.equals(user2));
    }

    @Test
    public void testaHashCodeIguais() {
        Usuario user1 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");
        Usuario user2 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");


        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testaHashCodediferentes() {
        Usuario user1 = new Usuario(1, "00", "777.888.999-00", "Nome", "nome@email.com", "senha");
        Usuario user2 = new Usuario(1, "12345", "000.000.000-00", "Nome", "nome@email.com", "senha");


        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
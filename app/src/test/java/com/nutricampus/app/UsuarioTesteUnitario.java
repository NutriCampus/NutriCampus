package com.nutricampus.app;

import com.nutricampus.app.entities.Usuario;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diego Bezerra on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class UsuarioTesteUnitario {

    private Usuario user1;
    private Usuario user2;

    @Before
    public void init() {
        user1 = new Usuario(1, "12345", "000.000.000-00", "Nome Um", "nome@email.com", "senha");
        user2 = new Usuario(1, "12345", "000.000.000-00", "Nome Dois", "nome@email.com", "senha");
    }

    @Test
    public void testaEqualsUsuarioIguais() throws Exception {
        String aux = user1.getNome();
        user1.setNome(user2.getNome());
        assertTrue(user1.equals(user2));
        user1.setNome(aux);
    }

    @Test
    public void testaEqualsUsuarioDiferentes() throws Exception {
        assertFalse(user1.equals(user2));
    }

    @Test
    public void testaEqualsComUsuarioNull() throws Exception {
        Usuario user = null;

        assertFalse(user1.equals(user));
    }

    @Test
    public void testaHashCodeIguais() {
        String aux = user1.getNome();
        user1.setNome(user2.getNome());
        assertEquals(user1.hashCode(), user2.hashCode());
        user1.setNome(aux);
    }

    @Test
    public void testaHashCodediferentes() {
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
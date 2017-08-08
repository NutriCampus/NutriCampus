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
    private Usuario user3;

    @Before
    public void init() {
        user1 = new Usuario(1, "12345", "000.000.000-00", "Nome Um", "nome@email.com", "senha");
        user3 = new Usuario(1, "12345", "000.000.000-00", "Nome Um", "nome@email.com", "senha");
        user2 = new Usuario(1, "12346", "000.990.000-01", "Dois", "ne@e.com", "password11");
    }

    @Test
    public void testaEqualsUsuarioIguais() throws Exception {
        assertTrue(user1.equals(user3));
    }

    @Test
    public void testaEqualsUsuarioDiferentes() throws Exception {
        assertFalse(user2.equals(user1));
    }

    @Test
    public void testaEqualsComUsuarioNull() throws Exception {
        Usuario user = null;
        assertFalse(user1.equals(user));
    }

    @Test
    public void testaEqualsOutraInstacia() throws Exception {
        assertFalse(user1.equals(new Object()));
    }

    @Test
    public void testaHashCodeIguais() {
        assertEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    public void testaHashCodediferentes() {
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testaToString() {

        String str = "Usuario{" +
                "ID: " + user1.getId() +
                ", crmv/CRZ: '" + user1.getCrmv() + '\'' +
                ", CPF: '" + user1.getCpf() + '\'' +
                ", Nome: '" + user1.getNome() + '\'' +
                ", Email: '" + user1.getEmail() + '\'' +
                ", Senha: '" + user1.getSenha() + '\'' +
                '}';

        assertEquals(str, user1.toString());
    }
}
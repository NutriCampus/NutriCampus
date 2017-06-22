package com.nutricampus.app;

import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTestUsuario {
    @Test
    public void constructorUsuario5Arguments() throws Exception {
        Usuario p1 = new Usuario("nome", "cpf", "senha", "nome", "registro");
        assertNotNull(p1);

        p1 = new Usuario(null, null, null, null, null);
        assertNotNull(p1);
    }

    @Test
    public void constructorUsuario6Arguments() throws Exception {
        Usuario p1 = new Usuario(0, "nome", "cpf", "senha", "nome", "registro");
        assertNotNull(p1);

        p1 = new Usuario(0, null, null, null, null, null);
        assertNotNull(p1);
    }
}
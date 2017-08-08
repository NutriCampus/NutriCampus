package com.nutricampus.app.repositorios;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.database.SharedPreferencesManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Diego Bezerra on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedTestSharedPreferencesManager {
    private Context appContext = InstrumentationRegistry.getTargetContext();
    private SharedPreferencesManager session = new SharedPreferencesManager(appContext);

    @Test
    public void testarCriarSessaoLogin() {
        session.createLoginSession(1, "usuario", "usuario@mail.com", "123456", "123456");

        assertTrue(session.isLoggedIn());
    }

    @Test
    public void testarRealizarLogout() {
        session.createLoginSession(1, "usuario", "usuario@mail.com", "123456", "123456");
        session.logoutUser();

        assertFalse(session.isLoggedIn());
    }

    @Test
    public void testarSetarUsuarioNC() {
        session.setUsuario("teste123");
        assertEquals("teste123", session.getUsuario());
    }

    @Test
    public void testarSetarSenhaNC() {
        session.setSenha("senha123");
        assertEquals("senha123", session.getSenha());
    }

    @Test
    public void testarSetarIdNC() {
        session.setIdUsuario("9999");
        assertEquals("9999", session.getIdUsuario());
    }

    @Test
    public void testarSetarEmailNC() {
        session.setEmail("testarSetarEmailNC@email.com");
        assertEquals("testarSetarEmailNC@email.com", session.getEmail());
    }

    @Test
    public void testarSetarCrmvNC() {
        session.setCrmvNC("123456");
        assertEquals("123456", session.getCrmvNC());
    }

}
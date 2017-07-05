package com.nutricampus.app.instrumented;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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
        session.createLoginSession(1, "usuario", "usuario@mail.com", "123456");

        assertTrue(session.isLoggedIn());
    }

    @Test
    public void testarRealizarLogout() {
        session.createLoginSession(1, "usuario", "usuario@mail.com", "123456");
        session.logoutUser();

        assertFalse(session.isLoggedIn());
    }

    @Test
    public void testarSetarUsuarioNC() {
        session.setUsuarioNC("teste123");
        assertEquals("teste123", session.getUsuarioNC());
    }

    @Test
    public void testarSetarSenhaNC() {
        session.setSenhaNC("senha123");
        assertEquals("senha123", session.getSenhaNC());
    }

    @Test
    public void testarSetarIdNC() {
        session.setIdNC("9999");
        assertEquals("9999", session.getIdNC());
    }

    @Test
    public void testarSetarEmailNC() {
        session.setEmailNC("testarSetarEmailNC@email.com");
        assertEquals("testarSetarEmailNC@email.com", session.getEmailNC());
    }

}
package com.nutricampus.app;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Created by Diego Bezerra on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
public class TesteIntegracaoRepositorioUsuario {

    @Test
    public void testBuscaDeUsuarioPeloCRMV(){
        String crmv = "0123";
        RepositorioUsuario repositorio = new RepositorioUsuario(new Activity().getBaseContext());

        Usuario usuarioAdicionado = new Usuario(crmv,"000.000.000-00","usuario","usuario@email.com","123456");
        repositorio.inserirUsuario(usuarioAdicionado);

        Usuario usuarioEncontrado = repositorio.buscarUsuario(crmv);

        assertEquals(usuarioAdicionado,usuarioEncontrado);
    }
}

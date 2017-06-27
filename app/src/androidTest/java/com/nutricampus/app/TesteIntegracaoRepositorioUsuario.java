package com.nutricampus.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by Diego Bezerra on 22/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
public class TesteIntegracaoRepositorioUsuario {
    private Context appContext = InstrumentationRegistry.getTargetContext();
    private RepositorioUsuario repositorio = new RepositorioUsuario(appContext);


    @Test
    public void testarInsereUsuarioInexistente() {
        boolean resultNovoUsuario;

        Usuario usuarioAdicionado = new Usuario("55555", "222.222.222-22", "usuario", "usuario@email.com", "123456");
        resultNovoUsuario = repositorio.inserirUsuario(usuarioAdicionado);

        assertTrue(resultNovoUsuario);
    }

    @Test
    public void testarInsereUsuarioExistente() {
        boolean resultUsuarioJaExistente;

        Usuario usuarioAdicionado = new Usuario("0000", "000.000.000-00", "usuario", "usuario@email.com", "123456");
        repositorio.inserirUsuario(usuarioAdicionado);
        Usuario usuarioEncontrado = repositorio.buscarUsuario("0000");

        resultUsuarioJaExistente = repositorio.inserirUsuario(usuarioEncontrado);

        assertFalse(resultUsuarioJaExistente);
    }

    @Test
    public void testarBuscaDeUsuarioPeloCrmv() {
        String crmv = "0123";

        Usuario usuarioAdicionado = new Usuario(crmv, "111.111.111-11", "usuario", "usuario@email.com", "123456");
        repositorio.inserirUsuario(usuarioAdicionado);

        Usuario usuarioEncontrado = repositorio.buscarUsuario(crmv);

        assertEquals(usuarioAdicionado, usuarioEncontrado);
    }

    @Test
    public void testarBuscaDeUsuarioPelaSenhaECrmv() {
        String crmv = "0123";
        String senha = "123456";

        Usuario usuarioAdicionado = new Usuario(crmv, "111.111.111-11", "usuario", "usuario@email.com", senha);
        repositorio.inserirUsuario(usuarioAdicionado);

        Usuario usuarioEncontrado = repositorio.buscarUsuario(crmv, senha);

        assertEquals(usuarioAdicionado, usuarioEncontrado);

    }

    @Test
    public void testarAtualizacaoDeDadosSemIDUsuario() {
        boolean result;
        Usuario usuarioAdicionado = new Usuario("0123", "111.111.111-11", "usuario", "usuario@email.com", "123456");
        repositorio.inserirUsuario(usuarioAdicionado);

        // Testa a atualização em um registro esem passar o id
        Usuario usuarioDadosAlterados = new Usuario("0123", "111.111.111-11", "NOME", "user@email.com", "000000");
        result = repositorio.atualizarUsuario(usuarioDadosAlterados);

        assertFalse(result);
    }

    @Test
    public void testarAtualizacaoDeDadosComId() {

        boolean resultUsuarioInexistente, result;
        Usuario usuarioEncontrado, usuarioDadosAlterados;

        Usuario usuarioAdicionado = new Usuario("0123", "111.111.111-11", "usuario", "usuario@email.com", "123456");
        repositorio.inserirUsuario(usuarioAdicionado);

        usuarioDadosAlterados = repositorio.buscarUsuario("0123");
        usuarioDadosAlterados.setNome("NOME DE ALMEIDA");
        result = repositorio.atualizarUsuario(usuarioDadosAlterados);

        // Testa o resultado do método atualizarUsuario
        assertTrue(result);

        usuarioEncontrado = repositorio.buscarUsuario("0123");

        // Testa se houver uma atualização no registro no banco
        assertNotEquals(usuarioEncontrado.getNome(), usuarioAdicionado.getNome());
    }

    @Test
    public void testarAtualizacaoDeDadosInexistentes() {

        boolean resultUsuarioInexistente;
        Usuario usuarioDadosAlterados;

        // Testa a atualização em um registro que não existe
        usuarioDadosAlterados = new Usuario("0999999", "111.111.111-11", "NOME", "user@email.com", "000000");
        resultUsuarioInexistente = repositorio.atualizarUsuario(usuarioDadosAlterados);

        assertFalse(resultUsuarioInexistente);

    }

    @Test
    public void testarRemocaoUsuario() {
        String crmv = "0123";
        Usuario usuarioEncontrado, usuarioAdicionado;

        usuarioAdicionado = new Usuario(crmv, "111.111.111-11", "usuario", "usuario@email.com", "123456");
        repositorio.inserirUsuario(usuarioAdicionado);

        usuarioEncontrado = repositorio.buscarUsuario(crmv);

        // Testa remoção de usuário existente
        repositorio.removerUsuario(usuarioEncontrado);

        usuarioEncontrado = repositorio.buscarUsuario(crmv);

        assertNull(usuarioEncontrado);

    }
}
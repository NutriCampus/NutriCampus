package com.nutricampus.app.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)


public class ValidaFormularioInstrumentedTest {
    private Context appContext;
    private TextView nome;
    private TextView sobrenome;


    @Before
    public void init() {
        this.appContext = InstrumentationRegistry.getTargetContext();

        nome = new TextView(appContext);
        sobrenome = new TextView(appContext);
    }

    @Test
    public void testaCamposTextosVazios() throws Exception {

        nome.setText("");
        sobrenome.setText("");

        List<TextView> views = new ArrayList<>();
        views.add(nome);
        views.add(sobrenome);

        List<TextView> result = ValidaFormulario.camposTextosVazios(views);

        assertEquals(2, result.size());

    }

    @Test
    public void testaCamposTextosNaoVazios() throws Exception {

        nome.setText("Joao");
        sobrenome.setText("Maria");

        List<TextView> views = new ArrayList<>();
        views.add(nome);
        views.add(sobrenome);

        List<TextView> result = ValidaFormulario.camposTextosVazios(views);

        assertEquals(0, result.size());

    }

    @Test
    public void testaCEPValido() throws Exception {
        assertTrue(ValidaFormulario.isCEPValido("55250-000"));
        assertTrue(ValidaFormulario.isCEPValido("55250000"));
    }

    @Test
    public void testaCEPInvalido() throws Exception {
        assertFalse(ValidaFormulario.isCEPValido("5525000"));
    }

    @Test
    public void testaTelefoneValido() throws Exception {
        assertTrue(ValidaFormulario.isTelefoneValido("(87) 99999 9999"));
        assertTrue(ValidaFormulario.isTelefoneValido("(87) 9999 9999"));
        assertTrue(ValidaFormulario.isTelefoneValido("87999999999"));
    }

    @Test
    public void testaTelefoneInvalido() throws Exception {
        assertFalse(ValidaFormulario.isTelefoneValido("(87) 99999"));
        assertFalse(ValidaFormulario.isTelefoneValido("1"));
        assertFalse(ValidaFormulario.isTelefoneValido("(87) 9999 999"));
        assertFalse(ValidaFormulario.isTelefoneValido("999999999"));
    }

    @Test
    public void isSelecaoValida() throws Exception {
        assertFalse(ValidaFormulario.isSelecaoValida(0, 0));
        assertTrue(ValidaFormulario.isSelecaoValida(1, 0));
    }

    @Test
    public void validarCpf() throws Exception {
        boolean valido;

        valido = ValidaFormulario.validarCpf("091.653.694-75");
        assertTrue(valido);


        valido = ValidaFormulario.validarCpf("091.653.694-70");
        assertFalse(valido);

    }

    @Test
    public void testaDefinicaoDeValidadeDeCampoFalse() throws Exception {
        boolean valido = ValidaFormulario.defineStatusCampo(nome, "Nome inválido");

        assertEquals("Nome inválido", nome.getError());
        assertFalse(valido);

    }


    @Test
    public void testaDefinirStatusCampo() throws Exception {

    }



}
package com.nutricampus.app;

import com.nutricampus.app.utils.Mascara;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class MacaraTesteUnitario {

    @Test
    public void testeCaracterESinal() {
        assertTrue(Mascara.isSinal('.'));
        assertTrue(Mascara.isSinal(' '));
        assertTrue(Mascara.isSinal('-'));
        assertTrue(Mascara.isSinal('/'));
        assertTrue(Mascara.isSinal(','));
        assertTrue(Mascara.isSinal(')'));
        assertTrue(Mascara.isSinal('('));
    }

    @Test
    public void testeCharacterNotIsSign() {
        boolean isSinal = Mascara.isSinal('c');
        assertFalse(isSinal);
    }

    @Test
    public void testeMascararString() {
        String result;

        String cpf = "08899977763";
        result = Mascara.mascarar(Mascara.CPF_MASK, cpf);
        assertEquals(result, "088.999.777-63", result);

        String telefone = "87999999999";
        result = Mascara.mascarar(Mascara.CELULAR_MASK, telefone);
        assertEquals(result, "(87) 99999 9999", result);

        String cep = "55250500";
        result = Mascara.mascarar(Mascara.CEP_MASK, cep);
        assertEquals(result, "55250-500", result);
    }

    @Test
    public void testeDesmascararString() {
        String cpf = "000.111.222-33";
        assertEquals("00011122233", Mascara.unmask(cpf));

        String telefone = "(87) 98888 8888";
        assertEquals("87988888888", Mascara.unmask(telefone));

        String cep = "55000-250";
        assertEquals("55000250", Mascara.unmask(cep));
    }
}

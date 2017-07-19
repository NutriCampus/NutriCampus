package com.nutricampus.app;

import com.nutricampus.app.utils.Mascara;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class TesteUnitarioMascara {

    @Test
    public void testeCaracterESinal() {
        boolean isSinal = Mascara.isSinal('.');
        assertTrue(isSinal);
    }

    @Test
    public void testeCharacterNotIsSign() {
        boolean isSinal = Mascara.isSinal('c');
        assertFalse(isSinal);
    }

    @Test
    public void testeMascararString() {
        String cpf = "08899977763";
        assertEquals("088.999.777-63", Mascara.mascarar(Mascara.CPF_MASK, cpf));

        String telefone = "87999999999";
        assertEquals("(87) 99999 9999", Mascara.mascarar(Mascara.CELULAR_MASK, telefone));

        String cep = "55250500";
        assertEquals("55250-500", Mascara.mascarar(Mascara.CEP_MASK, cep));
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

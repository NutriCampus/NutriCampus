package com.nutricampus.app;

import com.nutricampus.app.model.Mascara;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Felipe on 23/06/2017.
 */

public class TesteUnitarioMascara {

    @Test
    public void testeCpfValido() {
        boolean valido = Mascara.validarCpf("091.653.694-75");
        assertTrue(valido);
    }

    @Test
    public void testeCpfInvalido() {
        boolean valido = Mascara.validarCpf("091.653.694-70");
        assertFalse(valido);
    }

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
}

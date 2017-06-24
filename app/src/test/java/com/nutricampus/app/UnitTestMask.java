package com.nutricampus.app;

import com.nutricampus.app.model.Mask;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Felipe on 23/06/2017.
 */

public class UnitTestMask {

    @Test
    public void testCpfValid() {
        boolean valid = Mask.validateCpf("091.653.694-75");
        assertTrue(valid);
    }

    @Test
    public void testCpfInvalid() {
        boolean valid = Mask.validateCpf("091.653.694-70");
        assertFalse(valid);
    }

    @Test
    public void testCharacterIsSign() {
        boolean isASign = Mask.isASign('.');
        assertTrue(isASign);
    }

    @Test
    public void testCharacterNotIsSign() {
        boolean isASign = Mask.isASign('c');
        assertFalse(isASign);
    }
}

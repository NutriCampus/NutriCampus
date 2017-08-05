package com.nutricampus.app;

import com.nutricampus.app.utils.Conversor;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class ConversorTesteUnitario {

    @Test
    public void testaConstrutorPrivate() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor constructor = Conversor.class.getDeclaredConstructor();
        assertTrue("Construtor não é privado", Modifier.isPrivate(constructor.getModifiers()));

    }

    @Test
    public void booleanToInt() throws Exception {

        assertEquals(0, Conversor.booleanToInt(false));
        assertEquals(1, Conversor.booleanToInt(true));
        assertFalse(1 == Conversor.booleanToInt(false));
    }

    @Test
    public void intToBoolean() throws Exception {

        assertTrue(Conversor.intToBoolean(1));
        assertFalse(Conversor.intToBoolean(0));
        assertFalse(Conversor.intToBoolean(2));
    }

    @Test
    public void stringToBoolean() throws Exception {
        assertTrue(Conversor.stringToBoolean("true"));
        assertFalse(Conversor.stringToBoolean("false"));
    }

    @Test
    public void stringToDate() throws Exception {
        Calendar cal = Calendar.getInstance();
        String data1 = "10/08/1992";

        cal.setTimeInMillis(Conversor.stringToDate(data1).getTime());

        int month = cal.get(Calendar.MONTH);

        assertEquals(month, 7);
        assertNull(Conversor.stringToDate("JOAO---2"));
    }

    @Test
    public void dataFormatada() throws Exception {
        Calendar cal = Calendar.getInstance();

        cal.set(2015, 10, 22);
        assertTrue(("22/11/2015".equals(Conversor.dataFormatada(cal))));

        cal.set(2000, 0, 02);
        assertTrue(("02/01/2000".equals(Conversor.dataFormatada(cal))));
    }

    @Test
    public void mesParaNumero() throws Exception {
        int mes = Conversor.mesStringParaInt("janeiro");
        assertEquals(0, mes);
        mes = Conversor.mesStringParaInt("JAneirO");
        assertEquals(0, mes);
        mes = Conversor.mesStringParaInt("qualquer");
        assertEquals(12, mes);
    }

}
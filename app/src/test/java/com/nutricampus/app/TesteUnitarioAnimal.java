package com.nutricampus.app;

import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioAnimal {
    @Test
    public void constructorAnimal5Arguments() throws Exception {
        Animal p1 = new Animal("nome", "cpf", "nome", "cpf", "nome");
        assertNotNull(p1);

        p1 = new Animal(null, null, null, null, null);
        assertNotNull(p1);
    }

    @Test
    public void constructorAnima6Arguments() throws Exception {
        Animal p1 = new Animal(0, "nome", "cpf", "nome", "cpf", "nome");
        assertNotNull(p1);

        p1 = new Animal(0, null, null, null, null, null);
        assertNotNull(p1);
    }
}
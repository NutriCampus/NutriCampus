package com.nutricampus.app;

import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TesteUnitarioPropriedade {
    @Test
    public void testarConstrutorComId() throws Exception {

        Propriedade p2 = new Propriedade(1,"Joao","000000","Avenida X","Bairro Y","00000000","Cidade Z","UF", "000A");
        assertNotNull(p2);

        p2 =  new Propriedade(2,"Joao","000000","Avenida X","Bairro Y","00000000","Cidade Z","UF", "000A");
        assertNotNull(p2);

    }
    @Test
    public void testarConstrutorSemId() throws Exception {

        Propriedade p2 = new Propriedade("Joao","000000","Avenida X","Bairro Y","00000000","Cidade Z","UF", "000A");
        assertNotNull(p2);

        p2 =  new Propriedade("Joao","000000","Avenida X","Bairro Y","00000000","Cidade Z","UF", "000A");
        assertNotNull(p2);

    }



}
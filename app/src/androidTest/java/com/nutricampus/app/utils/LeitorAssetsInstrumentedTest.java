package com.nutricampus.app.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LeitorAssetsInstrumentedTest {
    private Context appContext;

    @Before
    public void init() {
        this.appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void carregaJSONAssets() throws Exception {
        String conteudo = LeitorAssets.carregaJSONAssets("estados.json", appContext);
        assertTrue(conteudo.length() > 2);
    }
}
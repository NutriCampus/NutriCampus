package com.nutricampus.app.instrumented.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.RecuperarSenhaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Mateus on 27/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedTestRecuperarSenhaActivity {
    @Rule
    public ActivityTestRule<RecuperarSenhaActivity> rule = new ActivityTestRule<>(RecuperarSenhaActivity.class);

    @Test
    public void ensureViewIsShowing() throws Exception {
        RecuperarSenhaActivity activity = rule.getActivity();
        EditText login = (EditText)activity.findViewById(R.id.input_usuario_recupera);
        assertNotNull(login);

        AppCompatButton btn_recuperar = (AppCompatButton)activity.findViewById(R.id.btn_recuperar);
        assertNotNull(btn_recuperar);
    }
}

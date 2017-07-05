package com.nutricampus.app.instrumented;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;

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
public class InstrumentedTestLoginActivity {
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureViewIsShowing() throws Exception {
        LoginActivity activity = rule.getActivity();
        EditText login = (EditText)activity.findViewById(R.id.input_usuario);
        assertNotNull(login);

        EditText password = (EditText)activity.findViewById(R.id.input_senha);
        assertNotNull(password);

        AppCompatButton btn_login = (AppCompatButton)activity.findViewById(R.id.btn_login);
        assertNotNull(btn_login);
    }
}

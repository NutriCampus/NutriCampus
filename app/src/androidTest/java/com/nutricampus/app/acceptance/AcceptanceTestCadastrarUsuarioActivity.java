package com.nutricampus.app.acceptance;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarUsuarioActivity;
import com.nutricampus.app.activities.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Mateus on 29/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestCadastrarUsuarioActivity {

    @Rule
    public ActivityTestRule<CadastrarUsuarioActivity> mActivityRule = new ActivityTestRule<>(
            CadastrarUsuarioActivity.class);

    @Before
    public void unlockScreen() {
        final CadastrarUsuarioActivity activity = mActivityRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void attempToCreateAccountSuccessfully() throws Exception {
        onView(withId(R.id.edtNome))
                .perform(typeText("attempToCreateAccountSuccessfully"));
        closeKeyboard();
        onView(withId(R.id.edtCpf))
                .perform(typeText("44502396605"));
        closeKeyboard();
        onView(withId(R.id.edtRegistro))
                .perform(typeText("44502396605"));
        closeKeyboard();
        onView(withId(R.id.edtEmail))
                .perform(typeText("attempToCreateAccountSuccessfully@email.com"));
        closeKeyboard();
        onView(withId(R.id.edtSenha))
                .perform(typeText("12345"));
        closeKeyboard();

        onView(withId(R.id.btn_salvar_cadastro)).perform(click());

        //Check por dialog https://stackoverflow.com/questions/21045509/check-if-a-dialog-is-displayed-with-espresso
        onView(withText("Cadastro")).check(matches(isDisplayed()));

        Thread.sleep(3000);
    }

    public void closeKeyboard() throws Exception {
        closeSoftKeyboard();
        Thread.sleep(1000);
    }
}

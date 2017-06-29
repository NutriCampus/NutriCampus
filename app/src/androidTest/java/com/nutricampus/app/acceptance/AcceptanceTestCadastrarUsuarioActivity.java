package com.nutricampus.app.acceptance;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarUsuarioActivity;

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

    @Test
    public void attempToCreatAccountSuccessfully() throws Exception {
        onView(withId(R.id.edtNome))
                .perform(typeText("Vinicius attempToCreatAccountSuccessfully"));
        closeSoftKeyboard();
        onView(withId(R.id.edtCpf))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
        onView(withId(R.id.edtRegistro))
                .perform(typeText("44502396605"));
        closeSoftKeyboard();
        onView(withId(R.id.edtEmail))
                .perform(typeText("vini_attempToCreatAccountSuccessfully@email.com"));
        closeSoftKeyboard();
        onView(withId(R.id.edtSenha))
                .perform(typeText("12345"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_salvar_cadastro)).perform(click());

        //Check por dialog https://stackoverflow.com/questions/21045509/check-if-a-dialog-is-displayed-with-espresso
        onView(withText("Cadastro")).check(matches(isDisplayed()));

    }
}

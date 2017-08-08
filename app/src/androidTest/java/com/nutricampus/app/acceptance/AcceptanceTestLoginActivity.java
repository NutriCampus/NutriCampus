package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;
import android.view.WindowManager;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Usuario;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.fail;

/**
 * Created by Mateus on 29/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestLoginActivity {
    private Activity currentActivity;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void unlockScreen() {
        final LoginActivity activity = mActivityRule.getActivity();
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
    public void attempToLoginWithNonExistentLogin() throws Exception {
        doLogout();

        onView(withId(R.id.input_usuario))
                .perform(typeText("asd123a8125"));
        closeKeyboard();
        onView(withId(R.id.input_senha))
                .perform(typeText("321login"));
        closeKeyboard();

        onView(withId(R.id.btn_login)).perform(click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Falha no login, usuário ou senha inválidos");
            Thread.sleep(1500);
        } catch (Exception e) {
            fail("Toast de mensagem de falha não identificado");
            e.printStackTrace();
        }
    }

    @Test
    public void attempToLoginWithExistentLoginAndWorngPassword() throws Exception {
        doLogout();

        onView(withId(R.id.input_usuario))
                .perform(typeText("12345"));
        closeKeyboard();
        onView(withId(R.id.input_senha))
                .perform(typeText("123"));
        closeKeyboard();

        onView(withId(R.id.btn_login)).perform(click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Falha no login");
            Thread.sleep(1500);
        } catch (Exception e) {
            fail("Toast de mensagem de falha não identificado");
            e.printStackTrace();
        }
    }

    @Test
    public void attempToLoginSuccessfully() throws Exception {
        doLogout();

        Usuario usuario = new Usuario("63876813590", "63876813590",
                "attempToLoginSuccessfully", "attempToLoginSuccessfully@email.com", "12345");
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(InstrumentationRegistry.getTargetContext());
        repositorioUsuario.inserirUsuario(usuario);

        Thread.sleep(1000);

        onView(withId(R.id.input_usuario)).perform(typeText("63876813590"));
        closeKeyboard();
        onView(withId(R.id.input_senha)).perform(typeText("12345"));
        closeKeyboard();

        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(500);//espera 1s p activity abrir

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("Bem-vindo ao NutriCampus");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doLogout() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            new SharedPreferencesManager(mActivityRule.getActivity()).logoutUser();
            currentActivity.finish();
        } else {
            Log.e(AcceptanceTestLoginActivity.class.getName(), "Activity __NÃO__ é main");
        }
    }

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }

    public void closeKeyboard() throws Exception {
        closeSoftKeyboard();
        Thread.sleep(1000);
    }
}

package com.nutricampus.app.acceptance;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;
import android.view.WindowManager;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.LoginActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

import java.util.Collection;

//https://github.com/travis-ci/travis-ci/issues/6340
//https://github.com/thyrlian/AwesomeValidation/blob/master/.travis.yml
//https://stackoverflow.com/questions/26065596/how-to-run-travis-ci-and-espresso-test

/**
 * Created by Mateus on 29/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestLoginActicity {
    private Activity currentActivity;
    private String mLoginToBeTyped;
    private String mPasswordToBeTyped;

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

        new ToastMatcher().isToastMessageDisplayedWithText("Falha no login, usuário ou senha inválidos");

        Thread.sleep(3000);
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

        new ToastMatcher().isToastMessageDisplayedWithText("Falha no login");
        Thread.sleep(3000);

    }

    @Test
    public void attempToLoginSuccessfully() throws Exception {
        doLogout();

        //PRECISA CRIAR USUÀRIO PRIMEIRO
        Thread.sleep(1000);
        onView(withId(R.id.rlayout_faca_login)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.edtNome))
                .perform(typeText("attempToLoginSuccessfully"));
        closeKeyboard();
        onView(withId(R.id.edtCpf))
                .perform(typeText("63876813590"));
        closeKeyboard();
        onView(withId(R.id.edtRegistro))
                .perform(typeText("63876813590"));
        closeKeyboard();
        onView(withId(R.id.edtEmail))
                .perform(typeText("attempToLoginSuccessfully@email.com"));
        closeKeyboard();
        onView(withId(R.id.edtSenha))
                .perform(typeText("12345"));
        closeKeyboard();

        onView(withId(R.id.btn_salvar_cadastro)).perform(click());
        onView(withText("OK")).perform(pressBack());
        onView(withId(R.id.edtNome)).perform(pressBack());
        Thread.sleep(1000);

        //VOLTOU PARA TELA INICIAL
        onView(withId(R.id.input_usuario))
                .perform(typeText("63876813590"));
        closeKeyboard();
        onView(withId(R.id.input_senha))
                .perform(typeText("12345"));
        closeKeyboard();

        onView(withId(R.id.btn_login)).perform(click());

        //Thread.sleep(1000);//espera 1s pelo toast
        new ToastMatcher().isToastMessageDisplayedWithText("Bem-vindo ao NutriCampus");

        Thread.sleep(3000);
    }

    public void doLogout() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            Log.e(AcceptanceTestLoginActicity.class.getName(), "Activity MAIN");
            new SharedPreferencesManager(mActivityRule.getActivity()).logoutUser();
            currentActivity.finish();
        } else {
            Log.e(AcceptanceTestLoginActicity.class.getName(), "Activity __NÃO__ é main");
        }
    }

    private ViewAction openDrawer() {
        return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.CENTER_LEFT,
                GeneralLocation.CENTER_RIGHT, Press.FINGER);
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

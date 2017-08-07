package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.WindowManager;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarPropriedadeActivity;
import com.nutricampus.app.activities.ListaPropriedadesActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Jorge on 11/07/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestLocalizarPropriedade {
        private Activity currentActivity;
        @Rule
        public ActivityTestRule<ListaPropriedadesActivity> mActivityRule = new ActivityTestRule<>(
                ListaPropriedadesActivity.class);

        @Before
        public void unlockScreen() {
            final ListaPropriedadesActivity activity = mActivityRule.getActivity();
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
        public void attempToLocatePropriedadeSuccessfully() throws Exception {//Inserindo novo proprietario ainda nao cadastrado

        /*ViewInteraction appCompatSpinner = onView( withId(R.id.spinner_proprietario));
        appCompatSpinner.perform(scrollTo(), click());
        ViewInteraction appCompatTextView = onView( allOf(withId(android.R.id.text1),
                withText("<< Cadastre um proprietário >>"),
                childAtPosition(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                withParent(withClassName(is("android.widget.FrameLayout")))), 0), isDisplayed()));
        appCompatTextView.perform(click());
*/
        onView(withId(R.id.input_pesquisa_propriedades)).perform(click());
            closeKeyboard();

         ViewInteraction appCompatTextView = onView( allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
            //appCompatTextView2.perform(click()); ViewInteraction appCompatAutoCompleteTextView2 = onView( withId(R.id.input_cidade));

            onView(withId(R.id.input_nome_proprietario)).perform(typeText("TesteProprietario"));
            closeKeyboard();
            onView(withId(R.id.input_cpf_proprietario)).perform(typeText("04998517490"));
            closeKeyboard();
            onView(withId(R.id.input_email_proprietario)).perform(typeText("jvsveloso@gmail.com"));
            closeKeyboard();
            onView(withId(R.id.input_fone_proprietario)).perform(typeText("87996248834"));
            closeKeyboard();
            onView(withId(R.id.btn_salvar_cadastro)).perform(click());
            closeKeyboard();
            onView(withId(R.id.input_nome_propriedade)).perform(typeText("Jorge"));
            closeKeyboard();
            onView(withId(R.id.input_telefone_propriedade)).perform(typeText("87996248834"));
            closeKeyboard();
            onView(withId(R.id.input_rua)).perform(typeText("Rua Fulano de Tal"));
            closeKeyboard();
            onView(withId(R.id.input_bairro)).perform(typeText("Heliopolis"));
            closeKeyboard();
            onView(withId(R.id.input_numero)).perform(typeText("123"));
            closeKeyboard();
            onView(withId(R.id.input_cep)).perform(typeText("55296200"));
            closeKeyboard();

                       closeKeyboard();
//        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gara"), closeSoftKeyboard());
//                ViewInteraction appCompatTextView3 = onView( allOf(withId(android.R.id.text1),
//                withText("Garanhuns"), isDisplayed())); appCompatTextView3.perform(click());

            //onView(withId(R.id.input_estado)).perform(typeText("Pernambuco"));
            //closeKeyboard();
            //onView(withId(R.id.input_cidade)).perform(typeText("Garanhuns"));
            closeKeyboard();
            onView(withId(R.id.btn_salvar_propriedade)).perform(click());
            Thread.sleep(3000);
        }
    public void closeKeyboard() throws Exception {
        closeSoftKeyboard();
        Thread.sleep(1000);
    }
    public void doLogout() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            new SharedPreferencesManager(mActivityRule.getActivity()).logoutUser();
            currentActivity.finish();
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

}

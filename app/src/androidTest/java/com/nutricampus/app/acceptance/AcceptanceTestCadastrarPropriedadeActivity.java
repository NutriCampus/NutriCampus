package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarPropriedadeActivity;
import com.nutricampus.app.activities.CadastrarProprietarioActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.openLink;
import static android.support.test.espresso.action.ViewActions.openLinkWithText;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Jorge on 09/07/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestCadastrarPropriedadeActivity {
    private Activity currentActivity;
    @Rule
    public ActivityTestRule<CadastrarPropriedadeActivity> mActivityRule = new ActivityTestRule<>(
            CadastrarPropriedadeActivity.class);

    @Before
    public void unlockScreen() {
        final CadastrarPropriedadeActivity activity = mActivityRule.getActivity();
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
    public void attempToCreatePropriedadeEProprietarioSuccessfully() throws Exception {//Inserindo novo proprietario ainda nao cadastrado
        ViewInteraction appCompatSpinner = onView(
                withId(R.id.spinner_proprietario));
        appCompatSpinner.perform(scrollTo(), click());
        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("<< Cadastre um proprietário >>"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                0),
                        isDisplayed()));
        closeKeyboard();
        appCompatTextView.perform(click());
        onView(withId(R.id.btn_add_proprietario)).perform(click());
        closeKeyboard();
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
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("TesteProprietario")));
        //appCompatButton3.perform(scrollTo(), click());
        closeKeyboard();
        appCompatButton3.perform(scrollTo());
        closeKeyboard();
        appCompatButton3.perform(click());
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

        ViewInteraction appCompatAutoCompleteTextView = onView(withId(R.id.input_estado));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("Perna"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.input_estado)).perform(typeText("Perna"));

        closeKeyboard();
        ViewInteraction appCompatTextView2 = onView(allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
        appCompatTextView2.perform(click());
        closeKeyboard();

        ViewInteraction appCompatAutoCompleteTextView2 = onView(withId(R.id.input_cidade));
        closeKeyboard();
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gara"), ViewActions.closeSoftKeyboard());
        ViewInteraction appCompatTextView3 = onView(allOf(withId(android.R.id.text1),
                withText("Garanhuns"), isDisplayed()));
        appCompatTextView3.perform(click());
        closeKeyboard();
        onView(withId(R.id.btn_salvar_propriedade)).perform(click());
        Thread.sleep(4000);
    }

     /*
        @Test
       public void attempToCreatePropriedadeJaExistenteSuccessfully() throws Exception {//selecionandoProprietário ja cadastrado
               }
        /*
        @Test
        public void attempToCreatePropriedaSemInformarProprietarioUnsuccessfully() throws Exception {

            onView(withId(R.id.input_nome_propriedade)).perform(typeText("Nome Propriedade"));
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

            ViewInteraction appCompatAutoCompleteTextView = onView(withId(R.id.input_estado));
            appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("Perna"), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.input_estado)).perform(typeText("Perna"));
            ViewInteraction appCompatTextView2 = onView(allOf(withId(android.R.id.text1), withText("Pernambuco"), isDisplayed()));
            appCompatTextView2.perform(click());
            ViewInteraction appCompatAutoCompleteTextView2 = onView(withId(R.id.input_cidade));

            closeKeyboard();
            appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("Gara"), ViewActions.closeSoftKeyboard());
            ViewInteraction appCompatTextView3 = onView(allOf(withId(android.R.id.text1),
                    withText("Garanhuns"), isDisplayed()));
            appCompatTextView3.perform(click());
            closeKeyboard();
            onView(withId(R.id.btn_salvar_propriedade)).perform(click());

            ViewInteraction appCompatTextView = onView(
                    allOf(withId(android.R.id.text1), withText("<< Cadastre um proprietário >>"),
                            childAtPosition(
                                    allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                            withParent(withClassName(is("android.widget.FrameLayout")))),
                                    0),
                            isDisplayed()));

            Thread.sleep(3000);
        }
        */

        @Test
        public void attempToCreatePropriedadeSemPreenchimentoUnsuccessfully() throws Exception {//Inserindo novo proprietario

            onView(withId(R.id.input_nome_propriedade)).perform(typeText(""));
            closeKeyboard();
            onView(withId(R.id.input_telefone_propriedade)).perform(typeText(""));
            closeKeyboard();
            onView(withId(R.id.input_rua)).perform(typeText(""));
            closeKeyboard();
            onView(withId(R.id.input_bairro)).perform(typeText(""));
            closeKeyboard();
            onView(withId(R.id.input_numero)).perform(typeText(""));
            closeKeyboard();
            onView(withId(R.id.input_cep)).perform(typeText(""));
            closeKeyboard();
            //onView(withId(R.id.btn_salvar_propriedade)).perform(click());
            //onData(hasToString(containsString("Salvar"))).perform(click());
            //onView(withText("Campos inválidos")).check(matches(isCompletelyDisplayed()));
            Thread.sleep(3000);
        }
        /*
        @Test
        public void attempToCreatePropriedadeComProprietarioUnsuccessfully() throws Exception {

        }
*/
    public void closeKeyboard() throws Exception {
        closeSoftKeyboard();
        Thread.sleep(2000);
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

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.Root;
import android.support.test.espresso.ViewAssertion;
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
import static org.hamcrest.Matchers.any;
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
public class AccTestCadastrarPropriedadeUnsucessActivity {

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
            onView(withId(R.id.btn_salvar_propriedade)).perform(click());
            closeKeyboard();
            //onView(withText("Nova Propriedade")).check(is(any(withText())));



            Thread.sleep(3000);
        }
        @Test
        public void attempToCreatePropriedadeComProprietarioUnsuccessfully() throws Exception {






        }
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

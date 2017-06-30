package com.nutricampus.app.acceptance;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * http://www.qaautomated.com/2016/01/how-to-test-toast-message-using-espresso.html
 */
public class ToastMatcher {
    //private static final int LONG_DELAY = 3500; // 3.5 seconds
    //private static final int SHORT_DELAY = 2000; // 2 seconds
    /*public void isToastMessageDisplayed(int textId) throws Exception {
        onView(withId(textId)).inRoot(new ToastMatcher1()).check(matches(isDisplayed()));
    }*/

    public void isToastMessageDisplayed(String textId) throws Exception {
        onView(withText(textId)).inRoot(new ToastMatcher1()).check(matches(isDisplayed()));
    }

    public void isToastMessageDisplayedWithText(String textId) throws Exception {
        onView(withText(textId)).inRoot(new ToastMatcher1()).check(matches(withText(textId)));
    }

    private class ToastMatcher1 extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    // windowToken == appToken means this window isn't contained by any other windows.
                    // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                    return true;
                }
            }
            return false;
        }
    }

}



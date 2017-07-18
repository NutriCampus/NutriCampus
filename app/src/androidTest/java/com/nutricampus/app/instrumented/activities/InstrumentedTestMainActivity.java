package com.nutricampus.app.instrumented.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.MainActivity;

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
public class InstrumentedTestMainActivity {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureViewIsShowing() throws Exception {
        MainActivity activity = rule.getActivity();

        TextView textView = (TextView) activity.findViewById(R.id.inicio);
        assertNotNull(textView);

        DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(R.id.drawer_layout);
        assertNotNull(drawerLayout);

        Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
    }
}

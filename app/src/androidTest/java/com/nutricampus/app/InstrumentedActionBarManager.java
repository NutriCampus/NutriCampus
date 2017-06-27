package com.nutricampus.app;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.entities.ActionBarManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mateus on 27/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
public class InstrumentedActionBarManager {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureActionbarIsShowing() throws Exception {
        MainActivity activity = rule.getActivity();

        assertNotNull(activity.getActionBarMainActivity());

        //assertEquals("Início", activity.getActionBarMainActivity().getmActionBar().getDrawerItem(0).toString());
        /*Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        Context appContext = InstrumentationRegistry.getTargetContext();

        ActionBarManager actionBarManager = new ActionBarManager((Activity) appContext, toolbar);
        assertNotNull(actionBarManager.get_act());
        assertNotNull(actionBarManager.getmActionBar());
        assertNotNull(actionBarManager.getToolbar());

        assertEquals("Inínio", actionBarManager.getmActionBar().getItemAdapter().getAdapterItem(0).toString());
        */


    }
}

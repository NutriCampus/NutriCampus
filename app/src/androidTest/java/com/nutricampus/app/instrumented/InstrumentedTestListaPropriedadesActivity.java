package com.nutricampus.app.instrumented;

import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.view.menu.ActionMenuItemView;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.ListaPropriedadesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Created by kellison on 12/07/17.
 */

@RunWith(AndroidJUnit4.class)

public class InstrumentedTestListaPropriedadesActivity {
    @Rule
    public ActivityTestRule<ListaPropriedadesActivity> rule = new ActivityTestRule<>(ListaPropriedadesActivity.class);

    @Test
    public void ensureViewIsShowing() throws Exception {
        ListaPropriedadesActivity activity = rule.getActivity();
        ActionMenuItemView icone_pesquisa = (ActionMenuItemView) activity.findViewById(R.id.action_search);
        assertNotNull(icone_pesquisa);

        FloatingActionButton add_proprietario = (FloatingActionButton) activity.findViewById(R.id.btn_add_proprietario);
        assertNotNull(add_proprietario);
    }
}

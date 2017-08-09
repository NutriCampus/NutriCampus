package com.nutricampus.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.EditarUsuarioActivity;

/**
 * Created by felipe on 06/08/17.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class ConfigFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    private Preference preferenceConta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        preferenceConta = findPreference(getString(R.string.pref_conta));

        preferenceConta.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        startActivity(new Intent(
                getActivity(), EditarUsuarioActivity.class));

        return true;
    }
}

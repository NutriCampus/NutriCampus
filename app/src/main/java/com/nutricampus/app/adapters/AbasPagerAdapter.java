package com.nutricampus.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nutricampus.app.R;
import com.nutricampus.app.fragments.ConteudoFragment;

import java.util.Locale;

/**
 * Created by Felipe on 19/07/2017.
 */

public class AbasPagerAdapter extends FragmentPagerAdapter {

    String titulos[];

    public AbasPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        titulos = context.getResources().getStringArray(R.array.titulos);
    }

    @Override
    public Fragment getItem(int position) {
        ConteudoFragment conteudoFragment = ConteudoFragment.newInstance(titulos[position]);
        return conteudoFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return titulos[position].toUpperCase();
    }
}

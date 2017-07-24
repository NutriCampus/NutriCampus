package com.nutricampus.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.fragments.DadosComplementaresFragment;

import java.util.Locale;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class AbasPagerAdapter extends FragmentPagerAdapter {

    String titulos[];
    Animal animal;
    Propriedade propriedade;

    public AbasPagerAdapter(Animal animal, Context context, FragmentManager fm, Propriedade propriedade) {
        super(fm);
        titulos = context.getResources().getStringArray(R.array.titulos);
        this.animal = animal;
        this.propriedade = propriedade;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DadosAnimalFragment dadosAnimalFragment = DadosAnimalFragment.newInstance(this.animal, this.propriedade);
                return dadosAnimalFragment;
            case 1:
                DadosComplementaresFragment dadosComplementaresFragment = DadosComplementaresFragment.newInstance(this.animal);
                return dadosComplementaresFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        return titulos[position];
    }
}

package com.nutricampus.app.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.fragments.DadosComplementaresFragment;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class AbasPagerAdapter extends FragmentPagerAdapter {

    private final String[] titulos;
    private final Animal animal;
    private final Propriedade propriedade;

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
                return DadosAnimalFragment.newInstance(this.animal, this.propriedade);

            case 1:
                return DadosComplementaresFragment.newInstance(this.animal);
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
        return titulos[position];
    }
}

package com.nutricampus.app.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.AbasPagerAdapter;
import com.nutricampus.app.fragments.DadosAnimalFragment;

public class CadastrarAnimalActivity extends AppCompatActivity
        implements DadosAnimalFragment.AoClicarConfirmaDados{

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_animal);

        AbasPagerAdapter pagerAdapter = new AbasPagerAdapter(
                CadastrarAnimalActivity.this,
                getSupportFragmentManager());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void confirmarDados() {
        //Alterna para a próxima aba
        viewPager.setCurrentItem(1);
    }

}

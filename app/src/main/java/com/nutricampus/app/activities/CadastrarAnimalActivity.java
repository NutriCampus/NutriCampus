package com.nutricampus.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.AbasPagerAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.fragments.DadosComplementaresFragment;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastrarAnimalActivity extends AppCompatActivity
        implements DadosAnimalFragment.AoClicarConfirmaDados, DadosComplementaresFragment.SalvarDadosAnimal {

    protected ViewPager viewPager;
    private Animal animal;
    protected AbasPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_animal);

        Propriedade propriedade = (Propriedade)
                getIntent().getSerializableExtra(CadastrarPropriedadeActivity.EXTRA_PROPRIEDADE);

        inicializarViewPager(animal, CadastrarAnimalActivity.this, propriedade);
    }

    public void inicializarViewPager(Animal animal, Context context, Propriedade propriedade) {
        pagerAdapter = new AbasPagerAdapter(
                animal,
                context,
                getSupportFragmentManager(),
                propriedade);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void confirmarDados(Animal animal) {
        this.animal = animal;
        Toast.makeText(CadastrarAnimalActivity.this, getString(R.string.msg_completar_cadastro, "seu cadastro"), Toast.LENGTH_LONG).show();

        //Alterna para a próxima aba
        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(CadastrarAnimalActivity.this, ListaAnimaisActivity.class);
            startActivity(it);
            finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(CadastrarAnimalActivity.this, ListaAnimaisActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void salvar(DadosComplAnimal dadosComplAnimal) {
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(CadastrarAnimalActivity.this);
        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(CadastrarAnimalActivity.this);

        //Verificação se o usuário tentou inserir sei cadastrar todos os dados ou não os confirmo-los
        if (animal == null) {
            Toast.makeText(CadastrarAnimalActivity.this, getString(R.string.msg_erro_falta_de_dados), Toast.LENGTH_LONG).show();
            return;
        }

        //Verificação se o animal já está cadastrado nesta propriedade
        Animal animalDuplicado = repositorioAnimal.buscarAnimal(animal.getIndentificador(), animal.getPropriedade());
        if (animalDuplicado != null) {
            Toast.makeText(CadastrarAnimalActivity.this, getString(R.string.msg_erro_duplicidade_animal), Toast.LENGTH_LONG).show();
            return;
        }


        int idAnimal = repositorioAnimal.inserirAnimal(animal);

        if (idAnimal > 0) {
            animal.setId(idAnimal);
        } else {
            Toast.makeText(CadastrarAnimalActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
            return;
        }

        dadosComplAnimal.setAnimal(idAnimal);
        int idDadosComp = repositorioDadosComplAnimal.inserirDadosComplAnimal(dadosComplAnimal);

        if (idDadosComp > 0) {
            Toast.makeText(CadastrarAnimalActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            dadosComplAnimal.setId(idDadosComp);
            Intent it = new Intent(CadastrarAnimalActivity.this, ListaAnimaisActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(CadastrarAnimalActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }
}

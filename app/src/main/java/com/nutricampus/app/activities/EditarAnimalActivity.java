package com.nutricampus.app.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.AbasPagerAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.fragments.DadosAnimalFragment;

public class EditarAnimalActivity extends CadastrarAnimalActivity {

    private int idAnimalAtual;
    private Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_editar_animal);
        Intent intent = getIntent();
        Animal animal = (Animal) intent.getSerializableExtra(DadosAnimalFragment.EXTRA_ANIMAL);

        inicializarViewPager(animal, EditarAnimalActivity.this, null);

    }

    @Override
    public void confirmarDados(Animal animal) {
        this.animal = animal;
        idAnimalAtual = animal.getId();
        Toast.makeText(EditarAnimalActivity.this, getString(R.string.msg_completar_cadastro, "sua edição"), Toast.LENGTH_LONG).show();

        Log.e("EF", "edtAct_confirmar " + this.animal.getId());

        //Alterna para a próxima aba
        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(EditarAnimalActivity.this, ListaAnimaisActivity.class);
                startActivity(it);
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed(){
        Intent it = new Intent(EditarAnimalActivity.this, ListaAnimaisActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void salvar(DadosComplAnimal dadosComplAnimal) {
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(EditarAnimalActivity.this);
        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(EditarAnimalActivity.this);

        //Verificação se o usuário tentou inserir sei cadastrar todos os dados ou não os confirmo-los
        if(animal == null) {
            Toast.makeText(EditarAnimalActivity.this, getString(R.string.msg_erro_falta_de_dados), Toast.LENGTH_LONG).show();
            return;
        }

        //Verificação se o animal já está cadastrado nesta propriedade
        Animal animalDuplicado = repositorioAnimal.buscarAnimal(animal.getIndentificador(), animal.getPropriedade());
        if(!(animalDuplicado == null) && idAnimalAtual != animalDuplicado.getId()) {
            Toast.makeText(EditarAnimalActivity.this, getString(R.string.msg_erro_duplicidade_animal), Toast.LENGTH_LONG).show();
            return;
        }

        boolean atualAnimal = repositorioAnimal.atualizarAnimal(animal);
        Log.e("EF", "edtAct_Salvar " + animal.getId());

        if(atualAnimal) {
            Toast.makeText(EditarAnimalActivity.this, "Animal atualizado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(EditarAnimalActivity.this, "Erro ao atualizar animal" , Toast.LENGTH_LONG).show();
            return;
        }

        //dadosComplAnimal.setAnimal(animal.getId());
        boolean atualDadosComp = repositorioDadosComplAnimal.atualizarDadosCompl(dadosComplAnimal);

        if(atualDadosComp && atualAnimal) {
            Toast.makeText(EditarAnimalActivity.this, getString(R.string.msg_sucesso_atualizar, animal.getIndentificador(), ""),
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(EditarAnimalActivity.this, ListaAnimaisActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(EditarAnimalActivity.this, getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
            return;
        }

    }
}

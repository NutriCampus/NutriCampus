package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDieta;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Dieta;
import com.nutricampus.app.entities.Propriedade;

import java.util.ArrayList;

@SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarDietaActivity extends CadastrarDietaActivity {

    public Dieta dieta = new Dieta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        inicializar();

    }

    public void inicializar() {

        int id = getIntent().getIntExtra("id", 0);
        dieta = new RepositorioDieta(this).buscarDietaById(id);

        identificador.setText(dieta.identificador);
        pb.setText(String.valueOf(dieta.proteinaBruta));

        //Selecionando propriedade
        for (int i = 0; i < propriedadesBD.size(); i++) {
            if (dieta.propriedade.getId() == propriedadesBD.get(i).getId()) {
                spinnerProprietarios.setSelection(i);
                break;
            }
        }

        arrAnimais = dieta.arrayAnimais;
        arrCompostos = dieta.arrayCompostosSelecionados;
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(this);
        animaisBD = repositorioAnimal.buscarPorPropridade(dieta.propriedade.getId());

        mSelectedItemsAnimal.clear();
        mSelectedItemsComposto.clear();
        //Selecionar Animais
        for (int i = 0; i < animaisBD.size(); i++) {
            for (int j = 0; j < arrAnimais.size(); j++) {
                if (animaisBD.get(i).getId() == arrAnimais.get(j).getId()) {
                    mSelectedItemsAnimal.add(i);
                }
            }
        }

        //Selecionar compostos
        for (int i = 0; i < compostosBD.size(); i++) {
            for (int j = 0; j < arrCompostos.size(); j++) {
                if (compostosBD.get(i).getId() == arrCompostos.get(j).getId()) {
                    mSelectedItemsComposto.add(i);
                }
            }
        }

    }

    @Override
    public void calcularDieta(View view) {
        if (identificador.getText().toString().isEmpty()) {
            Toast.makeText(this, "Adicione um nome a dieta", Toast.LENGTH_SHORT).show();
            return;
        }
        double pbD = 0;
        if (pb.getText().toString().isEmpty()) {
            Toast.makeText(this, "Adicione a quantidade de PB da dieta", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                pbD = Double.parseDouble(pb.getText().toString());
                if (pbD <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Adicione um valor maior que zero", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (arrAnimais.size() < 1) {
            Toast.makeText(this, "Adicione pelo menos 1 Animal", Toast.LENGTH_SHORT).show();
            return;
        }
        if (arrCompostos.size() < 2) {
            Toast.makeText(this, "Adicione pelo menos 2 Compostos", Toast.LENGTH_SHORT).show();
            return;
        }
        dieta.identificador = identificador.getText().toString();
        dieta.proteinaBruta = pbD;
        dieta.propriedade = propriedadesBD.get(spinnerProprietarios.getSelectedItemPosition());
        dieta.arrayAnimais = arrAnimais;
        dieta.arrayCompostosSelecionados = arrCompostos;
        dieta.calcular();

        showDieta(dieta);
    }

    @Override
    public void showDieta(final Dieta dieta) {
        String str = "";
        for (int i = 0; i < dieta.arrayObjetoDieta.size(); i++) {
            str += dieta.arrayObjetoDieta.get(i).composto.getIdentificador() +
                    ": " + dieta.arrayObjetoDieta.get(i).porcentagem + "%\n";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalhes");
        builder.setMessage(str)
                .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RepositorioDieta repositorioDieta = new RepositorioDieta(EditarDietaActivity.this);
                        repositorioDieta.atualizarDieta(dieta);

                        //dieta.print();

                        EditarDietaActivity.this.finish();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}

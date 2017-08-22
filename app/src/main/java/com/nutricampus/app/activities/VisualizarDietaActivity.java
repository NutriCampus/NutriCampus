package com.nutricampus.app.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class VisualizarDietaActivity extends EditarDietaActivity {
    /*
        public EditText identificador = null;
    public EditText pb = null;
    public Button btnAddAnimal = null;
    public Button btnAddComposto = null;
    public Button btnCalcular = null;
    public Spinner spinnerProprietarios = null;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        inicializar();

        edit();

        //Fecha teclado (bug)
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
    }

    private void edit() {

        identificador.setEnabled(false);
        pb.setEnabled(false);
        spinnerProprietarios.setEnabled(false);
        btnAddAnimal.setText("Ver Animais");
        btnAddComposto.setText("Ver Compostos");
        btnCalcular.setVisibility(View.GONE);

        String str = "";
        for (int i = 0; i < dieta.arrayObjetoDieta.size(); i++) {
            str += dieta.arrayObjetoDieta.get(i).composto.getIdentificador() +
                    ": " + dieta.arrayObjetoDieta.get(i).porcentagem + "%\n";
        }
        detalhes.setText(str);
        detalhes.setVisibility(View.VISIBLE);
    }

    @Override
    public void addAnimais(View view) {
        String[] animais = new String[arrAnimais.size()];
        for (int i = 0; i < arrAnimais.size(); i++) {
            animais[i] = arrAnimais.get(i).getIndentificador();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle("Animais Selecionados")
                .setItems(animais, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                    }
                })
                // Set the action buttons
                .setPositiveButton("Pronto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }

    @Override
    public void addCompostos(View view) {
        String[] compostos = new String[arrCompostos.size()];
        for (int i = 0; i < arrCompostos.size(); i++) {
            compostos[i] = arrCompostos.get(i).getIdentificador();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle("Compostos Selecionados")
                .setItems(compostos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                    }
                })
                // Set the action buttons
                .setPositiveButton("Pronto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();

    }
}

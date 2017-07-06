package com.nutricampus.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastrarPropriedadeActivity extends AppCompatActivity {

    @BindView(R.id.input_id_propriedade) EditText inputId;
    @BindView(R.id.input_nome_propriedade) EditText inputNome;
    @BindView(R.id.input_cep) EditText inputCep;
    @BindView(R.id.input_rua) EditText inputRua;
    @BindView(R.id.input_bairro) EditText inputBairro;
    @BindView(R.id.input_numero) EditText inputNumero;
    @BindView(R.id.input_telefone_propriedade) EditText inputTelefone;
    @BindView(R.id.input_cidade) AutoCompleteTextView inputCidade;
    @BindView(R.id.input_estado) AutoCompleteTextView inputEstado;
    @BindView(R.id.btn_salvar_propriedade) Button buttonSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_propriedade);

        ButterKnife.bind(this);

        addAutoCompletes();
    }

    private void addAutoCompletes(){

        // Autocomplete para o campo estado
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listaEstados());
        inputEstado.setAdapter(adapterEstados);

        // Autocomplete para o campo cidade
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listaCidades());
        inputCidade.setAdapter(adapterCidades);

    }

    private String carregaJSONAssets(String arquivo) {
        String json;

        try {
            InputStream is = getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];

            @SuppressWarnings("UnusedAssignment")
            int result = is.read(buffer);

            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.i("IOException",ex.toString());
            return null;
        }

        return json;

    }

    private ArrayList<HashMap<String, String>> estruturaEstados(){
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(carregaJSONAssets("estados.json"));
            JSONArray estadosArray = obj.getJSONArray("estados");

            HashMap<String, String> dados;

            for (int i = 0; i < estadosArray.length(); i++) {
                JSONObject dadosJSON = estadosArray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String sigla = dadosJSON.getString("Sigla");
                String nome = dadosJSON.getString("Nome");

                dados = new HashMap<>();
                dados.put("id", id);
                dados.put("sigla", sigla);
                dados.put("nome", nome);

                lista.add(dados);
            }

        } catch (JSONException e) {
            Log.i("JSONException", String.valueOf(e));
        }

        return lista;
    }

    private ArrayList<HashMap<String, String>> estruturaCidades(){
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(carregaJSONAssets("cidades.json"));
            JSONArray estadosArray = obj.getJSONArray("cidades");

            HashMap<String, String> dados;

            for (int i = 0; i < estadosArray.length(); i++) {
                JSONObject dadosJSON = estadosArray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String idEstado = dadosJSON.getString("Estado");
                String nome = dadosJSON.getString("Nome");

                dados = new HashMap<>();
                dados.put("id", id);
                dados.put("idEstado", idEstado);
                dados.put("nome", nome);

                lista.add(dados);
            }

        } catch (JSONException e) {
            Log.i("JSONException", String.valueOf(e));
        }

        return lista;
    }

    protected void salvar(View view){
        if (!validaDados()) {
            Toast.makeText(CadastrarPropriedadeActivity.this,R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }
    }

    private boolean validaDados(){
        boolean valido = true;

        if (inputNome.getText().toString().isEmpty()) {
            inputNome.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputNome.setError(null);
        }

        if (inputTelefone.getText().toString().isEmpty()) {
            inputTelefone.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputTelefone.setError(null);
        }

        if (inputRua.getText().toString().isEmpty()) {
            inputRua.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputRua.setError(null);
        }

        if (inputBairro.getText().toString().isEmpty()) {
            inputBairro.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputBairro.setError(null);
        }

        if (inputNumero.getText().toString().isEmpty()) {
            inputNumero.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputNumero.setError(null);
        }

        if (inputCep.getText().toString().isEmpty()) {
            inputCep.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputCep.setError(null);
        }

        if (inputCidade.getText().toString().isEmpty()) {
            inputCidade.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputCidade.setError(null);
        }

        if (inputEstado.getText().toString().isEmpty()) {
            inputEstado.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputEstado.setError(null);
        }

        return valido;
    }


    private String[] listaEstados(){
        ArrayList<HashMap<String, String>> estados = estruturaEstados();
        String[] lista = new String[estados.size()];
        for (int i = 0; i < estados.size();i++){
            lista[i] = estados.get(i).get("nome");
        }

        return lista;
    }

    private String[] listaCidades(){
        ArrayList<HashMap<String, String>> cidades = estruturaCidades();
        String[] lista = new String[cidades.size()];
        for (int i = 0; i < cidades.size();i++){
            lista[i] = cidades.get(i).get("nome");
        }

        return lista;
    }



}

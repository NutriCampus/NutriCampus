package com.nutricampus.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.ActionBarManager;

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
    private ActionBarManager actionBar;

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

        // Autocomplete para o campo estado
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listaEstados());
        inputEstado.setAdapter(adapterEstados);

        // Autocomplete para o campo cidade
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listaCidades());
        inputCidade.setAdapter(adapterCidades);

    }


    protected String carregaJSONAssets(String arquivo) throws IOException {
        String json = "";

        try {
            InputStream is = getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

    protected ArrayList<HashMap<String, String>> estruturaEstados(){
        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

        try {
            JSONObject obj = new JSONObject(carregaJSONAssets("estados.json"));
            JSONArray estadosArray = obj.getJSONArray("estados");

            HashMap<String, String> dados;

            for (int i = 0; i < estadosArray.length(); i++) {
                JSONObject dadosJSON = estadosArray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String sigla = dadosJSON.getString("Sigla");
                String nome = dadosJSON.getString("Nome");

                dados = new HashMap<String, String>();
                dados.put("id", id);
                dados.put("sigla", sigla);
                dados.put("nome", nome);

                lista.add(dados);
            }

            for (int i = 0; i < lista.size(); i++) {

            }
        } catch (JSONException e) {
            Log.i("JSONException", String.valueOf(e));
        } catch (IOException e) {
            Log.i("IOException", String.valueOf(e));
        }

        return lista;
    }

    public String[] listaEstados(){
        ArrayList<HashMap<String, String>> estados = estruturaEstados();
        String[] lista = new String[estados.size()];
        for (int i = 0; i < estados.size();i++){
            lista[i] = estados.get(i).get("nome");
        }

        return lista;
    }


    protected ArrayList<HashMap<String, String>> estruturaCidades(){
        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

        try {
            JSONObject obj = new JSONObject(carregaJSONAssets("cidades.json"));
            JSONArray estadosArray = obj.getJSONArray("cidades");

            HashMap<String, String> dados;

            for (int i = 0; i < estadosArray.length(); i++) {
                JSONObject dadosJSON = estadosArray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String idEstado = dadosJSON.getString("Estado");
                String nome = dadosJSON.getString("Nome");

                dados = new HashMap<String, String>();
                dados.put("id", id);
                dados.put("idEstado", idEstado);
                dados.put("nome", nome);

                lista.add(dados);
            }

            for (int i = 0; i < lista.size(); i++) {

            }
        } catch (JSONException e) {
            Log.i("JSONException", String.valueOf(e));
        } catch (IOException e) {
            Log.i("IOException", String.valueOf(e));
        }

        return lista;
    }

    public String[] listaCidades(){
        ArrayList<HashMap<String, String>> cidades = estruturaCidades();
        String[] lista = new String[cidades.size()];
        for (int i = 0; i < cidades.size();i++){
            lista[i] = cidades.get(i).get("nome");
        }

        return lista;
    }

    protected void salvar(){

    }
}

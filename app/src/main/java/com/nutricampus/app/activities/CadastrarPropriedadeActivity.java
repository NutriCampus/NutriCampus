package com.nutricampus.app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.ListaPropriedadesAdapter;
import com.nutricampus.app.model.Mascara;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastrarPropriedadeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.input_id_propriedade) EditText inputId;
    @BindView(R.id.input_id_proprietario) EditText inputIdProprietario;
    @BindView(R.id.input_nome_propriedade) EditText inputNome;
    @BindView(R.id.input_cep) EditText inputCep;
    @BindView(R.id.input_rua) EditText inputRua;
    @BindView(R.id.input_bairro) EditText inputBairro;
    @BindView(R.id.input_numero) EditText inputNumero;
    @BindView(R.id.input_telefone_propriedade) EditText inputTelefone;
    @BindView(R.id.input_cidade) AutoCompleteTextView inputCidade;
    @BindView(R.id.input_estado) AutoCompleteTextView inputEstado;
    @BindView(R.id.btn_salvar_propriedade) Button buttonSalvar;
    @BindView(R.id.spinner_proprietario)
    Spinner spinnerProprietario;
    @BindView(R.id.btn_add_proprietario)
    Button buttonAddProprietario;

    SharedPreferencesManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_cadastrar_propriedade);

        ButterKnife.bind(this);

        addAutoCompletes();

        inputTelefone.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputTelefone));
        inputCep.addTextChangedListener(Mascara.insert(Mascara.CEP_MASK, inputCep));

        spinnerProprietario.setOnItemSelectedListener(this);

        // Loading spinner data from database
        preencherSpinnerListaProprietario();
        buttonAddProprietario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                criarProprietario(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherSpinnerListaProprietario();
    }

    public void preencherSpinnerListaProprietario() {

        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        List<Proprietario> todosProprietarios = repositorioProprietario.buscarTodosProprietarios();

        if(todosProprietarios.size() > 0) {

            // Adiciona a msg de "Selecione..." no spinner do proprietario
            Proprietario posZero = new Proprietario(0,"",getString(R.string.msg_spinner_proprietario),"","");
            todosProprietarios.add(0,posZero);

            ArrayAdapter<Proprietario> spinnerProprietarioAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todosProprietarios);

            spinnerProprietarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerProprietario.setAdapter(spinnerProprietarioAdapter);


            int posicao;
            String idProprietario = inputIdProprietario.getText().toString();

            if (idProprietario.isEmpty())
                posicao = 0;
            else
                posicao = spinnerProprietarioAdapter.getPosition(repositorioProprietario.buscarProprietario(Integer.parseInt(idProprietario)));


            spinnerProprietario.setSelection(posicao);
        }
        else{
            ArrayAdapter adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, new String[]{"<< " + getString(R.string.msg_cadastre_proprietario) + " >>"});
            spinnerProprietario.setAdapter(adapter);


        }
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
        String json = new String("");

        try {
            InputStream is = getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];


            if (is.read(buffer) > 0)
                json = new String(buffer, "UTF-8");

            is.close();
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

        Proprietario proprietario = (Proprietario) spinnerProprietario.getSelectedItem();

        Propriedade propriedade = new Propriedade(
                inputNome.getText().toString(),
                inputTelefone.getText().toString(),
                inputRua.getText().toString(),
                inputBairro.getText().toString(),
                inputCep.getText().toString(),
                inputCidade.getText().toString(),
                inputEstado.getText().toString(),
                inputNumero.getText().toString(),
                ((Proprietario) spinnerProprietario.getSelectedItem()).getId(),
                Integer.parseInt(session.getIdNC()));

        propriedade.setProprietario(proprietario);

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());

        int idPropriedade = repositorioPropriedade.inserirPropriedade(propriedade);

        if(idPropriedade > 0) {
            Toast.makeText(CadastrarPropriedadeActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            propriedade.setId(idPropriedade);
            Intent it = new Intent(CadastrarPropriedadeActivity.this, ListaPropriedadesActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(CadastrarPropriedadeActivity.this, R.string.msg_cadastro_erro, Toast.LENGTH_LONG).show();
        }


    }

    public void criarProprietario(View view) {
        Intent it = new Intent(this, CadastrarProprietarioActivity.class);
        startActivity(it);
    }


    protected boolean validaDados(){
        boolean valido = true;

        if (inputNome.getText().toString().isEmpty()) {
            inputNome.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputNome.setError(null);
        }

        if (spinnerProprietario.getSelectedItemPosition() == 0) {
            TextView text = (TextView) spinnerProprietario.getSelectedView();
            text.setError("");
            text.setTextColor(Color.RED);
            valido = false;
        } else {
            TextView text = (TextView) spinnerProprietario.getSelectedView();
            text.setError(null);
            text.setTextColor(Color.BLACK);
        }

        if (inputTelefone.getText().toString().isEmpty()) {
            inputTelefone.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (inputTelefone.getText().toString().length() < 14){
            inputTelefone.setError(getString(R.string.msg_erro_telefone_incompleto));
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
        } else if(inputCep.getText().toString().length() > 0 && inputCep.getText().toString().length() < 9) {
            inputCep.setError(getString(R.string.msg_erro_cep_incompleto));
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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        if(parent.getItemAtPosition(position) instanceof Proprietario){
            Proprietario proprietario = (Proprietario) parent.getItemAtPosition(position);
            inputIdProprietario.setText(String.valueOf(proprietario.getId()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Implementação necessário por causa da Interface usada nesta classe
    }
}

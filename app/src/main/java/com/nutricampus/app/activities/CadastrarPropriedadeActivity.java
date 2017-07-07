package com.nutricampus.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.Mascara;

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
    @BindView(R.id.search_cpf_proprietario) EditText pesquisarCpf;
    @BindView(R.id.result_nome_proprietario) EditText nomeProprietario;

    private Proprietario proprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_propriedade);

        ButterKnife.bind(this);

        addAutoCompletes();

        inputTelefone.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputTelefone));
        inputCep.addTextChangedListener(Mascara.insert(Mascara.CEP_MASK, inputCep));
        pesquisarCpf.addTextChangedListener(Mascara.insert(Mascara.CPF_MASK, pesquisarCpf));

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

        Propriedade propriedade = new Propriedade(
                inputNome.getText().toString(),
                inputTelefone.getText().toString(),
                inputRua.getText().toString(),
                inputBairro.getText().toString(),
                inputCep.getText().toString(),
                inputCidade.getText().toString(),
                inputEstado.getText().toString(),
                inputNumero.getText().toString());

        propriedade.setProprietario(proprietario);

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        int idPropriedade = repositorioPropriedade.inserirPropriedade(propriedade);

        if(idPropriedade > 0) {
            Toast.makeText(CadastrarPropriedadeActivity.this, "Propriedade gravada com sucesso", Toast.LENGTH_LONG).show();
            propriedade.setId(idPropriedade);
        } else {
            Toast.makeText(CadastrarPropriedadeActivity.this, "Erro ao gravar Propriedade", Toast.LENGTH_LONG).show();
        }


    }

    public void criarProprietario(View v) {
        Intent it = new Intent(this, CadastrarProprietarioActivity.class);
        startActivity(it);
    }

    public void buscarProprietario(View v) {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        proprietario = repositorioProprietario.buscarProprietario(pesquisarCpf.getText().toString());

        if(proprietario == null) {
            Toast.makeText(CadastrarPropriedadeActivity.this, getString(R.string.msg_erro_busca_proprietario),
                    Toast.LENGTH_LONG).show();
        } else {
            nomeProprietario.setText(proprietario.getNome());
        }
    }

    public void editarProprietario(View v) {
        if(nomeProprietario.getText().toString().equals("")) {
            Toast.makeText(CadastrarPropriedadeActivity.this, getString(R.string.msg_erro_editar_proprietario),
                    Toast.LENGTH_LONG).show();
        } else {
            Intent it = new Intent(CadastrarPropriedadeActivity.this, EditarProprietarioActivity.class);
            it.putExtra("proprietario", proprietario);
            pesquisarCpf.setText("");
            nomeProprietario.setText("");
            startActivity(it);
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

        if (pesquisarCpf.getText().toString().isEmpty()) {
            this.pesquisarCpf.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (pesquisarCpf.getText().toString().length() < 14) {
            this.pesquisarCpf.setError(getString(R.string.msg_erro_cpf_1));
            valido = false;
        } else {
            this.pesquisarCpf.setError(null);
        }

        if(nomeProprietario.getText().toString().isEmpty() && pesquisarCpf.getText().toString().length() == 14) {
            this.pesquisarCpf.setError(getString(R.string.msg_erro_editar_proprietario));
            valido = false;
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

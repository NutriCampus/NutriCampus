package com.nutricampus.app.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.nutricampus.app.activities.LoginActivity;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class SharedPreferencesManager {
    /*
    http://randomkeygen.com/
    TODO trocar keys a cada versão do app
     */
    private static final String UNIQ_KEY = "p_I9yQ4L";

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Shared pref mode
    public static final int PRIVATE_MODE_SHARED_PREF = 0;

    // Shared preferences file name
    public static final String PREF_NAME = UNIQ_KEY + "NutriCampus";

    /*KEYS para o sharedpreferences*/
    private static final String KEY_USUARIO = PREF_NAME + "usuario";
    private static final String KEY_SENHA = PREF_NAME + "senha";
    private static final String KEY_EMAIL = PREF_NAME + "email";
    private static final String KEY_ID_USUARIO = PREF_NAME + "idUsuario";
    private static final String KEY_LOGADO = "logado";

    public SharedPreferencesManager(Context context) {
        this.context = context;

        removerBdVersoesAnteriores();

        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE_SHARED_PREF);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * NUNCA remover essa chamada. Isso fara com que seja apagado o bd anterior do usuário.
     * Isso pq o usuário pode não ter a versão atual. Quando tiver, o bd anterior sera limpado.
     */
    public void removerBdVersoesAnteriores() {
        final String bd1 = "s@ap6j?nu7ra3as&ASuB5*#4PruSig@Viewer";
        final String bd2 = "3as6W32#$!@a*b-AKHSdaSig@Viewer";

        if (PREF_NAME.equals(bd1)) {
            context.getSharedPreferences(bd1, 0).edit().clear().commit();
        } else if (PREF_NAME.equals(bd2)) {
            context.getSharedPreferences(bd2, 0).edit().clear().commit();
        }

    }

    public void close() {
        editor = null;
        pref = null;
    }

    public String getUsuario() {
        return pref.getString(KEY_USUARIO, "");
    }

    public void setUsuario(String keyUsuario) {
        editor.putString(KEY_USUARIO, keyUsuario);
        editor.commit();
    }

    public String getSenha() {
        return pref.getString(KEY_SENHA, "");
    }

    public void setSenha(String keySenha) {
        editor.putString(KEY_SENHA, keySenha);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setEmail(String keyEmail) {
        editor.putString(KEY_EMAIL, keyEmail);
        editor.commit();
    }

    public String getIdUsuario() {
        return pref.getString(KEY_ID_USUARIO, "");
    }

    public void setIdUsuario(String keyIdNC) {
        editor.putString(KEY_ID_USUARIO, keyIdNC);
        editor.commit();
    }

    public void createLoginSession(int id, String name, String email, String senha){
        // Storing login value as TRUE
        editor.putBoolean(KEY_LOGADO, true);

        // Storing name in pref
        editor.putString(KEY_USUARIO, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_SENHA, senha);
        editor.putString(KEY_ID_USUARIO, Integer.toString(id));

        // commit changes
        editor.commit();
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

        }
    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGADO, false);
    }
}

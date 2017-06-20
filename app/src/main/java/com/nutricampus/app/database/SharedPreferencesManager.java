package com.nutricampus.app.database;

import android.content.Context;
import android.content.SharedPreferences;

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
    private final String UNIQ_KEY = "p_I9yQ4L";

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared pref mode
    public final int PRIVATE_MODE_SHARED_PREF = 0;

    // Shared preferences file name
    public final String PREF_NAME = UNIQ_KEY + "NutriCampus";

    /*KEYS para o sharedpreferences*/
    private final String KEY_USERSIGA = PREF_NAME + "sigaUser";
    private final String KEY_PWDSIGA = PREF_NAME + "sigaPwd";

    public SharedPreferencesManager(Context context) {
        this._context = context;

        removerBdVersoesAnteriores();

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE_SHARED_PREF);
        editor = pref.edit();
    }

    /**
     * NUNCA remover essa chamada. Isso fara com que seja apagado o bd anterior do usuário.
     * Isso pq o usuário pode não ter a versão atual. Quando tiver, o bd anterior sera limpado.
     */
    public void removerBdVersoesAnteriores() {
        final String bd1 = "s@ap6j?nu7ra3as&ASuB5*#4PruSig@Viewer";
        final String bd2 = "3as6W32#$!@a*b-AKHSdaSig@Viewer";

        if (PREF_NAME.equals(bd1)) {
            _context.getSharedPreferences(bd1, 0).edit().clear().commit();
        } else if (PREF_NAME.equals(bd2)) {
            _context.getSharedPreferences(bd2, 0).edit().clear().commit();
        }

    }

    public void close() {
        editor = null;
        pref = null;
    }

    public String getUserSiga() {
        return pref.getString(KEY_USERSIGA, "");
    }

    public void setUserSiga(String keyUsersiga) {
        editor.putString(KEY_USERSIGA, keyUsersiga);
        editor.commit();
    }

    public String getPwdSiga() {
        return pref.getString(KEY_PWDSIGA, "");
    }

    public void setPwdSiga(String keyPwdsiga) {
        editor.putString(KEY_PWDSIGA, keyPwdsiga);
        editor.commit();
    }
}

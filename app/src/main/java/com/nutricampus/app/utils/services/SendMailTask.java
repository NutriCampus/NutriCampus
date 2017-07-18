package com.nutricampus.app.utils.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class SendMailTask extends AsyncTask {

    private static final String TAG = "SendMailTask";

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;

    public SendMailTask(Activity activity) {
        sendMailActivity = activity;

    }

    @Override
    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            Log.i(TAG, "Iniciando GMail...");
            publishProgress("Processando dados de entrada....");
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(), (List) args[2], args[3].toString(),
                    args[4].toString());
            publishProgress("Preparando mensagem....");
            androidEmail.createEmailMessage();
            publishProgress("Enviando e-mail...");
            androidEmail.sendEmail();
            publishProgress("E-mail enviado");
            Log.i(TAG, "E-mail enviado.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }

}

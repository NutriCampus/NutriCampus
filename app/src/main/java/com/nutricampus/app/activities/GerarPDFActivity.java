package com.nutricampus.app.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.nutricampus.app.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Felipe on 21/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class GerarPDFActivity extends AppCompatActivity {

    private static final String PATH_APP = "NutriCampus";
    private static final String GERAR = "Relatorios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_pdf);

        Button button = (Button) findViewById(R.id.btnGerarPdf);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarPdfOnClick(view);
            }
        });

        // Retira a exceção causada: FileUriExposedException
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void gerarPdfOnClick(View v) {

        Document document = new Document(PageSize.LETTER);

        String nomeArquivo = "RelatorioNutriCampus.pdf";
        String targetaSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(targetaSD + File.separator + PATH_APP);

        if(!pdfDir.exists()) {
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GERAR);
        if(!pdfSubDir.exists()) {
            pdfSubDir.mkdir();
        }

        String nomeCompleto = Environment.getExternalStorageDirectory() + File.separator + PATH_APP +
                File.separator + GERAR + File.separator + nomeArquivo;

        File outputFile = new File(nomeCompleto);
        if(outputFile.exists())
            outputFile.delete();

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nomeCompleto));

            //Criar o documento
            document.open();
            document.addAuthor("Zé");
            document.addCreator("Create");
            document.addSubject("Teste");
            document.addCreationDate();
            document.addTitle("IRRU");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmltoPdf = "<html><head></head><body><h1>kkkkkkkkkkk</h1> <p> klklklklklklklk </p></body></html>";
            try {
                worker.parseXHtml(pdfWriter, document, new StringReader(htmltoPdf));
                document.close();
                Toast.makeText(GerarPDFActivity.this, "PDF está sendo gerado", Toast.LENGTH_SHORT).show();
                mostrarPdf(nomeCompleto, GerarPDFActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void mostrarPdf(String arquivo, Context context) {
        Toast.makeText(context, "Lendo o PDF", Toast.LENGTH_SHORT).show();

        File file = new File(arquivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GerarPDFActivity.this, "Não tem arquivos para abrir", Toast.LENGTH_SHORT).show();
        }

    }

}

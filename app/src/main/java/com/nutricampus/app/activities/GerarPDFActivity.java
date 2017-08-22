package com.nutricampus.app.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Dieta;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Felipe on 21/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class GerarPDFActivity extends AppCompatActivity {

    private static final String PATH_APP = "NutriCampus";
    private static final String GERAR = "Relatorios";
    private static Dieta dieta;
    SharedPreferencesManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_pdf);

        session = new SharedPreferencesManager(this);

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

    public static void getDieta(Dieta d) {
        dieta = d;
    }

    public void gerarPdfOnClick(View v) {

        ArrayList<Animal> animais = dieta.arrayAnimais;
        Animal animal = animais.get(0);
        Propriedade propriedade = new RepositorioPropriedade(this).buscarPropriedade(animal.getIdPropriedade());
        Proprietario proprietario = new RepositorioProprietario(this).buscarProprietario(propriedade.getIdProprietario());
        Usuario usuario = new RepositorioUsuario(this).buscarUsuario(session.getCrmvNC(), session.getSenha());

        DadosComplAnimal dadosComplAnimal = new RepositorioDadosComplAnimal(this).buscarDadosComplAnimal(animal.getId());
        int idGrupo = dadosComplAnimal.getIdGrupo();
        Grupo grupo = new RepositorioGrupo(this).buscarGrupo(idGrupo);

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
            document.addAuthor("NutriCampus");
            document.addCreator("Create");
            document.addSubject("Teste");
            document.addCreationDate();
            document.addTitle("Relatorio de Dieta");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmltoPdf = "" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<title>Relat&oacute;rio de Dieta - NutriCampus</title>\n" +
                    "\t\t<meta content=\"text/html; charset=us-ascii\" http-equiv=\"content-type\" />\n" +
                    "\t\t<meta content=\"NutriCampus - Tecnologia em Nutrição Animal\" name=\"author\" />\n" +
                    "\t\t<meta content=\"NutriCampus 2017\" name=\"copyright\" />\n" +
                    "\t</head>\n" +
                    "\t<body style=\"margin: 3cm 2cm 2cm 3cm;\">\n" +
                    "\t\t<div style=\"text-align: center;\">\n" +
                    "\t\t\t<p>\n" +
                    "\t\t\t\t<strong><span style=\"font-size:22px;\">Relat&oacute;rio de Dieta</span></strong></p>\n" +
                    "\t\t\t<p>\n" +
                    "\t\t\t\t&nbsp;</p>\n" +
                    "\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" height=\"75\" width=\"649\">\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t<strong>Data e hora:</strong></td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t22/08/2017 19:00:00</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t<strong>Respons&aacute;vel:</strong></td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+usuario.getNome()+"</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tCRMV/CRZ:" + usuario.getCrmv() + "</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" height=\"75\" width=\"649\">\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t<strong>Dieta:</strong></td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+dieta.identificador+"</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t<strong>Propriedade:</strong></td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+propriedade.getNome()+"</td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+proprietario.getNome()+"</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+propriedade.getCidade() +" - " + propriedade.getEstado() +"</td>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t"+proprietario.getTelefone()+"</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" height=\"178\" id=\"comp_alimentar\" width=\"646\">\n" +
                    "\t\t\t\t<caption>\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<strong>Tabela 1. Caracter&iacute;sticas do Grupo "+grupo.getIdentificador()+"</strong></caption>\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Par&acirc;metro</strong></span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Valor m&eacute;dio</strong></span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tPeso vivo:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+dadosComplAnimal.getPesoVivo()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tEscore de Condi&ccedil;&atilde;o Corporal:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t"+dadosComplAnimal.getEec()+"</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tKg/Leite/Dia:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t50 Kg</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tTeor de gordura:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t4,0%</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tSemana de lacta&ccedil;&atilde;o:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t15</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tGanho de peso:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t0,22</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tGestante:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tN&atilde;o</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" height=\"42\" id=\"comp_alimentar\" width=\"646\">\n" +
                    "\t\t\t\t<caption>\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<strong>Tabela 2. Composi&ccedil;&atilde;o Alimentar (Volumoso)</strong></caption>\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Composto</strong></span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Mat&eacute;ria Natural<br />\n" +
                    "\t\t\t\t\t\t\tKg</strong></span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>&nbsp;Mistura<br />\n" +
                    "\t\t\t\t\t\t\t% / Kg</strong></span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tSilagem de Milho</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">59,31</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">100</span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tBagaço de cana</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">50,31</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">98</span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t<br />\n" +
                    "\t\t\t<br />\n" +
                    "\t\t\t<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" height=\"178\" id=\"comp_alimentar\" width=\"646\">\n" +
                    "\t\t\t\t<caption>\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<br />\n" +
                    "\t\t\t\t\t<strong>Tabela 3. NRC 2001</strong></caption>\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td colspan=\"3\">\n" +
                    "\t\t\t\t\t\t\t<strong>Consumo (Kg/Dia)</strong></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tMat&eacute;ria Seca</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tMat&eacute;ria Natural</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tVolumoso:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t14,20</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t45,96</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\tConcentrado:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t16,05</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t19,10</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td colspan=\"3\">\n" +
                    "\t\t\t\t\t\t\t<strong>Rela&ccedil;&atilde;o Volumoso/Concentrado</strong></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tMat&eacute;ria Seca</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\tMat&eacute;ria Natural</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t% Volumoso:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t46,91%</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t71,74%</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t% Concentrado:</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t53,09%</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t28,26%</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td style=\"text-align: left;\">\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t&nbsp;</td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t\t<p>\n" +
                    "\t\t\t\t&nbsp;</p>\n" +
                    "\t\t\t<p>\n" +
                    "\t\t\t\t<strong>Tabela 4. Composi&ccedil;&atilde;o Alimentar (Concentrado)</strong></p>\n" +
                    "\t\t</div>\n" +
                    "\t\t<div style=\"text-align: center;\">\n" +
                    "\t\t\t<table align=\"center\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" height=\"93\" id=\"comp_alimentar\" width=\"644\">\n" +
                    "\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Composto</strong></span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>Mat&eacute;ria Natural<br />\n" +
                    "\t\t\t\t\t\t\tKg</strong></span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:14px;\"><strong>&nbsp;Mistura<br />\n" +
                    "\t\t\t\t\t\t\t% / Kg</strong></span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">MILHO FAR. GLUTEN 21%</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">44,98</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">39,77</span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">SOJA FARELO 48%</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">5,60</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">4,95</span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">FOSFATO BICALCICO</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">2,50</span></td>\n" +
                    "\t\t\t\t\t\t<td>\n" +
                    "\t\t\t\t\t\t\t<span style=\"font-size:12px;\">2,21</span></td>\n" +
                    "\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t</tbody>\n" +
                    "\t\t\t</table>\n" +
                    "\t\t</div>\n" +
                    "\t\t<h1>\n" +
                    "\t\t\t&nbsp;</h1>\n" +
                    "\t</body>\n" +
                    "</html>\n";
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

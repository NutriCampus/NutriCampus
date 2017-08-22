package com.nutricampus.app.entities;

import java.util.ArrayList;

/**
 * Created by Paulo Mateus on 20/08/17.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class Dieta {
    public int id = 0;
    public Propriedade propriedade = null;
    public String identificador = null;
    public ArrayList<CompostosAlimentares> arrayCompostosSelecionados = null;
    public ArrayList<Animal> arrayAnimais = null;
    public ArrayList<CompostoComPorcentagem> arrayObjetoDieta = new ArrayList<>();

    public double proteinaBruta = -1;
    private ArrayList<CompostosAlimentares> fonteProteica = new ArrayList<>();
    private ArrayList<CompostosAlimentares> fonteEnergetica = new ArrayList<>();

    private static final double restricaoEnergetico = 18.0;//PB Acima de 18% composto é proteico

    public static void main(String[] args) {
        ArrayList<CompostosAlimentares> arr = new ArrayList<>();

        CompostosAlimentares c1 = new CompostosAlimentares();
        c1.setIdentificador("Soja");
        c1.setPb(46);
        arr.add(c1);
        CompostosAlimentares c2 = new CompostosAlimentares();
        c2.setIdentificador("Fubá");
        c2.setPb(9);
        arr.add(c2);
        CompostosAlimentares c3 = new CompostosAlimentares();
        c3.setIdentificador("F.Trigo");
        c3.setPb(16);
        arr.add(c3);

        Dieta d = new Dieta(null, 20, null, arr);
    }

    public Dieta() {
    }

    public Dieta(String identificador, double proteinaBruta, ArrayList<Animal> arrayAnimais, ArrayList<CompostosAlimentares> arrayCompostosSelecionados) {
        this.identificador = identificador;
        this.arrayCompostosSelecionados = arrayCompostosSelecionados;
        this.arrayAnimais = arrayAnimais;
        this.proteinaBruta = proteinaBruta;

        calcular();

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void print() {
        System.out.println("ID: " + id);
        System.out.println("Identificador: " + identificador);
        System.out.println("Propriedade: " + propriedade.getId());
        System.out.println("---");
        for (int i = 0; i < arrayAnimais.size(); i++) {
            System.out.println(arrayAnimais.get(i).getIndentificador());
        }
        System.out.println("---");
        for (int i = 0; i < arrayObjetoDieta.size(); i++) {
            arrayObjetoDieta.get(i).print();
        }
        System.out.println("-------------------------------------");

    }

    public void calcular() {
        arrayObjetoDieta = new ArrayList<>();

        //Seleciono quais compostosBD serão compostosBD proteicos/energéticos
        for (int i = 0; i < arrayCompostosSelecionados.size(); i++) {
            if (arrayCompostosSelecionados.get(i).getPb() >= restricaoEnergetico) {//proteico
                fonteProteica.add(arrayCompostosSelecionados.get(i));
                //System.out.println("Proteico: " + arrayCompostosSelecionados.get(i).getIdentificador());
            } else {//energetico
                fonteEnergetica.add(arrayCompostosSelecionados.get(i));
                //System.out.println("Energetico: " + arrayCompostosSelecionados.get(i).getIdentificador());
            }
        }


        //Estou ignorando as restrições por composto, visto q não tem espaço no bd pra isso
        double porcentagem1 = 1.0 / (double) fonteProteica.size();
        double numeroSUPERIOR = 0, numeroINFERIOR = 0;
        //Obter a porcentagem de cada composto, caso seja seja mais de 1
        for (int i = 0; i < fonteProteica.size(); i++) {
            fonteProteica.get(i).porcentagem = fonteProteica.get(i).getPb() * porcentagem1;
            numeroINFERIOR += fonteProteica.get(i).porcentagem;
        }

        double porcentagem2 = 1.0 / (double) fonteEnergetica.size();
        //Obter a porcentagem de cada composto, caso seja seja mais de 1
        for (int i = 0; i < fonteEnergetica.size(); i++) {
            fonteEnergetica.get(i).porcentagem = fonteEnergetica.get(i).getPb() * porcentagem2;
            numeroSUPERIOR += fonteEnergetica.get(i).porcentagem;
        }

        //calculando
        //System.out.println("Numero SUPERIOR: " + numeroSUPERIOR);
        //System.out.println("Numero INFERIOR: " + numeroINFERIOR);
        //System.out.println("--------------------------------------");
        numeroSUPERIOR = Math.abs(proteinaBruta - numeroSUPERIOR);
        numeroINFERIOR = Math.abs(proteinaBruta - numeroINFERIOR);
        //System.out.println("Numero SUPERIOR: " + numeroSUPERIOR);
        //System.out.println("Numero INFERIOR: " + numeroINFERIOR);

        //Porcentagem
        double divisorNormalizar = numeroSUPERIOR + numeroINFERIOR;
        //System.out.println("DIVISOR: " + divisorNormalizar);
        double porcentagemEnergetica = (numeroSUPERIOR / divisorNormalizar);// * 100.0;
        double porcentagemProteica = (numeroINFERIOR / divisorNormalizar);// * 100.0;
        //System.out.println("PORCENTAGEM ENERGETICO: " + porcentagemEnergetica);
        //System.out.println("PORCENTAGEM PROTEICA: " + porcentagemProteica);

        //Fragmentando valores, caso seja mais de 1
        if (fonteEnergetica.size() > 1) {
            for (int i = 0; i < fonteEnergetica.size(); i++) {
                fonteEnergetica.get(i).porcentagem = porcentagem2 * porcentagemEnergetica;
            }
        } else {
            fonteEnergetica.get(0).porcentagem = porcentagemEnergetica;
        }
        //Fragmentando valores, caso seja mais de 1
        if (fonteProteica.size() > 1) {
            for (int i = 0; i < fonteProteica.size(); i++) {
                fonteProteica.get(i).porcentagem = porcentagem1 * porcentagemProteica;
            }
        } else {
            fonteProteica.get(0).porcentagem = porcentagemProteica;
        }
        //Setando array principal
        for (int i = 0; i < fonteProteica.size(); i++) {
            double vr = Math.round((fonteProteica.get(i).porcentagem) * 100.0 * 100.0) / 100.0;
            arrayObjetoDieta.add(new CompostoComPorcentagem(fonteProteica.get(i), vr));
            //arrayObjetoDieta.add(new CompostoComPorcentagem(fonteProteica.get(i), round(fonteProteica.get(i).porcentagem, 2) * 100.00));
        }
        for (int i = 0; i < fonteEnergetica.size(); i++) {
            double vr = Math.round((fonteEnergetica.get(i).porcentagem) * 100.0 * 100.0) / 100.0;
            arrayObjetoDieta.add(new CompostoComPorcentagem(fonteEnergetica.get(i), vr));
            //arrayObjetoDieta.add(new CompostoComPorcentagem(fonteEnergetica.get(i), round(fonteEnergetica.get(i).porcentagem, 2) * 100.00));
        }


        /**
         * TODO. DEBUG
         */
        /*for (int i = 0; i < arrayObjetoDieta.size(); i++) {
            arrayObjetoDieta.get(i).print();
        }*/
        /*for (int i = 0; i < fonteProteica.size(); i++) {
            System.out.println("ID (P): " + fonteProteica.get(i).getIdentificador()
                    + " " + fonteProteica.get(i).porcentagem);
        }
        for (int i = 0; i < fonteEnergetica.size(); i++) {
            System.out.println("ID (E): " + fonteEnergetica.get(i).getIdentificador()
                    + " " + fonteEnergetica.get(i).porcentagem);
        }*/
    }
}

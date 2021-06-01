package br.com.fulltime.app.model;

import br.com.fulltime.app.service.Conversor;

public class Interpretador {

    public static boolean validarHeader (String[] array){
        return array[0].equals("7B");
    }

    public static int identificarTamanho(String[] array){
        return Integer.parseInt(array[1], 16);
    }

    public static int identificarComando(String[] array){
        return Integer.parseInt(array[3], 16);
    }

}

package br.com.fulltime.app.model;

public class Interpretador {

    public static boolean validarHeader(String[] array) {
        return array[0].equals("7B");
    }

    public static int getTamanho(String[] array) {
        return Integer.parseInt(array[1], 16);
    }

    public static int getComando(String[] array) {
        return Integer.parseInt(array[3], 16);
    }

    public static String[] getDados(String[] array) {
        var comando = getComando(array);
        switch (comando) {
            case 33 -> {
                return Dados.lerAutenticacao(array);
            }
            default -> {
                return null;
            }
        }
    }

}

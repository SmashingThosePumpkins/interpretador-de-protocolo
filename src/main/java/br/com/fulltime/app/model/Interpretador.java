package br.com.fulltime.app.model;

public class Interpretador {

    public boolean validarHeader(String[] array) {
        return array[0].equals("7B");
    }

    public int getTamanho(String[] array) {
        return Integer.parseInt(array[1], 16);
    }

    public int getComando(String[] array) {
        return Integer.parseInt(array[3], 16);
    }

    public String[] getDados(String[] array) {
        var comando = getComando(array);
        switch (comando) {
            case 33 -> {
                return Autenticacao.getDados(array);
            }
            case 36 -> {
                return Evento.getDados(array);
            }
            default -> {
                return null;
            }
        }
    }

}

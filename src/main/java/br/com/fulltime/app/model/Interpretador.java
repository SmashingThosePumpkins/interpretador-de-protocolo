package br.com.fulltime.app.model;

public class Interpretador {

    String[] array;

    public Interpretador (String[] array) {
        this.array = array;
    }

    public boolean validarHeader() {
        return array[0].equals("7B");
    }

    public int getTamanho() {
        return Integer.parseInt(array[1], 16);
    }

    public int getComando() {
        return Integer.parseInt(array[3], 16);
    }

    public String[] getDados() {
        var comando = getComando();
        switch (comando) {
            case 33 -> {
                var autenticacao = new Autenticacao();
                return autenticacao.getDados(array);
            }
            case 36 -> {
                var evento = new Evento();
                return evento.getDados(array);
            }
            default -> {
                return null;
            }
        }
    }

}

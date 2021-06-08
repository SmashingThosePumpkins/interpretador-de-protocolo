package br.com.fulltime.app.model;

public class Interpretador {

    final int COD_AUTENTICACAO = 33;
    final int COD_EVENTO = 36;
    final int COD_STATUS = 86;
    String[] array;

    public Interpretador(String[] array) {
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

    public String getComando(boolean pretty) {
        if(!pretty){
            return getComando() + "";
        }

        switch (getComando()) {
            case COD_AUTENTICACAO -> {
                return "AUTENTICAÇÃO";
            }
            case COD_EVENTO -> {
                return "EVENTO";
            }
            case COD_STATUS -> {
                return "STATUS";
            }
        }
        return null;
    }

    public String[] getDados() {
        switch (getComando()) {
            case COD_AUTENTICACAO -> {
                var autenticacao = new Autenticacao();
                return autenticacao.getDados(array);
            }
            case COD_EVENTO -> {
                var evento = new Evento();
                return evento.getDados(array);
            }
            case COD_STATUS -> {
                var status = new Status();
                return status.getDados(array);
            }
            default -> {
                return null;
            }
        }
    }

    public String getDadosFormatados() {
        switch (getComando()) {
            case COD_AUTENTICACAO ->{
                var autenticacao = new Autenticacao();
                return autenticacao.getDadosFormatados(array);
            }
            case COD_EVENTO -> {
                var evento = new Evento();
                return evento.getDadosFormatados(array);
            }
            case COD_STATUS -> {
                var status = new Status();
                return status.getDadosFormatados(array);
            }
            default -> {
                return null;
            }
        }
    }
}

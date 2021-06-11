package br.com.fulltime.app.model;

import br.com.fulltime.app.model.centralservidor.Autenticacao;
import br.com.fulltime.app.model.centralservidor.Evento;
import br.com.fulltime.app.model.centralservidor.Status;

public class Interpretador {

    final int COD_AUTENTICACAO = 33;
    final int COD_EVENTO = 36;
    final int COD_STATUS = 86;
    final int COD_STATUS_2 = 80;
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

    public String getSequencia() {
        return array[2];
    }

    public int getComando() {
        return Integer.parseInt(array[3], 16);
    }

    public String getComando(boolean asString) {
        if (!asString) {
            return getComando() + "";
        }

        return switch (getComando()) {
            case COD_AUTENTICACAO -> "AUTENTICAÇÃO";
            case COD_EVENTO -> "EVENTO";
            case COD_STATUS, COD_STATUS_2 -> "STATUS";
            default -> "null";
        };
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
            case COD_STATUS, COD_STATUS_2 -> {
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
            case COD_AUTENTICACAO -> {
                var autenticacao = new Autenticacao();
                return autenticacao.getDadosFormatados(array);
            }
            case COD_EVENTO -> {
                var evento = new Evento();
                return evento.getDadosFormatados(array);
            }
            case COD_STATUS, COD_STATUS_2 -> {
                var status = new Status();
                return status.getDadosFormatados(array);
            }
            default -> {
                return null;
            }
        }
    }
}

package br.com.fulltime.app.model;

public class Interpretador {

    final int COD_AUTENTICACAO = 33;
    final int COD_EVENTO = 36;
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
            default -> {
                return null;
            }
        }
    }

    public String getDadosFormatados() {
        var dados = getDados();
        String dadosFormatados;
        switch (getComando()) {
            case COD_AUTENTICACAO ->
                dadosFormatados = String.format(
                        """
                        NUMERO DE SERIE = %s
                        IMEI = %s
                        MAC = %s
                        MODELO = %s
                        VERSÃO = %s
                        IP = %s
                        SIMCARD = %s
                        VIA = %s
                        OPERADORA = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8]);
            case COD_EVENTO ->
                dadosFormatados = String.format(
                        """
                        CONTA = %s
                        QUALIFICADOR = %s
                        EVENTO = %s
                        PARTIÇÃO = %s
                        ARGUMENTO = %s
                        CONTADOR = %s
                        STATUS PART. = %s
                        PROBLEMA = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7]);
            default -> {
                return null;
            }
        }
        return dadosFormatados;
    }
}

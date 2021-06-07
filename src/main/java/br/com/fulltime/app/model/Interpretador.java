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
        var dados = getDados();
        String dadosFormatados;
        switch (getComando()) {
            case COD_AUTENTICACAO -> dadosFormatados = String.format("""
                    NUMERO DE SERIE = %s
                    IMEI = %s
                    MAC = %s
                    MODELO = %s
                    VERSÃO = %s
                    IP = %s
                    SIMCARD = %s
                    VIA = %s
                    OPERADORA = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8]);
            case COD_EVENTO -> dadosFormatados = String.format("""
                    CONTA = %s
                    QUALIFICADOR = %s
                    EVENTO = %s
                    PARTIÇÃO = %s
                    ARGUMENTO = %s
                    CONTADOR = %s
                    STATUS PART. = %s
                    PROBLEMA = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7]);
            case COD_STATUS -> dadosFormatados = String.format("""
                    KP = %s
                    DATA / HORA = %s
                    BATERIA = %s
                    ==== PGM ==== 
                    %s
                    === PGM 2 === 
                    %s
                    == PARTIÇÃO == 
                    %s
                    ELETRIFICADOR = %s
                    ZONAS = %s
                    PROBLEMA = %s
                    PERMISSÕES:
                    - ELETRIFICADOR = %s
                    - PGM = %s
                    - PGM 2 = %s
                    - PARTIÇÃO = %s
                    - INIBIR ZONAS = %s
                    PÂNICO = %s
                    PARAR SIRENE = %s
                    ATUALIZAÇÃO = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8], dados[9], dados[10], dados[11], dados[12], dados[13], dados[14], dados[15], dados[16]);
            default -> {
                return null;
            }
        }
        return dadosFormatados;
    }
}

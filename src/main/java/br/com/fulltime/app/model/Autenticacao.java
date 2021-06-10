package br.com.fulltime.app.model;

import java.util.NoSuchElementException;

public class Autenticacao implements Comando {

    public String[] getDados(String[] array) {

        String numSerie = aplicarNumero(array, 4, 14),
                imei = aplicarNumero(array, 14, 29),
                mac = aplicarNumero(array, 29, 41),
                modelo = aplicarModelo(array, 41),
                versao = aplicarVersao(array, 42),
                ip = aplicarIP(array, 45),
                simCard = aplicarSIMCARD(array, 46),
                via = aplicarVia(array, 47),
                operadora = aplicarOperadora(array, 48);


        return new String[]{numSerie, imei, mac, modelo, versao, ip, simCard, via, operadora};
    }

    @Override
    public String getDadosFormatados(String[] array) {
        var dados = getDados(array);
        return String.format(
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
    }

    private String aplicarModelo(String[] array, int index) {

        return switch (array[index]) {
            case "A0" -> "ACTIVE 32 DUO";
            case "A1" -> "ACTIVE 20 ULTRA";
            case "A2" -> "ACTIVE 8 ULTRA";
            case "A3" -> "ACTIVE 20 ETHERNET";
            case "A4" -> "ACTIVE 100 BUS";
            case "A5" -> "ACTIVE 20 BUS";
            default -> "";
        };

    }

    private String aplicarVersao(String[] array, int index) {
        var val1 = (char) Integer.parseInt(array[index], 16);
        var val2 = (char) Integer.parseInt(array[index + 1], 16);
        var val3 = (char) Integer.parseInt(array[index + 2], 16);
        String valB3;
        if (val3 != '0') {
            valB3 = "b" + val3;
        } else {
            valB3 = val3 + "";
        }

        return String.format("v%s.%s.%s", val1, val2, valB3);
    }

    private String aplicarNumero(String[] array, int from, int to) {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append((char) Integer.parseInt(array[from], 16));
            from++;
        } while (from <= (to - 1));
        return stringBuilder.toString();
    }

    private String aplicarIP(String[] array, int index) {
        return switch (array[index]) {
            case "01" -> "IP1";
            case "02" -> "IP2";
            default -> throw new NoSuchElementException();
        };
    }

    private String aplicarSIMCARD(String[] array, int index) {
        return switch (array[index]) {
            case "01" -> "SIM1";
            case "02" -> "SIM2";
            case "03" -> "N/A";
            default -> throw new NoSuchElementException();
        };
    }

    private String aplicarVia(String[] array, int index) {
        return switch (array[index]) {
            case "00" -> "GPRS";
            case "01" -> "ETHERNET";
            default -> throw new NoSuchElementException();
        };
    }

    private String aplicarOperadora(String[] array, int index) {
        EnumOperadoras operadora = EnumOperadoras.getFromValue(array[index]);
        return operadora.name();
    }

}

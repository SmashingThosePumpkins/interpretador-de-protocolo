package br.com.fulltime.app.model;

import java.util.NoSuchElementException;

public class Autenticacao {

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

    private String aplicarModelo(String[] array, int index) {
        switch (array[index]) {
            case "A0" -> {
                return "ACTIVE 32 DUO";
            }
            case "A1" -> {
                return "ACTIVE 20 ULTRA";
            }
            case "A2" -> {
                return "ACTIVE 8 ULTRA";
            }
            case "A3" -> {
                return "ACTIVE 20 ETHERNET";
            }
            case "A4" -> {
                return "ACTIVE 100 BUS";
            }
            case "A5" -> {
                return "ACTIVE 20 BUS";
            }
            default -> throw new NoSuchElementException();
        }
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
//        Arrays.copyOfRange(array, from, to);
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append((char) Integer.parseInt(array[from], 16));
            from++;
        } while (from <= (to - 1));
        return stringBuilder.toString();
    }

    private String aplicarIP(String[] array, int index) {
        switch (array[index]) {
            case "01" -> {
                return "IP1";
            }
            case "02" -> {
                return "IP2";
            }
            default -> throw new NoSuchElementException();
        }
    }

    private String aplicarSIMCARD(String[] array, int index) {
        switch (array[index]) {
            case "01" -> {
                return "SIM1";
            }
            case "02" -> {
                return "SIM2";
            }
            case "03" -> {
                return "N/A";
            }
            default -> throw new NoSuchElementException();
        }
    }

    private String aplicarVia(String[] array, int index) {
        switch (array[index]) {
            case "00" -> {
                return "GPRS";
            }
            case "01" -> {
                return "ETHERNET";
            }
            default -> throw new NoSuchElementException();
        }
    }

    private String aplicarOperadora(String[] array, int index) {
        switch (array[index]) {
            case "00" -> {
                return "VIVO";
            }
            case "01" -> {
                return "BRASIL TELECOM";
            }
            case "02" -> {
                return "CLARO";
            }
            case "03" -> {
                return "OI";
            }
            case "04" -> {
                return "TIM";
            }
            case "05" -> {
                return "TELEMIG CELULAR";
            }
            case "06" -> {
                return "N/A";
            }
            case "07" -> {
                return "CONFIGURAÇÕES DA OPERADORA PERSONALIZADA";
            }
            default -> throw new NoSuchElementException();
        }
    }

}
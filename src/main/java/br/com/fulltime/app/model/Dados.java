package br.com.fulltime.app.model;

import java.util.NoSuchElementException;

public class Dados {

    public static String[] lerAutenticacao(String[] array) {
        String numSerie, imei, mac, modelo, versao, ip, simCard, via, operadora;

        numSerie = aplicarNumero(array, 4, 13);
        imei = aplicarNumero(array, 14, 28);
        mac = aplicarNumero(array, 29, 40);
        modelo = aplicarModelo(array, 41);
        versao = aplicarVersao(array, 42);
        ip = aplicarIP(array, 45);
        simCard = aplicarSIMCARD(array, 46);
        via = aplicarVia(array, 47);
        operadora = aplicarOperadora(array, 48);


        return new String[]{numSerie, imei, mac, modelo, versao, ip, simCard, via, operadora};
    }

//    public static String[] lerEvento(String[] array) {
//
//    }

    private static String aplicarModelo(String[] array, int index) {
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

    private static String aplicarVersao(String[] array, int index) {
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

    private static String aplicarNumero(String[] array, int from, int to) {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append((char) Integer.parseInt(array[from], 16));
            from++;
        } while (from < (to - 1));
        return stringBuilder.toString();
    }

    private static String aplicarIP(String[] array, int index) {
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

    private static String aplicarSIMCARD(String[] array, int index) {
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

    private static String aplicarVia(String[] array, int index) {
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

    private static String aplicarOperadora(String[] array, int index) {
        switch (array[index]) {
            case "00" -> {
                return "OPERADORA 0";
            }
            case "01" -> {
                return "OPERADORA 1";
            }
            case "02" -> {
                return "OPERADORA 2";
            }
            case "03" -> {
                return "OPERADORA 3";
            }
            case "04" -> {
                return "OPERADORA 4";
            }
            case "05" -> {
                return "OPERADORA 5";
            }
            case "06" -> {
                return "OPERADORA 6";
            }
            case "07" -> {
                return "OPERADORA 7";
            }
            default -> throw new NoSuchElementException();
        }
    }

}

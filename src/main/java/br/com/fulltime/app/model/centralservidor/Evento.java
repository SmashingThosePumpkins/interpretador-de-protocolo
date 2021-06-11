package br.com.fulltime.app.model.centralservidor;

import br.com.fulltime.app.model.Comando;

import java.util.NoSuchElementException;

public class Evento implements Comando {

    public String[] getDados(String[] array) {

        String conta = aplicarConta(array, 4),
                qualificador = aplicarQualificador(array, 8),
                evento = aplicarEvento(array, 9),
                particao = aplicarParticao(array, 12),
                argumento = aplicarArgumento(array, 14),
                contador = aplicarContador(array, 17),
                statusPart = aplicarStatusPart(array, 21),
                problema = aplicarProblema(array, 22);

        return new String[]{conta, qualificador, evento, particao, argumento, contador, statusPart, problema};
    }

    @Override
    public String getDadosFormatados(String[] array) {
        var dados = getDados(array);
        return String.format(
                """
                CONTA = %s
                QUALIFICADOR = %s
                EVENTO = %s
                PARTIÇÃO = %s
                ARGUMENTO = %s
                CONTADOR = %s
                STATUS PART. = %s
                PROBLEMA = %s
                """, dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7]);
    }

    private String aplicarConta(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < index + 4; i++) {
            stringBuilder.append((char) Integer.parseInt(array[i], 16));
            if (Integer.parseInt(array[i]) == 41) {
                throw new RuntimeException();
            }
        }
        return stringBuilder.toString();
    }

    private String aplicarQualificador(String[] array, int index) {
        switch (array[index]) {
            case "31" -> {
                return "EVENTO";
            }
            case "33" -> {
                return "RESTAURAÇÃO";
            }
            default -> throw new NoSuchElementException();
        }
    }

    private String aplicarEvento(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < index + 3; i++) {
            stringBuilder.append((char) Integer.parseInt(array[i], 16));
        }
        return stringBuilder.toString();
    }

    private String aplicarParticao(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < index + 2; i++) {
            stringBuilder.append((char) Integer.parseInt(array[i], 16));
        }
        if (Integer.parseInt(stringBuilder.toString()) > 16){
            throw new IllegalArgumentException();
        }
        return stringBuilder.toString();
    }

    private String aplicarArgumento(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < index + 3; i++) {
            stringBuilder.append((char) Integer.parseInt(array[i], 16));
        }
        if (stringBuilder.toString().equals("000")){
            return "N/A";
        }
        return stringBuilder.toString();
    }

    private String aplicarContador(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < index + 4; i++) {
            stringBuilder.append(array[i]);
        }
        return stringBuilder.toString();
    }

    private String aplicarStatusPart(String[] array, int index) {
        return array[index];
    }

    private String aplicarProblema(String[] array, int index) {
        return array[index];
    }

}

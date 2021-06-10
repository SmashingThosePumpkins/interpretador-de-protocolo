package br.com.fulltime.app.model;

import java.util.BitSet;
import java.util.NoSuchElementException;

public class Status implements Comando {
    @Override
    public String[] getDados(String[] array) {
        String kp = aplicarKP(array, 4),
                datahora = aplicarDataHora(array, 6),
                bateria = aplicarBateria(array, 12),
                pgm = aplicarPGM(array, 13),
                pgm2 = aplicarPGM(array, 118),
                particao = aplicarParticao(array, 14),
                eletrificador = aplicarEletrificador(array, 30),
                zonas = aplicarZonas(array, 31),
                problema = aplicarProblema(array, 82),
                permissaoEletrificador = aplicarPermEletrificador(array, 87),
                permissaoPGM = aplicarPermPGM(array, 88),
                permissaoPGM2 = aplicarPermPGM(array, 118),
                permissaoParticao = aplicarPermParticao(array, 89),
                permissaoZonas = aplicarPermZonas(array, 105);

        return new String[]{kp, datahora, bateria, pgm, pgm2, particao, eletrificador, zonas, problema, permissaoEletrificador, permissaoPGM, permissaoPGM2, permissaoParticao, permissaoZonas};
    }

    @Override
    public String getDadosFormatados(String[] array) {
        var dados = getDados(array);

        return String.format("""
                KP = %s
                DATA / HORA = %s
                BATERIA = %s
                ==== PGM ====
                %s
                === PGM 2 ===
                %s
                ==PARTIÇÃO ==
                %s
                =============
                ELETRIFICADOR = %s
                == = ZONAS == =
                %s
                =============
                PROBLEMAS = %s
                =============
                PERMISSÕES:
                - ELETRIFICADOR = %s
                - PGM = %s
                - PGM 2 = %s
                - PARTIÇÃO = %s
                - INIBIR ZONAS = %s""", dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8], dados[9], dados[10], dados[11], dados[12], dados[13]);
    }

    private String aplicarPermZonas(String[] array, int index) {
        return "null";
    }

    private String aplicarPermParticao(String[] array, int index) {
        return "null";
    }

    private String aplicarPermPGM(String[] array, int index) {
        return "null";
    }

    private String aplicarPermEletrificador(String[] array, int index) {
        return "null";
    }

    private String aplicarProblema(String[] array, int index) {
        return String.format("%s-%s-%s-%s-%s", array[index], array[index + 1], array[index + 2], array[index + 3], array[index + 4]);
    }

    private String aplicarZonas(String[] array, int index) {
        var zonas = new StringBuilder();
        var contador = 1;
        for (int i = 0; i < 50; i++) {
            String hex1 = array[index + i].charAt(0) + "";
            String hex2 = array[index + i].charAt(1) + "";

            zonas.append(String.format("ZONA %03d = ", contador++))
                    .append(statusZona(hex1))
                    .append(String.format("\nZONA %03d = ", contador++))
                    .append(statusZona(hex2))
                    .append('\n');
        }
        return zonas.toString().trim();
    }

    private String statusZona(String zona) {
        switch (zona) {
            case "0" -> {
                return "ZONA DESABILITADA";
            }
            case "1" -> {
                return "ZONA INIBIDA";
            }
            case "2" -> {
                return "ZONA EM DESPARO";
            }
            case "3" -> {
                return "SENSOR SEM COMUNICAÇÃO";
            }
            case "4" -> {
                return "ZONA EM CURTO";
            }
            case "5" -> {
                return "TAMPER ABERTO";
            }
            case "6" -> {
                return "BATERIA BAIXA";
            }
            case "7" -> {
                return "ZONA ABERTA";
            }
            case "8" -> {
                return "ZONA FECHADA";
            }
        }
        return "UNKNOWN";
    }

    private String aplicarEletrificador(String[] array, int index) {
        var hex = array[index];
        switch (hex) {
            case "00" -> {
                return "NÃO PROGRAMADO";
            }
            case "01" -> {
                return "DESARMADO, SEM DISPARO";
            }
            case "02" -> {
                return "ARMADO, SEM DISPARO";
            }
            case "81" -> {
                return "DESARMADO, COM DISPARO";
            }
            case "82" -> {
                return "ARMADO, COM DESPARO";
            }
        }
        return "DESCONHECIDO";
    }

    private String aplicarParticao(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        var contador = 0;
        for (int i = index; i < index + 16; i++) {
            stringBuilder.append("PGM ")
                    .append(contador++)
                    .append(" = ")
                    .append(statusParticao(array[index]))
                    .append('\n');
        }
        return stringBuilder.toString().trim();
    }

    private String statusParticao(String hex) {
        var status = new StringBuilder();
        if (hex.charAt(1) == '0') {
            return "NÃO PROGRAMADA";
        }

        // Verificar o estado da partição
        if (hex.charAt(1) == '1') {
            status.append("DESARMADA");
        } else if (hex.charAt(1) == '2') {
            status.append("ARMADA");
        } else {
            status.append("ARMADA STAY");
        }

        // Verificar se está em disparo
        if (hex.charAt(0) == '8') {
            status.append(", EM DISPARO");
        } else {
            status.append(", SEM DISPARO");
        }

        return status.toString();
    }

    private String aplicarPGM(String[] array, int index) {
        if (array.length <= index) {
            return "N/A";
        }

        var hex = array[index];
        BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(hex, 16)});

        StringBuilder stringBuilder = new StringBuilder();
        int contador = 1;
        for (int i = 0; i < 8; i++) {
            stringBuilder.append("PGM ").append(contador++).append(" = ");
            if (bitSet.get(i)) {
                stringBuilder.append("ACIONADA\n");
            } else {
                stringBuilder.append("DESACIONADA\n");
            }
        }
        return stringBuilder.toString().trim();
    }

    private String aplicarBateria(String[] array, int index) {
        var val = (double) Integer.parseInt(array[index], 16);
        String porcentagem;
        double valorDivido = val / 14;

        if (val / 14 > 12.5) {
            porcentagem = "100%";
        } else if (val / 14 > 12) {
            porcentagem = "80%";
        } else if (val / 14 > 11.5) {
            porcentagem = "60%";
        } else if (val / 14 > 11) {
            porcentagem = "40%";
        } else if (val / 14 > 10.5) {
            porcentagem = "20%";
        } else if (val / 14 <= 10.5) {
            porcentagem = "VERMELHO";
        } else if (val / 14 < 10) {
            porcentagem = "0%";
        } else {
            return null;
        }
        return String.format("%s (%sV)", porcentagem, val);
    }

    private String aplicarDataHora(String[] array, int index) {
        var dia = array[index++];
        var mes = array[index++];
        var ano = array[index++];
        var hora = array[index++];
        var minuto = array[index++];
        var segundo = array[index];

        return String.format("%s/%s/%s - %s:%s:%s", dia, mes, ano, hora, minuto, segundo);
    }

    private String aplicarKP(String[] array, int index) {
        return array[index] + array[index + 1];
    }

}

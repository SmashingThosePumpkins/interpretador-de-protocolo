package br.com.fulltime.app.model.centralservidor;

import br.com.fulltime.app.model.Comando;

import java.util.BitSet;

public class Status implements Comando {
    private int numeroDeZonas = 0;

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

        return String.format(
                """
                        KP = %s
                        DATA / HORA = %s
                        BATERIA = %s
                        ==== PGM ====
                        %s
                        === PGM 2 ===
                        %s
                        == PARTIÇÃO ==
                        %s
                        =ELETRIFICADOR=
                        %s
                        == ZONAS ==
                        %s
                        =============
                        PROBLEMAS = %s
                        =============
                        PERMISSÕES:
                        - ELETRIFICADOR
                        %s
                        - PGM
                        %s
                        - PGM 2
                        %s
                        - PARTIÇÃO
                        %s
                        - INIBIR ZONAS
                        %s
                        """, dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6], dados[7], dados[8], dados[9], dados[10], dados[11], dados[12], dados[13]);
    }

    private String aplicarPermZonas(String[] array, int index) {
        var zonas = new StringBuilder();
        String hex;
        int contador = 0;
        for (int i = index; i < index + 13; i++) {
            var bitSet = BitSet.valueOf(new long[]{Long.valueOf(array[i], 16)});
            for (int j = 0; j < 8; j++) {
                contador++;
                if (contador <= numeroDeZonas) {
                    zonas.append("     PGM ").append(contador).append(" = ");
                    if (bitSet.get(j)) {
                        zonas.append("PERMITIDO\n");
                    } else {
                        zonas.append("NÃO PERMITIDO\n");
                    }
                }
            }
        }
        zonas.deleteCharAt(zonas.length() - 1);
        return zonas.toString();
    }

    private String aplicarPermParticao(String[] array, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        var contador = 0;
        for (int i = index; i < index + 16; i++) {
            stringBuilder.append("      PGM ")
                    .append(contador++)
                    .append(" = ")
                    .append(statusPermParticao(array[index]))
                    .append('\n');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private String statusPermParticao(String hex) {
        return switch (hex.charAt(1)) {
            case '0' -> "PERMISSÃO PARA DESARMAR A PARTIÇÃO";
            case '1' -> "PERMISSÃO PARA ARMAR A PARTIÇÃO";
            case '2' -> "PERMISSÃO PARA ARMAR A PARTIÇÃO EM STAY";
            case '3' -> "PERMISSÃO PARA ARMAR A PARTIÇÃO EM AWAY";
            case '4' -> "A PARTIÇÃO ESTÁ PRONTA";
            default -> "DESCONHECIDO";
        };
    }

    private String aplicarPermPGM(String[] array, int index) {
        if (array.length <= index) {
            return "     N/A";
        }

        var hex = array[index];
        BitSet bitSet = BitSet.valueOf(new long[]{Long.valueOf(hex, 16)});

        StringBuilder stringBuilder = new StringBuilder();
        int contador = 1;
        for (int i = 0; i < 8; i++) {
            stringBuilder.append("     PGM ").append(contador++).append(" = ");
            if (bitSet.get(i)) {
                stringBuilder.append("PERMITIDO\n");
            } else {
                stringBuilder.append("NÃO PERMITIDO\n");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private String aplicarPermEletrificador(String[] array, int index) {
        return switch (array[index]) {
            case "00" -> "     ACIONAMENTO NÃO PERMITIDO";
            case "01" -> "     ACIONAMENTO PERMITIDO";
            default -> "     DESCONHECIDO";
        };
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

            if (!hex1.equals("0")) {
                zonas.append(String.format("ZONA %03d = ", contador++))
                        .append(statusZona(hex1))
                        .append('\n');
                numeroDeZonas++;
            }
            if (!hex2.equals("0")) {
                zonas.append(String.format("ZONA %03d = ", contador++))
                        .append(statusZona(hex2))
                        .append('\n');
                numeroDeZonas++;
            }
        }
        return zonas.toString().trim();
    }

    private String statusZona(String zona) {
        return switch (zona) {
            case "0" -> "ZONA DESABILITADA";
            case "1" -> "ZONA INIBIDA";
            case "2" -> "ZONA EM DESPARO";
            case "3" -> "SENSOR SEM COMUNICAÇÃO";
            case "4" -> "ZONA EM CURTO";
            case "5" -> "TAMPER ABERTO";
            case "6" -> "BATERIA BAIXA";
            case "7" -> "ZONA ABERTA";
            case "8" -> "ZONA FECHADA";
            default -> "UNKNOWN";
        };
    }

    private String aplicarEletrificador(String[] array, int index) {
        return switch (array[index]) {
            case "00" -> "NÃO PROGRAMADO";
            case "01" -> "DESARMADO, SEM DISPARO";
            case "02" -> "ARMADO, SEM DISPARO";
            case "81" -> "DESARMADO, COM DISPARO";
            case "82" -> "ARMADO, COM DESPARO";
            default -> "UNKNOWN";
        };
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
        var div = val / 14.0;
        String porcentagem;
        double valorDivido = val / 14;

        if (div > 12.5) {
            porcentagem = "100%";
        } else if (div > 12) {
            porcentagem = "80%";
        } else if (div > 11.5) {
            porcentagem = "60%";
        } else if (div > 11) {
            porcentagem = "40%";
        } else if (div > 10.5) {
            porcentagem = "20%";
        } else if (div <= 10.5) {
            porcentagem = "VERMELHO";
        } else if (div < 10) {
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

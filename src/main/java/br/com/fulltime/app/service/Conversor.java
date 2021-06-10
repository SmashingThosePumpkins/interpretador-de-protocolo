package br.com.fulltime.app.service;

import java.util.Vector;

public class Conversor {

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String byteArrayToHexString(byte[] data, boolean pretty) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
            if (pretty) {
                r.append(" ");
            }
        }
        return r.toString();
    }

    public static byte[] fromHexString(String rawData) {
        int len = rawData.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int nextPosition = i + 1;
            data[i / 2] = (byte) ((Character.digit(rawData.charAt(i), 16) << 4)
                    + Character.digit(rawData.charAt(nextPosition), 16));
        }

        return data;
    }

    public static String[] toHexArray(String rawData) {
        String[] data = new String[rawData.length()];

        // Caso haja RegEx's
        if (rawData.contains(" ")) {
            data = rawData.split(" ");
        } else if (rawData.contains("\\")) {
            data = rawData.split("\\\\");
        } else {
        // Caso não hajam RegEx's
            var contador = 0;
            for(int i = 0; i < rawData.length() / 2; i++) {
                data[i] = rawData.charAt(contador++) + "" + rawData.charAt(contador++);
            }
        }



        // Caso o primeiro dígito esteja vazio
        if (data[0].isEmpty()) {
            var temp = new String[data.length - 1];
            System.arraycopy(data, 1, temp, 0, data.length - 1);
            return temp;
        }

        return data;
    }

}

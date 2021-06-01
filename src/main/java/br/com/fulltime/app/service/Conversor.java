package br.com.fulltime.app.service;

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
}

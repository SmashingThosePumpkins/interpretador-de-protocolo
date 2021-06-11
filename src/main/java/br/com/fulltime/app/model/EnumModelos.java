package br.com.fulltime.app.model;

import java.util.stream.Stream;

public enum EnumModelos {
    ACTIVE_32_DUO("A0"),
    ACTIVE_20_ULTRA("A1"),
    ACTIVE_8_ULTRA("A2"),
    ACTIVE_20_ETHERNET("A3"),
    ACTIVE_100_BUS("A4"),
    ACTIVE_20_BUS("A5"),
    DESCONHECIDA("A6");

    public String value;

    EnumModelos(String value) {
        this.value = value;
    }

    public static EnumModelos getFromValue (String valor) {

        return Stream.of(EnumModelos.values())
                .filter(operadora -> operadora.value.equals(valor))
                .findFirst()
                .orElse(DESCONHECIDA);

    }
}
package br.com.fulltime.app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public enum EnumOperadoras {
    VIVO("00"),
    BRASIL_TELECOM("01"),
    CLARO("02"),
    OI("03"),
    TIM("04"),
    TELEMIG_CELULAR("05"),
    DESCONHECIDA("06"),
    OPERADORA_PERSONALIZADA("07");

    public String value;

    EnumOperadoras(String value) {
        this.value = value;
    }

    public static EnumOperadoras getFromValue (String valor) {

        return Stream.of(EnumOperadoras.values())
                .filter(operadora -> operadora.value.equals(valor))
                .findFirst()
                .orElse(DESCONHECIDA);

    }
}

package br.com.fulltime.app;

import br.com.fulltime.app.service.Conversor;
import br.com.fulltime.app.model.Interpretador;

public class Main {

    public static void main(String[] args) {

        var hexArray = Conversor.toHexArray("7B 66 00 21 32 37 35 31 34 36 37 34 39 31 33 35 33 37 31 39 30 39 31 36 32 35 34 32 34 30 34 39 31 36 32 30 38 30 37 32 42 A4 35 35 30 01 01 01 06 00 01 10 10 10 02 02 00 03 00 04 00 05 00 06 00 07 00 08 00 09 00 10 00 11 00 12 00 13 00 14 00 15 00 16 00 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 E5 ");
        System.out.println(Interpretador.validarHeader(hexArray));
        System.out.println(Interpretador.identificarTamanho(hexArray));
        System.out.println(Interpretador.identificarComando(hexArray));

    }

}

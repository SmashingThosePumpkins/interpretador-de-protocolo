package br.com.fulltime.app;

import br.com.fulltime.app.model.Interpretador;
import br.com.fulltime.app.service.Conversor;

import javax.swing.*;

public class MainJOP {

    public static void main(String[] args) {

        MainJOP main = new MainJOP();
        main.start();

    }

    void start() {

        boolean runningStatus = true;

        do {

            String input = JOptionPane.showInputDialog(null, "Digite o código HEX correspondente ao comando a ser executado.");
            var interpretador = new Interpretador(Conversor.toHexArray(input));
            JOptionPane.showMessageDialog(null, interpretador.getDadosFormatados());


        } while (runningStatus);

    }

}

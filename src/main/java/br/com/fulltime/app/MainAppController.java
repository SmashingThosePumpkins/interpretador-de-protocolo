package br.com.fulltime.app;

import br.com.fulltime.app.model.Interpretador;
import br.com.fulltime.app.service.Conversor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

    @FXML
    public TextArea display;
    @FXML
    public TextField input;
    @FXML
    public Button botaoInterpretar;
    @FXML
    public Button botaoLimpar;
    @FXML
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String dataTemp = "";

    @FXML
    public void onClickDisplayDados(ActionEvent event) {

        if (input.getText().trim().charAt(0) == '>') {
            dissect(input.getText().substring(1).trim());
            input.setText("");
            return;
        }

        dataTemp = (dataTemp + " " + input.getText().trim()).trim();
        input.setText("");
        var interpretador = new Interpretador(Conversor.toHexArray(dataTemp));

        try {
            if (Conversor.toHexArray(dataTemp).length < interpretador.getTamanho()) {
                display.setText("Mensagem parcial. Insira o resto para prosseguir.");
                return;
            }

            if (!interpretador.validarHeader()) {
                dataTemp = "";
                throw new RuntimeException();
            }

            display.setText(formatar("" + interpretador.getTamanho(),
                    "" + interpretador.getComando(true),
                    interpretador.getSequencia(),
                    interpretador.getDadosFormatados()));
            dataTemp = "";
        } catch (RuntimeException ex) {
            display.setText("Erro! Mensagem inválida.\nCertifique-se que a mensagem foi inserida corretamente (Hexadecimal)");
        }

    }

    private void dissect(String command) {
        if (command.startsWith("mode")) {
            mode(command.substring(4).trim());
        }
    }

    @FXML
    public void mode(String mode) {
        if (mode.startsWith("dark")) {
            anchorPane.setStyle("-fx-background-color: #0a0a0a");
        }
        if (mode.startsWith("light")) {
            anchorPane.setStyle("-fx-background-color: #ffffff");
        }
        if (mode.startsWith("red")) {
            anchorPane.setStyle("-fx-background-color: #923131");
        }
    }


    private String formatar(String tamanho, String comando, String sequencia, String dados) {
        return "============\n" +
                "TAMANHO DO PACOTE: " + tamanho + " BYTES" + "\n" +
                "COMANDO: " + comando + "\n" +
                "SEQUÊNCIA: " + sequencia + "\n" +
                "===========\n" + dados + "===========\n";
    }


    @FXML
    public void onClickLimpar(ActionEvent event) {
        display.setText("");
    }

}

package br.com.fulltime.app;

import br.com.fulltime.app.model.Interpretador;
import br.com.fulltime.app.service.Conversor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void onClickDisplayDados(ActionEvent event) {

        try {
            display.setText(formatar(input.getText()));
            input.setText("");
        } catch (RuntimeException ex) {
            display.setText("Erro! Mensagem inválida.\nCertifique-se que a mensagem foi inserida corretamente (Hexadecimal)");
        }

    }

    private String formatar(String rawData) {
        var interpretador = new Interpretador(Conversor.toHexArray(rawData));
        if (!interpretador.validarHeader()) {
            throw new RuntimeException();
        }
        return "============\n" +
                "TAMANHO DO PACOTE: " + interpretador.getTamanho() + " BYTES" + "\n" +
                "COMANDO: " + interpretador.getComando(true) + "\n" +
                "SEQUÊNCIA: " + interpretador.getSequencia() + "\n" +
                "===========\n" +
                interpretador.getDadosFormatados() +
                "===========\n";
    }


    @FXML
    public void onClickLimpar(ActionEvent event) {
        display.setText("");
    }

}

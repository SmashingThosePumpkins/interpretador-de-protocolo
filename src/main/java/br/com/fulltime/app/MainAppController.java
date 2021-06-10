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
            var rawData = input.getText();
            input.setText("");
            Interpretador interpretador;
            try {
                interpretador = new Interpretador(Conversor.toHexArray(rawData));
            } catch (RuntimeException ex) {
                display.setText("Erro! Mensagem inválida.\nCertifique-se que a mensagem foi inserida corretamente (Hexadecimal)");
                return;
            }
            var stringBuilder = new StringBuilder();

            if (!interpretador.validarHeader()) {
                display.setText("Erro! Header inválido.\nCertifique-se que a mensagem foi inserida corretamente.");
                return;
            }

            stringBuilder.append("==========================\n");
            stringBuilder.append("TAMANHO DO PACOTE: ").append(interpretador.getTamanho()).append("\n");
            stringBuilder.append("COMANDO: ").append(interpretador.getComando(true)).append("\n");
            //        stringBuilder.append("SEQUÊNCIA: " + interpretador.getSequencia() + "\n");
            stringBuilder.append("==========================\n");
            stringBuilder.append(interpretador.getDadosFormatados());
            stringBuilder.append("\n==========================\n");

            display.setText(stringBuilder.toString());
        } catch (RuntimeException ex) {
            display.setText("Erro! Mensagem inválida.\nCertifique-se que a mensagem foi inserida corretamente (Hexadecimal)");
        }

    }

    @FXML
    public void onClickLimpar(ActionEvent event) {
        display.setText("");
    }

}

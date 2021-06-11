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

    private String dataTemp = "";

    @FXML
    public void onClickDisplayDados(ActionEvent event) {

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

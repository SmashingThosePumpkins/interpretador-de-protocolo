package br.com.fulltime.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        System.out.println("Exemplo de pacote de autenticação: 7B 66 00 21 32 37 35 31 34 36 37 34 39 31 33 35 33 37 31 39 30 39 31 36 32 35 34 32 34 30 34 39 31 36 32 30 38 30 37 32 42 A4 35 35 30 01 01 01 06 00 01 10 10 10 02 02 00 03 00 04 00 05 00 06 00 07 00 08 00 09 00 10 00 11 00 12 00 13 00 14 00 15 00 16 00 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 E5");
        System.out.println("Exemplo de pacote de evento: \\7B\\18\\01\\24\\31\\30\\31\\30\\31\\33\\30\\32\\30\\31\\30\\30\\30\\00\\00\\2C\\AA\\01\\01\\F1");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainApp.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

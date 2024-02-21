package com.example.practica1;

import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class HelloApplication extends Application {
    TextField txtNum,txtDen;
    FlowPane pane;
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(buildGUI(), 700, 600);
        stage.setTitle("Fracciones");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Parent buildGUI() {
        VBox vBox = new VBox(10);

        txtNum = new TextField();
        txtNum.setPrefWidth(60);
        txtNum.setMaxWidth(60);
        txtNum.setPrefHeight(60);
        txtNum.setMaxHeight(60);

        txtDen = new TextField();
        txtDen.setPrefWidth(60);
        txtDen.setMaxWidth(60);
        txtDen.setPrefHeight(60);
        txtDen.setMaxHeight(60);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Asegúrate de que solo sean dígitos
                return change;
            }
            return null; // Si no son dígitos, ignora el cambio
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);

        // Aplicar el TextFormatter al TextField
        txtNum.setTextFormatter(textFormatter);
        txtDen.setTextFormatter(textFormatter2);

        txtDen.setStyle("-fx-font-size: 15;-fx-alignment: CENTER");
        txtNum.setStyle("-fx-font-size: 15;-fx-alignment: CENTER");

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(5);
        rectangle.setWidth(60);

        Button btncalcular = new Button("Calcular");
        Label lblespacio = new Label("  ");
        Button btnreset = new Button("Resetear");

        btnreset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                limpiar();
            }
        });

        btncalcular.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                valida();
            }
        });

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(btncalcular,lblespacio,btnreset);

        pane = new FlowPane();
        initPane();

        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(txtNum,rectangle,txtDen,hBox2,pane);
        vBox.setPadding(new Insets(15));

        return vBox;
    }

    private void initPane() {
        pane.getChildren().clear();
        Label lbl1 = new Label();
        lbl1.setMaxHeight(30);
        lbl1.setPrefHeight(30);
        lbl1.setPrefWidth(150);
        lbl1.setMaxWidth(150);
        lbl1.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000");
        pane.getChildren().add(lbl1);
        pane.setAlignment(Pos.CENTER);
    }

    private void valida() {
        if(txtDen.getCharacters().isEmpty() || txtNum.getCharacters().isEmpty()){
            Alert builder = new Alert(Alert.AlertType.WARNING);
            builder.setHeaderText("Verificar datos");
            builder.setTitle("Advertencia!");
            builder.setContentText("Tienes algun/algunos campos(s) en blanco, llena todos los campos para continuar");
            builder.show();
        }else{
            if(Integer.parseInt(txtNum.getCharacters().toString()) > Integer.parseInt(txtDen.getCharacters().toString())){
                Alert builder = new Alert(Alert.AlertType.ERROR);
                builder.setHeaderText("Fraccion Impropia");
                builder.setTitle("Error!");
                builder.setContentText("No puedes trabajar con fracciones impropias, por favor ingresa una fraccion propia para continuar");
                builder.show();

            }else{
                cuadros();
            }
        }

    }

    private void cuadros() {
        int den,num;
        den = Integer.parseInt(txtDen.getCharacters().toString());
        num = Integer.parseInt(txtNum.getCharacters().toString());

        pane.getChildren().clear();

        for (int i = 0; i < num; i++) {
            Rectangle rectangle = createColoredRectangle(Color.BLUE,30,new Insets(5));
            pane.getChildren().add(rectangle);
        }

        // Crear los cuadros para representar las partes restantes de la fracción
        for (int i = 0; i < den - num; i++) {
            Rectangle rectangle = createColoredRectangle(Color.WHITE,30,new Insets(5));
            pane.getChildren().add(rectangle);
        }

    }

    private Rectangle createColoredRectangle(Color color, double size, Insets padding) {
        Rectangle rectangle = new Rectangle(size, size); // Tamaño del cuadro
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);

        // Aplicar relleno alrededor del rectángulo
        StackPane.setMargin(rectangle, padding);
        return rectangle;
    }

    private void limpiar() {
        txtDen.clear();
        txtNum.clear();
        initPane();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.mycompany.app;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World!");
        VBox root = new VBox();
        Scene scene = new Scene(root, 300, 250);
        
        root.getChildren().add(new Label("Za Warudo!"));
        root.getChildren().add(new Text("Za Warudo!"));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
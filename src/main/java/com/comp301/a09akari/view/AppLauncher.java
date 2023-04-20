package com.comp301.a09akari.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("Play Akari!");

    BorderPane fullGameView = new BorderPane();

    fullGameView.setCenter(new GridPane());

    Scene gameView = new Scene(fullGameView, 300, 250);
  }
}

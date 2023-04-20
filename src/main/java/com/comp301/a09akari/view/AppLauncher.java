package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("Play Akari!");

    // Create puzzle library from sample puzzles
    PuzzleLibrary offeredPuzzles = new PuzzleLibraryImpl();
    offeredPuzzles.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    offeredPuzzles.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    offeredPuzzles.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    offeredPuzzles.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    offeredPuzzles.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(offeredPuzzles);
    ClassicMvcController controller = new ControllerImpl(model);
    GameView view = new GameView(model, controller);

    Scene scene = view.getScene();
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.show();
  }
}

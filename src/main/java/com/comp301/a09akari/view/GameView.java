package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameView implements FXComponent, ModelObserver {
  private final Scene scene;
  private final PuzzleView puzzle;
  private final HeaderView header;

  public GameView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.puzzle = new PuzzleView(model, controller);
    this.header = new HeaderView(model, controller);
    this.scene = new Scene(render(), 400, 450);
    model.addObserver(this);
  }

  @Override
  public Parent render() {
    BorderPane rootPane = new BorderPane();
    rootPane.setTop(header.render());
    rootPane.setCenter(puzzle.render());
    return rootPane;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }

  public Scene getScene() {
    return scene;
  }
}

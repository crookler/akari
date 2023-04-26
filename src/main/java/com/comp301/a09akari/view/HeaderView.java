package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HeaderView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public HeaderView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox formatting = new VBox();
    formatting.setAlignment(Pos.CENTER);
    formatting.getChildren().clear();

    Label welcome = new Label("Welcome to Akari!");
    formatting.getChildren().add(welcome);

    Label puzzleNumber =
        new Label(
            "Currently Playing Puzzle "
                + (model.getActivePuzzleIndex() + 1)
                + " of "
                + model.getPuzzleLibrarySize());
    formatting.getChildren().add(puzzleNumber);

    Button previousPuzzle = new Button("<- Previous Puzzle");
    previousPuzzle.setOnAction((ActionEvent) -> controller.clickPrevPuzzle());
    Button shuffle = new Button("Shuffle");
    shuffle.setOnAction((ActionEvent) -> controller.clickRandPuzzle());
    Button reset = new Button("Reset");
    reset.setOnAction((ActionEvent) -> controller.clickResetPuzzle());
    Button nextPuzzle = new Button("Next Puzzle ->");
    nextPuzzle.setOnAction((ActionEvent) -> controller.clickNextPuzzle());

    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    buttons.getStyleClass().add("header");
    buttons.getChildren().addAll(previousPuzzle, shuffle, reset, nextPuzzle);
    formatting.getChildren().add(buttons);

    Label winning = new Label("");
    if (model.isSolved()) {
      winning.setText("Puzzle is Solved!");
    }
    formatting.getChildren().add(winning);

    return formatting;
  }
}

package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlsView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public ControlsView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox controls = new VBox();
    controls.setAlignment(Pos.CENTER);

    Button reset = new Button("Reset");
    reset.setOnAction((ActionEvent) -> controller.clickResetPuzzle());
    controls.getChildren().add(reset);

    if (model.isSolved()) {
      Label winning = new Label();
      winning.setText("Puzzle is Solved!");
      controls.getChildren().add(winning);
    }

    return controls;
  }
}

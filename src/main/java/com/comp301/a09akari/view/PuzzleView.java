package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PuzzleView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public PuzzleView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException();
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.getChildren().clear();

    int rowsBoundary = model.getActivePuzzle().getHeight();
    int columnsBoundary = model.getActivePuzzle().getWidth();

    for (int rows = 0; rows < rowsBoundary; rows++) {
      for (int columns = 0; columns < columnsBoundary; columns++) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");
        Rectangle cellDisplay = new Rectangle(30, 30);

        if (model.getActivePuzzle().getCellType(rows, columns)
            == CellType.CORRIDOR) { // handle corridor (lamp/lit/unlit)
          if (model.isLit(rows, columns)) {
            if (model.isLamp(rows, columns) && model.isLampIllegal(rows, columns)) {
              cellDisplay.setFill(Color.RED);
            } else if (model.isLamp(rows, columns)) {
              cellDisplay.setFill(Color.ORANGE);
            } else {
              cellDisplay.setFill(Color.YELLOW);
            }
          } else {
            cellDisplay.setFill(Color.WHITE);
          }

          int currentRow = rows;
          int currentColumn = columns;

          // Add button to each corridor that will place at lamp at that location if called
          Button lampControl = new Button();
          lampControl.setPrefHeight(30);
          lampControl.setPrefWidth(30);
          lampControl.getStyleClass().add("lampControl");
          lampControl.setOnAction((ActionEvent) -> controller.clickCell(currentRow, currentColumn));
          cell.getChildren().add(cellDisplay);
          cell.getChildren().add(lampControl);
        } else if (model.getActivePuzzle().getCellType(rows, columns)
            == CellType.CLUE) { // handle clue (satisfied vs unsatisfied)
          if (model.isClueSatisfied(rows, columns)) {
            cellDisplay.setFill(Color.DARKTURQUOISE);
          } else {
            cellDisplay.setFill((Color.BLACK));
          }
          Label clueNumber = new Label("" + model.getActivePuzzle().getClue(rows, columns));
          clueNumber.setTextFill(Color.WHITE);
          clueNumber.getStyleClass().add("clueNumber");
          cell.getChildren().add(cellDisplay);
          cell.getChildren().add(clueNumber);
        } else if (model.getActivePuzzle().getCellType(rows, columns) == CellType.WALL) {
          cellDisplay.setFill(Color.BLACK);
          cell.getChildren().add(cellDisplay);
        } else {
          throw new IllegalStateException("Malformed puzzle");
        }

        grid.add(cell, columns, rows);
      }
    }

    return grid;
  }
}

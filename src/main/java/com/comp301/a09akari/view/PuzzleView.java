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

        if (model.getActivePuzzle().getCellType(rows, columns) == CellType.CORRIDOR) {
          if (model.isLit(rows, columns)) {
            if (model.isLamp(rows, columns) && model.isLampIllegal(rows, columns)) {
              Rectangle cellDisplay = new Rectangle(30, 30);
              cellDisplay.setFill(Color.RED);
              cell.getChildren().add(cellDisplay);
            } else if (model.isLamp(rows, columns)) {
              Rectangle cellDisplay = new Rectangle(30, 30);
              cellDisplay.setFill(Color.ORANGE);
              cell.getChildren().add(cellDisplay);
            } else {
              Rectangle cellDisplay = new Rectangle(30, 30);
              cellDisplay.setFill(Color.YELLOW);
              cell.getChildren().add(cellDisplay);
            }
          } else {
            Rectangle cellDisplay = new Rectangle(30, 30);
            cellDisplay.setFill(Color.WHITE);
            cell.getChildren().add(cellDisplay);
          }

          /*
          if (model.isLamp(rows, columns))
          {
              //TODO add lamp icon
          }
          */

          int currentRow = rows;
          int currentColumn = columns;

          Button lampControl = new Button();
          lampControl.setPrefHeight(30);
          lampControl.setPrefWidth(30);
          lampControl.getStyleClass().add("lampControl");
          lampControl.setOnAction((ActionEvent) -> controller.clickCell(currentRow, currentColumn));
          cell.getChildren().add(lampControl);
        } else if (model.getActivePuzzle().getCellType(rows, columns) == CellType.CLUE) {
          Rectangle cellDisplay = new Rectangle(30, 30);
          cellDisplay.setFill(Color.BLACK);
          Label clueNumber = new Label("" + model.getActivePuzzle().getClue(rows, columns));
          clueNumber.setTextFill(Color.WHITE);
          cell.getChildren().addAll(cellDisplay, clueNumber);
        } else if (model.getActivePuzzle().getCellType(rows, columns) == CellType.WALL) {
          Rectangle cellDisplay = new Rectangle(30, 30);
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

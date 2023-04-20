package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException();
    }

    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();

    // At least one more puzzle to go forward to in library
    if (currentIndex < (model.getPuzzleLibrarySize() - 1)) {
      model.setActivePuzzleIndex(currentIndex + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();

    // At least one more puzzle to go down to in library
    if (currentIndex > 0) {
      model.setActivePuzzleIndex(currentIndex - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int randomIndex = (int) Math.round((model.getPuzzleLibrarySize() - 1) * Math.random());

    // keep randomizing until new puzzle number
    while (randomIndex == currentIndex) {
      randomIndex = (int) Math.round((model.getPuzzleLibrarySize() - 1) * Math.random());
    }

    model.setActivePuzzleIndex(randomIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    // Only change state if user clicked on corridor cell
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}

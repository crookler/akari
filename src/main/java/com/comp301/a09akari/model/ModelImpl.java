package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model
{
    private PuzzleLibrary library;
    private int activeIndex;
    private Puzzle activePuzzle;
    private int[][] lamps;
    private List<ModelObserver> activeObservers;

    public ModelImpl(PuzzleLibrary library)
    {
        if (library == null)
        {
            throw new IllegalArgumentException();
        }

        this.library = library;
        this.activeIndex = 0;
        this.activePuzzle = library.getPuzzle(activeIndex);
        this.lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
        this.activeObservers = new ArrayList<ModelObserver>();
    }

    @Override
    public void addLamp(int r, int c)
    {
        //This call will throw index out of bounds if r/c are invalid
        if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
        {
            throw new IllegalArgumentException();
        }

        lamps[r][c] = 1;
    }

    @Override
    public void removeLamp(int r, int c)
    {
        if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
        {
            throw new IllegalArgumentException();
        }

        lamps[r][c] = 0;
    }

    @Override
    public boolean isLit(int r, int c)
    {
        if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
        {
            throw new IllegalArgumentException();
        }

        return isLamp(r,c) || checkSurroundingsForLamp(r, c);
    }

    @Override
    public boolean isLamp(int r, int c)
    {
        if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR)
        {
            throw new IllegalArgumentException();
        }

        return lamps[r][c] == 1;
    }

    @Override
    public boolean isLampIllegal(int r, int c)
    {
        if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR
            || lamps[r][c] != 1)
        {
            throw new IllegalArgumentException();
        }

        //If lamp found return false but if no lamp found return true
        return !checkSurroundingsForLamp(r, c);
    }

    @Override
    public Puzzle getActivePuzzle()
    {
        return activePuzzle;
    }

    @Override
    public int getActivePuzzleIndex()
    {
        return activeIndex;
    }

    @Override
    public void setActivePuzzleIndex(int index)
    {
        if (index < 0 || index >= getPuzzleLibrarySize())
        {
            throw new IllegalArgumentException();
        }

        this.activeIndex = index;
        this.activePuzzle = library.getPuzzle(activeIndex);
    }

    @Override
    public int getPuzzleLibrarySize()
    {
        return library.size();
    }

    @Override
    public void resetPuzzle()
    {
        this.lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    }

    @Override
    public boolean isSolved()
    {
        int rowBoundary = activePuzzle.getHeight();
        int columnBoundary = activePuzzle.getWidth();

        //Check each space in the puzzle to see if it is satisfied
        for (int row = 0; row < rowBoundary; row++)
        {
            for (int column = 0; column < columnBoundary; column++)
            {
                if (activePuzzle.getCellType(row, column) == CellType.CORRIDOR
                    && !isLit(row, column)) //all corridors must be lit
                {
                    return false;
                }
                else if (activePuzzle.getCellType(row, column) == CellType.CLUE
                        && !isClueSatisfied(row, column)) //all clues must be satisfied
                {
                    return false;
                }
            }
        }

        return true; //all checks were passed above so the puzzle is solved
    }

    @Override
    public boolean isClueSatisfied(int r, int c)
    {
        //will throw index out of bounds exception if r or c invalid
        if (activePuzzle.getCellType(r, c) != CellType.CLUE)
        {
            throw new IllegalArgumentException();
        }

        int lampCount = 0;

        //Check column to left
        if (c > 0 && activePuzzle.getCellType(r,c-1) == CellType.CORRIDOR
            && lamps[r][c-1] == 1)
        {
            lampCount++;
        }

        //Check column to right
        if (c < activePuzzle.getWidth()-1 && activePuzzle.getCellType(r,c+1) == CellType.CORRIDOR
                && lamps[r][c+1] == 1)
        {
            lampCount++;
        }

        //Check row above
        if (r > 0 && activePuzzle.getCellType(r-1,c) == CellType.CORRIDOR
                && lamps[r-1][c] == 1)
        {
            lampCount++;
        }

        //Check row below
        if (r < activePuzzle.getHeight()-1 && activePuzzle.getCellType(r+1,c) == CellType.CORRIDOR
                && lamps[r+1][c] == 1)
        {
            lampCount++;
        }

        return lampCount == activePuzzle.getClue(r, c);
    }

    @Override
    public void addObserver(ModelObserver observer)
    {
        activeObservers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer)
    {
        activeObservers.remove(observer);
    }

    //Return true if there is a lamp in either the same row or the
    //same column as the inputted coordinates
    private boolean checkSurroundingsForLamp(int r, int c)
    {
        //Check current row for lamp
        for (int direction = 1; direction >= -1; direction -= 2)
        {
            int offset = 0;
            int column = c;

            //Check row for valid indices or until a non-corridor is hit
            while (column >= 0 && column < activePuzzle.getWidth()
                    && activePuzzle.getCellType(r, column) == CellType.CORRIDOR)
            {
                if (lamps[r][column] == 1) //found lamp
                {
                    return true;
                }

                offset++;
                column = c+(direction*offset);
            }
        }

        //Check current column for lamp
        for (int direction = 1; direction >= -1; direction -= 2)
        {
            int offset = 0;
            int row = r;

            //Check column for valid indices or until a non-corridor is hit
            while (row >= 0 && row < activePuzzle.getHeight()
                    && activePuzzle.getCellType(row, c) == CellType.CORRIDOR)
            {
                if (lamps[row][c] == 1) //found lamp
                {
                    return true;
                }

                offset++;
                row = r+(direction*offset);
            }
        }

        return false; //neither row nor column has a lamp
    }

    private void notifyObservers()
    {
        for (ModelObserver observer :activeObservers)
        {
            observer.update(this);
        }
    }
}

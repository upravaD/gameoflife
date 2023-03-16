package org.example;

public class GameOfLife {
    private GameInitializer init;

    public void game(String input, String output) {
        init = new GameInitializer(input);

        while (init.getIterations() > 0) {
            nextGeneration();
            init.printString();
            init.decrementIterations();
        }

        init.saveArrayToFile(init.getField(), output);
    }

    private void nextGeneration() {
        Cell[][] nextField = new Cell[init.getRows()][init.getColumns()];

        for (int i = 0; i < init.getRows(); i++) {
            for (int j = 0; j < init.getColumns(); j++) {
                int count = countNeighbors(i, j);
                boolean isAlive = init.getField()[i][j].isAlive();

                if (isAlive && (count < 2 || count > 3)) {
                    // Клетка умирает от одиночества или перенаселения
                    nextField[i][j] = new Cell(false);
                } else if (!isAlive && count == 3) {
                    // Новая клетка рождается из-за окружения трех живых клеток
                    nextField[i][j] = new Cell(true);
                } else {
                    // Клетка остается в том же состоянии
                    nextField[i][j] = new Cell(isAlive);
                }
            }
        }
        init.setField(nextField);
    }

    private int countNeighbors(int x, int y) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) continue;
                int row = (i + init.getRows()) % init.getRows();
                int col = (j + init.getColumns()) % init.getColumns();
                if (init.getField()[row][col].isAlive()) count++;
            }
        }
        return count;
    }
}
package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameOfLife {
    private int rows;
    private int columns;
    private int iterations;
    private Cell[][] field;

    public void game(String input, String output) {
        try {
            initFromFile(input);
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла: " + e.getMessage());
        }

        while (iterations > 0) {
            nextGeneration();
            printString(convertToString(field));
            System.out.println();
            iterations--;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
        saveArrayToFile(field, output);
    }

    private void nextGeneration() {
        Cell[][] nextField = new Cell[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int count = countNeighbors(i, j);
                boolean isAlive = field[i][j].isAlive();

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

        field = nextField;
    }

    private int countNeighbors(int x, int y) {
        int count = 0;
        int numRows = field.length;
        int numCols = field[0].length;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) continue;
                int row = (i + numRows) % numRows;
                int col = (j + numCols) % numCols;
                if (field[row][col].isAlive()) count++;
            }
        }
        return count;
    }

    private void initFromFile(String fileName) throws IOException {
        // Инициализация параметров игры
        BufferedReader file = new BufferedReader(new FileReader("src/test/resources/" + fileName));
        String[] gameInit = file.readLine().split(",");
        rows = Integer.parseInt(gameInit[0]);
        columns = Integer.parseInt(gameInit[1]);
        iterations = Integer.parseInt(gameInit[2]);
        field = new Cell[rows][columns];

        // Инициализация игрового поля
        int row = 0;
        String line;
        String[][] arr = new String[rows][columns];
        while ((line = file.readLine()) != null && row < rows) {
            String[] tokens = line.split(" ");
            for (int col = 0; col < rows && col < tokens.length; col++) {
                arr[row][col] = tokens[col];
            }
            row++;
        }
        //print
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                System.out.print(arr[i][j] + " ");
//            }
//            System.out.println();
//        }
        file.close();

        // Конвертация массива
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean isAlive = Objects.equals(arr[i][j], "X");
                field[i][j] = new Cell(isAlive);
            }
        }
    }

    public void printField() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(field[i][j].isAlive() + " ");
            }
            System.out.println();
        }
    }
    public void printString(String[][] arr) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Cell[][] getField() {
        return field;
    }

    public String[][] convertToString(Cell[][] arr) {
        String[][] str = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String isAlive;
                if (arr[i][j].isAlive()) {
                    isAlive = "X";
                } else {
                    isAlive = "O";
                }
                str[i][j] = isAlive;
            }
        }
        return str;
    }

    public void saveArrayToFile(Cell[][] arr, String output) {
        try (FileWriter writer = new FileWriter("src/test/resources/" + output)) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    if (arr[0][j].isAlive()) {
                        writer.write("X");
                        if (j != arr[0].length - 1) {
                            writer.write(" ");
                        }
                    } else {
                        writer.write("O");
                        if (j != arr[0].length - 1) {
                            writer.write(" ");
                        }
                    }
                }
                if (i != arr[0].length - 1) {
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        GameOfLife game = new GameOfLife();
        game.game("inputGlider.txt", "outputGlider.txt");
    }
}

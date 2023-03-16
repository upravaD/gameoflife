package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class GameInitializer {
    private int rows;
    private int columns;
    private int iterations;
    private Cell[][] field;

    public GameInitializer(String filename) {
        initFromFile(filename);
    }

    private void initFromFile(String fileName) {
        try {
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
            file.close();
            convertToBoolean(arr);

        } catch (IOException e) {
            System.err.println("Ошибка чтения из файла: " + e.getMessage());
        }
    }

    public void saveArrayToFile(Cell[][] array, String output) {
        try (FileWriter writer = new FileWriter("src/test/resources/" + output)) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++)
                    if (array[i][j].isAlive()) {
                        writer.write("X");
                        if (j != array[0].length - 1) writer.write(" ");
                    } else {
                        writer.write("O");
                        if (j != array[0].length - 1) writer.write(" ");
                    }
                if (i != array[0].length - 1) writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private void convertToBoolean(String[][] array) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++) {
                boolean isAlive = Objects.equals(array[i][j], "X");
                field[i][j] = new Cell(isAlive);
            }
    }

    private String[][] convertToString(Cell[][] arr) {
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

    public void printString() {
        String[][] arr = convertToString(field);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nIterations left:"  + (iterations-1) + "\n");
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getIterations() {
        return iterations;
    }

    public void decrementIterations() {
        this.iterations--;
    }

    public Cell[][] getField() {
        return field;
    }

    public void setField(Cell[][] field) {
        this.field = field;
    }
}

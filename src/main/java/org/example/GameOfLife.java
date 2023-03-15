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

    }

    private void nextGeneration() {

    }

    private void countNeighbors() {

    }

    private void initFromFile(String fileName) throws IOException {
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
//        assert inputStream != null;
//        Scanner scanner = new Scanner(inputStream);
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                boolean isAlive = scanner.nextInt() == 1;
//                field[i][j] = new Cell(isAlive);
//            }
//        }
//
//        scanner.close();

        // Инициализация параметров игры
        BufferedReader file = new BufferedReader(new FileReader("src/test/resources/" + fileName));
        String[] gameInit = file.readLine().split(",");
        rows = Integer.parseInt(gameInit[0]);
        columns = Integer.parseInt(gameInit[1]);
        iterations = Integer.parseInt(gameInit[2]);
        field = new Cell[rows][columns];

        // Инициализация игрового поля

        System.out.println(Arrays.deepToString(field));
    }

    public Cell[][] getField() {
        return field;
    }

    public static void main(String[] args) throws IOException {
        GameOfLife game = new GameOfLife();
        game.initFromFile("inputGlider.txt");
    }
}

package org.example;

public class Main {
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        game.game("inputGlider.txt", "outputGlider.txt");
    }
}

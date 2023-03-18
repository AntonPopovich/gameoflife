package org.example;

import java.io.*;
import java.util.stream.Stream;

public class GameOfLife {
    String X = "X";
    String O = "O";
    int sizeY;
    int sizeX;
    String[][] initial;
    int iterations;
    String folder = "src/test/resources/";

    public void game(String input, String output) {
        StringBuilder result;

        readFile(input);
        result = generate(initial, iterations);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(folder + output))) {
            writer.write(result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder generate(String[][] initial, int iterations) {
        StringBuilder result = new StringBuilder();
        String[][] generation;
        int sizeY = initial.length;
        int sizeX =initial[0].length;
        while (iterations > 0) {
            generation = new String[sizeY][sizeX];
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {

                    int x1 = x + 1;
                    int x_1 = x - 1;
                    int y1 = y + 1;
                    int y_1 = y - 1;

                    if ((x-1) < 0) {
                        x_1 = sizeX - 1;
                    }
                    if ((x+1) == sizeX) {
                        x1 = 0;
                    }
                    if ((y-1) < 0) {
                        y_1 = sizeY - 1;
                    }
                    if ((y+1) == sizeY) {
                        y1 = 0;
                    }
                    Stream<String> stream = Stream.of(
                            initial[y][x1],
                            initial[y1][x1],
                            initial[y1][x],
                            initial[y1][x_1],
                            initial[y][x_1],
                            initial[y_1][x_1],
                            initial[y_1][x],
                            initial[y_1][x1]);
                    int neighbours = (int) stream.filter(el -> el.equals(X)).count();

                    if (initial[y][x].equals(X)) {
                        if (neighbours == 2 || neighbours == 3) {
                            generation[y][x] = X;
                        } else generation[y][x] = O;
                    } else if (neighbours == 3) {
                        generation[y][x] = X;
                    } else generation[y][x] = O;
                }
            }
            initial = generation;
            iterations--;
        }
        for (String[] strings : initial) {
            if (!result.toString().equals("")) {
                result.append("\n");
            }
            for (int x = 0; x < sizeX; x++) {
                result.append(strings[x]).append(" ");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return result;
    }

    private void readFile(String input) {

        try (BufferedReader br = new BufferedReader(new FileReader(folder + input))) {
            String s;
            s = br.readLine();
            String[] data = s.split(",", 3);
            iterations = Integer.parseInt(data[2]);
            sizeX = Integer.parseInt(data[1]);
            sizeY = Integer.parseInt(data[0]);

            initial = new String[sizeY][sizeX];

            int i = 0;
            while ((s = br.readLine()) != null) {
                data = s.split(" ", sizeX);
                System.arraycopy(data, 0, initial[i], 0, sizeX);
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

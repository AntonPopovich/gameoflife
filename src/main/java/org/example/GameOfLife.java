package org.example;

import java.io.*;

public class GameOfLife {
    public void game(String input, String output) {
            String folder = "src/test/resources/";
            int iterations;
            int sizeY;
            int sizeX;
            String[][] initial;
            String[][] generation;
            String X = "X";
            String O = "O";
            StringBuilder result = new StringBuilder();

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

        while (iterations > 0) {
            generation = new String[sizeY][sizeX];
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    int neighbours = 0;

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

                    if(initial[y][x1].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y1][x1].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y1][x].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y1][x_1].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y][x_1].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y_1][x_1].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y_1][x].equals(X)) {
                        neighbours++;
                    }
                    if(initial[y_1][x1].equals(X)) {
                        neighbours++;
                    }

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
            for (int y = 0; y < sizeY; y++) {
                if (!result.toString().equals("")) {
                    result.append("\n");
                }
                for (int x = 0; x < sizeX; x++) {
                    result.append(initial[y][x]).append(" ");
                }
                result = new StringBuilder(result.substring(0, result.length() - 1));
            }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(folder + output))) {
            writer.write(result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

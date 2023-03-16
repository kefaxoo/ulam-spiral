package by.kefaxoo.frame;

import java.awt.*;
import java.util.ArrayList;
import java.io.*;

public class Frame extends java.awt.Frame {

    private boolean isPrime = false;

    public Frame(int size, boolean isPrime) {
        setSize(size, size);
        setBackground(null);
        setVisible(true);
        setTitle("Ulam spiral");
        setResizable(false);
        this.isPrime = isPrime;
        int x = size / 2 - 20;
        int y = size / 2 + 20;
        int dx = 1;
        int dy = 0;
        int step = 1;
        int turn = 1;
        int number = 1;
        double angle = 0.0;
        int maxValue = 1;
        while ((x > 5 && x + 5 < size) && (y > 20 && y + 20 < size)) {
            if (number - 1 == turn) {
                angle += 90;
                if ((dx == 0 && dy == -1) || (dx == 0 && dy == 1)) {
                    step++;
                }

                turn += step;
                dx = (int)Math.cos(Math.toRadians(angle));
                dy = (int)Math.sin(Math.toRadians(-angle));
            }

            drawSpiral(number, x, y, angle);
            x += dx * 50;
            y += dy * 50;
            number += 1;
            maxValue = number;
        }

        if (isPrime) {
            var listOfNumbers = new ArrayList<ArrayList<Integer>>();

            for (int i = 2; i <= maxValue; i++) {
                if (isPrimeNumber(i, 2)) {
                    if (listOfNumbers.size() < decimalToBytes(i).length() / 8) {
                        listOfNumbers.add(new ArrayList<Integer>());
                    }

                    listOfNumbers.get((decimalToBytes(i).length() / 8) - 1).add(i);
                }
            }

            try (FileOutputStream fos = new FileOutputStream("result.bin")) {
                StringBuilder line = new StringBuilder();
                for (var numbers: listOfNumbers) {
                    line.append(numbers.size() + " ");
                    for (int i = 0; i < numbers.size(); i++) {
                        line.append(numbers.get(i));
                        if (i != numbers.size() - 1) {
                            line.append(" ");
                        } else {
                            line.append('\n');
                        }
                    }

                    fos.write(String.valueOf(line).getBytes(), 0, String.valueOf(line).getBytes().length);
                    line = new StringBuilder();
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }


    }

    private boolean isPrimeNumber(int n, int step) {
        if (step == n || n < 3) {
            return true;
        }

        if (n % step == 0) {
            return false;
        }

        return isPrimeNumber(n, step + 1);
    }

    private String decimalToBytes(int decimalValue) {
        var binaryString = new StringBuffer(Integer.toBinaryString(decimalValue));
        while (binaryString.length() % 8 != 0) {
            binaryString.insert(0, '0');
        }

        return String.valueOf(binaryString);
    }

    private void drawSpiral(int number, int x, int y, double angle) {
        Label numberLabel = new Label(String.valueOf(number));
        if (isPrime) {
            if (!isPrimeNumber(number, 2) || number == 1) {
                if (angle % 180 == 0) {
                    numberLabel.setText("-");
                } else {
                    numberLabel.setText("|");
                }
            }
        }


        numberLabel.setBounds(x - 5, y - 20, 30, 30);
        System.out.println("Number: " + number + " x: " + x + " y: " + y);
        this.add(numberLabel);
    }
}

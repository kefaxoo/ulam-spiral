package by.kefaxoo.frame;

import java.awt.*;

public class Frame extends java.awt.Frame {
    public Frame(int size) {
        setSize(size, size);
        setBackground(null);
        setVisible(true);
        setTitle("Ulam spiral");
        setResizable(false);
        int x = size / 2 - 20;
        int y = size / 2 + 20;
        int dx = 1;
        int dy = 0;
        int step = 1;
        int turn = 1;
        int number = 1;
        double angle = 0.0;
        do {
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
        } while ((x > 0 && x < size) && (y > 0 && y < size));
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

    private void drawSpiral(int number, int x, int y, double angle) {
        Label numberLabel = new Label(String.valueOf(number));
        if (!isPrimeNumber(number, 2) || number == 1) {
            if (angle % 180 == 0) {
                numberLabel.setText("-");
            } else {
                numberLabel.setText("|");
            }
        }

        numberLabel.setBounds(x - 5, y - 20, 30, 30);
        this.add(numberLabel);
    }
}

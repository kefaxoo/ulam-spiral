package by.kefaxoo.frame;

import javax.swing.*;
import java.util.ArrayList;
import java.io.*;

public class Frame extends javax.swing.JFrame {

    private boolean isPrime = false; // свойство для показа только простых чисел
    private int previousX; // координата предыдущей точки по х
    private int previousY; // по у

    public Frame(int size, boolean isPrime) {
        setSize(size, size); // окно размера
        setBackground(null); // установка фонового цвета окна
        setVisible(true); // будет ли видно окно
        setTitle("Ulam spiral"); // название окна
        setResizable(false); // можно ли изменять размер окна
        this.isPrime = isPrime; // сохраняем в свойство объекта информации о выводе только простых чисел
        int x = size / 2 - 20; // вычисляем координаты первого значения по х
        previousX = x;
        int y = size / 2 - 30; // по у
        previousY = y;
        int dx = 1; // это переменная для изменения координаты по х для следующего числа
        int dy = 0; // по у
        int step = 1; // переменная для шага
        int turn = 1; // для поворота
        int number = 1; // для числа
        double angle = 0.0; // для угла
        int maxValue = 1; // переменная максимального числа
        while ((x > 0 && x < size) && (y > 0 && y < size)) { // пока мы находимся в окне (по координатам)
            if (number - 1 == turn) { // если вдруг пришёл момент для поворота
                angle += 90; // добавляем к углу 90 градусов
                if ((dx == 0 && dy == -1) || (dx == 0 && dy == 1)) { // если число находится, где х == константа
                    step++; // + 1 значение к шагу
                }

                turn += step; // к повороту добавляем количество шагов
                dx = (int)Math.cos(Math.toRadians(angle)); // вычисляем по какой координате двигаемся (будет или 0 или 1)
                dy = (int)Math.sin(Math.toRadians(-angle));
            }

            drawSpiral(number, x, y, angle); // вызываем функцию рисования цифр
            previousX = x; // сохраняем в предыдущие координаты нарисованное число
            previousY = y;
            x += dx * 80; // вычисляем коордианты следующего числа
            y += dy * 80;
            number += 1; // увеличиваем следующее число на +1
            maxValue = number; // и сохраняем его
        }

        var label = new JLabel(""); // создаём пустой лэйбл для "обхода" бага с магическим перемещением последнего элемента
        label.setBounds(-100, -100, 10, 10); // делаем для него привязку
        this.add(label); // и добавляем на экран

        if (isPrime) { // затем если окно с простыми числами
            var listOfNumbers = new ArrayList<ArrayList<Integer>>(); // создаём матрицу с простыми числами

            for (int i = 2; i <= maxValue; i++) { // в которой проходимся от 2 до максимального числа
                if (isPrimeNumber(i, 2)) { // если число в итерации цикла простое
                    if (listOfNumbers.size() < decimalToBytes(i).length() / 8) { // проверяем его количество байтов, если строки для его количества байтов нет
                        listOfNumbers.add(new ArrayList<Integer>()); // создаём новую строку
                    }

                    listOfNumbers.get((decimalToBytes(i).length() / 8) - 1).add(i); // записываем в нужную строку число
                }
            }

            try (FileOutputStream fos = new FileOutputStream("result.bin")) { // затем мы создаём объект для записи в бинарный файл
                StringBuilder line = new StringBuilder(); // создаём строку для записи
                for (var numbers: listOfNumbers) { // проходимся по всей матрице
                    line.append(numbers.size() + " "); // и первым значением в строке будет количество цифр в строке матрице
                    for (int i = 0; i < numbers.size(); i++) { // затем мы проходимся по строке
                        line.append(numbers.get(i)); // и добавляем каждое число
                        if (i != numbers.size() - 1) { // если число не последнее
                            line.append(" "); // добавим разделитель пробел
                        } else { // иначе
                            line.append('\n'); // перейдём на новую строку
                        }
                    }

                    fos.write(String.valueOf(line).getBytes(), 0, String.valueOf(line).getBytes().length); // а затем записываем в бинарный файл эту строку
                    line = new StringBuilder(); // и очищаем строку, чтобы при следующей записи в ней не было уже записанных данных
                }
            } catch (IOException exception) { // а если мы не смогли записать
                System.out.println(exception.getMessage()); // будет ерор
            }
        }
    }

    private boolean isPrimeNumber(int n, int step) { // рекурсивная функция на проверку является ли числом простым
        if (step == n || n < 3) { // если step равен числу или число меньше трёх
            return true; // оно простое
        }

        if (n % step == 0) { // если число делится на step без остатка
            return false; // то оно не простое
        }

        return isPrimeNumber(n, step + 1); // и вызовем снова эту функцию с шагом +1
    }

    private String decimalToBytes(int decimalValue) { // функция перевода десятичного числа в байты
        var binaryString = new StringBuffer(Integer.toBinaryString(decimalValue)); // строка с двоичным представлением десятичного числа
        while (binaryString.length() % 8 != 0) { // и если его количество байт не кратно восьми
            binaryString.insert(0, '0'); // будет до него добавлять нули
        }

        return String.valueOf(binaryString); // и вернём число в байтовом представлении
    }

    private void drawSpiral(int number, int x, int y, double angle) { // функция рисования числа на спирали
        var numberLabel = new JLabel(String.valueOf(number)); // лейбл с числом
        if (isPrime) { // если сейчас спираль только с простыми числами
            if (!isPrimeNumber(number, 2) || number == 1) { // и если это число не простое
                if (angle % 180 == 0) { // проверяем какая палка будет вместо числа
                    numberLabel.setText("-");
                } else {
                    numberLabel.setText("|");
                }
            }
        }

        numberLabel.setBounds(x, y, 30, 30); // выставляем лейблу расположение в окне
        this.add(numberLabel); // и добавляем на экран это число/палочку

        if (previousX != x || previousY != y) { // если это не первое число
            var lineLabel = new JLabel(); // создаём новый лабел на палочку между числами
            if (previousX == x) { // если координаты чисел по х равны
                lineLabel.setText("|"); // будет вертикальная палочка
                if (previousY - y < 0) { // здесь вычисляется знак у дельта у, если дельта меньше нуля
                    lineLabel.setBounds(x, previousY + 40, 20, 20); // то относительно предыдущего числа палочка будет ниже по у (хоть и здесь +)
                } else {
                    lineLabel.setBounds(x, previousY - 40, 20, 20); // ну или выше
                }
            } else if (previousY == y) { // the same, но если у равен
                lineLabel.setText("-");
                if (previousX - x < 0) {
                    lineLabel.setBounds(previousX + 40, y, 20, 20); // здесь по-нормальному, то есть + вправо
                } else {
                    lineLabel.setBounds(previousX - 40, y, 20, 20); // а если -, то в лево
                }
            }

            this.add(lineLabel); // ну и добавляем палочку
        }
    }
}

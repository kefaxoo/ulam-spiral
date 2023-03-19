import by.kefaxoo.frame.Frame;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (var fis = new FileInputStream("result.bin")) { // здесь создаём объект потока чтения из файла
            byte[] buffer = new byte[256]; // создаём массив байтов для чтения из файла
            System.out.println("File data:"); // вывод сообщения о чтении данных из файла

            int count; // переменная для количества символов в файле
            while((count=fis.read(buffer))!=-1){ // пока это количество не равно -1 (количество берётся из файла (максимально 256 байтов)

                for(int i=0; i<count;i++){ // выводим все эти байты

                    System.out.print((char)buffer[i]); // переводя в символы
                }
            }
        } catch (IOException exception) { // если не получилось создать объект (в основном из-за отсутствия файла в директории)
            System.out.println(exception.getMessage()); // выведем ошибку
        }

        Frame frame = new Frame(600, true); // создание окна с спиралью, первый параметр - размер окна, второй - только ли простые числа в спирали
        Frame allNumbers = new Frame(600, false);
    }
}
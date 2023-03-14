import by.kefaxoo.frame.Frame;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (var fis = new FileInputStream("result.bin")) {
            byte[] buffer = new byte[256];
            System.out.println("File data:");

            int count;
            while((count=fis.read(buffer))!=-1){

                for(int i=0; i<count;i++){

                    System.out.print((char)buffer[i]);
                }
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        Frame frame = new Frame(1000);
    }
}
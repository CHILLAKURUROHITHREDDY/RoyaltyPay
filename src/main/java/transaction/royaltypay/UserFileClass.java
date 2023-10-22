package transaction.royaltypay;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserFileClass {

    String string;

    UserFileClass() {

        string = "";
        int i;
        try {
            FileInputStream inputFile = new FileInputStream("src/main/userFile.txt");
            while ((i = inputFile.read()) != -1) {
                string = string.concat(String.valueOf(((char) i)));
            }
            inputFile.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setString(String set){

        try {
            FileOutputStream outputFile = new FileOutputStream("src/main/userFile.txt");
            byte[] array = set.getBytes();
            outputFile.write(array);
            outputFile.close();
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
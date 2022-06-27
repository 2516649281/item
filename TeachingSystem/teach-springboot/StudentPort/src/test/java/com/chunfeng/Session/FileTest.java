package com.chunfeng.Session;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class FileTest {

    @Test
    void Test1() {
        File file = new File("../../HeaderImage/1.txt");
        FileOutputStream inputStream = null;
        try {
            inputStream = new FileOutputStream(file);
            inputStream.write(97);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(inputStream);
    }
}

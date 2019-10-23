package singleton;

import java.io.*;
import java.util.logging.Logger;

public class SerializationAttack {
    private static String FILE_PATH = "serialized_singleton.ser";
    private static Logger logger = Logger.getLogger(SerializationAttack.class.getName());

    public static void main(String[] args) {
        SerializableSingleton s = SerializableSingleton.getInstance();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_PATH));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(s);

            outputStream.close();
            fileOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            SerializableSingleton s2;
            SerializableSingleton s3;

            s2 = (SerializableSingleton) objectInputStream.readObject();

            FileInputStream fileInputStream2 = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream2 = new ObjectInputStream(fileInputStream2);
            s3 = (SerializableSingleton) objectInputStream2.readObject();

            // inne obiekty (chyba, że dodamy metodę Object getResolve)
            logger.info(Boolean.toString(s2.equals(s3)));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.info(e.toString());
        }
    }
}

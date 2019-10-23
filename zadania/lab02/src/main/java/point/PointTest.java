package point;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PointTest {
    private static Logger logger = Logger.getLogger(PointTest.class.getName());
    private static String FILE_PATH = "serialized.ser";
    private static String OBJECT_FILE_PATH = "object.dat";

    public static void main(String[] args) {
        Point p1 = new Point(1.0f, 2.0f);
        Point p2 = new Point(4.0f, 3.5f);

        try {
            FileOutputStream fileOutputStream= new FileOutputStream(
                    new File(FILE_PATH)
            );
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(p1);
            outputStream.writeObject(p2);

            outputStream.close();
            fileOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Point readP1 = (Point) objectInputStream.readObject();
            Point readP2 = (Point) objectInputStream.readObject();

            logger.info(Boolean.toString(p1.equals(readP1)));
            logger.info(Boolean.toString(p2.equals(readP2)));

            fileInputStream.close();
            objectInputStream.close();

//            FileInputStream fileInputStream = new FileInputStream(OBJECT_FILE_PATH);
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//            Object obj = objectInputStream.readObject();
//
//            logger.info(obj.toString());


        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }
}

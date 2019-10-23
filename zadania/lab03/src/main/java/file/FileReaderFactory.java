package file;

import file.impl.WavFileReader;
import file.interfaces.IFileReader;

public class FileReaderFactory {
    public static IFileReader getFileReader(Class<? extends IFileReader> clazz) {
        if (WavFileReader.class.isAssignableFrom(clazz)) {
            return new WavFileReader();
        }

        return null;
    }
}

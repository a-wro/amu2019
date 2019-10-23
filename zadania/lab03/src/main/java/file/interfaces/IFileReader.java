package file.interfaces;

import file.data.AbstractFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileReader {
    AbstractFile open(Path path) throws IOException;
}

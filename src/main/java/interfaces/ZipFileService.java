package interfaces;

import java.io.IOException;

/**
 * Created by victoria on 19.07.17.
 */
public interface ZipFileService {

    void unzip(String zipFile, String destPath);

    void zip(String File, String destPath);
}

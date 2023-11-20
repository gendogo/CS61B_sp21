package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: this class used to turn the file created by user to blob in .git folder
 * @Author: whj
 * @Date: 2023-10-28 21:52
 */
public class Blob implements Serializable {
    // hash name of this blob
    private String hashID;
    //content of this blob
    private byte[] bytes;
    // origin name of this blob
    private String fileName;
    // origin path of this blob
    private String filePath;


    public Blob(File file) {
        saveMessage(file);
    }
    public void saveMessage(File file){
        File orginFile = file;
        bytes = Utils.readContents(file);
        filePath = file.getPath();
        fileName = file.getName();
        hashID = Utils.sha1(fileName,filePath,bytes);
    }

    public String getHashID() {
        return hashID;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}

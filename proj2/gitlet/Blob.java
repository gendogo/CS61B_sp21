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
    // origin name of this blob before transferred
    private String fileName;
    // origin path of this blob before transferred
    private String filePath;
    //path of this blob in .gitlet folder
    private String blobPath;
    private Boolean commitStatus;


    public Blob(File file) {
        saveMessage(file);
    }
    public void saveMessage(File file){
        File orginFile = file;
        bytes = Utils.readContents(file);
        filePath = file.getPath();
        fileName = file.getName();
        hashID = Utils.sha1(fileName,filePath,bytes);
        String folderName = hashID.substring(0, 2);
        String blobName = hashID.substring(2);
        StringBuilder stringBuilder = new StringBuilder();
        blobPath = stringBuilder.append("objects").append(Repository.FILESEPARATOR).
                append(folderName).append(Repository.FILESEPARATOR).append(blobName).toString();
        commitStatus = false;
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

    public String getBlobPath() {
        return blobPath;
    }

    public Boolean isCommitted() {
        return commitStatus;
    }

    public void commitThisBlob() {
        this.commitStatus = true;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "hashID='" + hashID + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", blobPath='" + blobPath + '\'' +
                '}';
    }
}

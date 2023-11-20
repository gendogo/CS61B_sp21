package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static gitlet.Utils.sha1;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author whj
 */
public class Commit implements Serializable {

    private String message;
    // The parents of this commit(hashID)
    private List<String> parents;
    //create timestamp
    private String creatTime;
    //key is origin file name,value is hashID
    private Map<String,String> blobPath;
    private String author;
    public static final String AUTHOR = "whj";
    public static final ZoneId ZONEID = ZoneId.of("America/Los_Angeles");
    //path of this commit
    private String commitPath;
    private String hashId;


    public String getHashId() {
        return hashId;
    }
     public void setHashId(){
         hashId = sha1(this.toString());
    }

    public String getCommitPath() {
        StringBuilder stringBuilder = new StringBuilder();
        commitPath = stringBuilder.append("objects").append(Repository.FILESEPARATOR).
                append(hashId.substring(0,2)).append(Repository.FILESEPARATOR).append(hashId.substring(2)).toString();
        return commitPath;
    }

    public Commit(String message) {
        this.message = message;
        this.parents = new ArrayList<>();
        this.blobPath = new HashMap<>();
        this.author = AUTHOR;
        this.creatTime = getTimeWithFormat();
    }

    public static String getTimeWithFormat() {
        ZonedDateTime timeWithoutFormat = Instant.now().atZone(ZONEID);
        String timeWithFormat = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z").format(timeWithoutFormat);
        return timeWithFormat;
    }
    public static String getInitTime(){
        ZonedDateTime timeWithoutFormat = Instant.ofEpochSecond(0L).atZone(Commit.ZONEID);
        String timeWithFormat = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z").format(timeWithoutFormat);
        return timeWithFormat;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParent(String hashIdOfParent) {
        parents.add(hashIdOfParent);
    }

    public String getCreatTime() {
        return creatTime;
    }

    public Map<String, String> getBlobPath() {
        return blobPath;
    }

    public List<String> getBlobIDList(){
        List<String> list = new ArrayList<>(blobPath.values());
        return list;
    }

    public void setBlobPath(Map<String, String> blobPath) {
        this.blobPath = blobPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", parents=" + parents.toString() +
                ", creatTime='" + creatTime + '\'' +
                ", blobList=" + blobPath.toString() +
                ", author='" + author + '\'' +
                '}';
    }
}

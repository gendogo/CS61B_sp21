package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    //get the list from index file which contains blob's name and location
    private Map<String,String> blobList;
    private String author;
    public static final String AUTHOR = "whj";
    public static final ZoneId ZONEID = ZoneId.of("America/Los_Angeles");

    public Commit(String message) {
        this.message = message;
        this.parents = new ArrayList<>();
        this.blobList = new TreeMap<>();
        this.author = AUTHOR;
        ZonedDateTime timeWithoutFormat = Instant.now().atZone(ZONEID);
        String timeWithFormat = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z").format(timeWithoutFormat);
        this.creatTime = timeWithFormat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Map<String, String> getBlobList() {
        return blobList;
    }

    public void setBlobList(Map<String, String> blobList) {
        this.blobList = blobList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", parents=" + parents.toString() +
                ", creatTime='" + creatTime + '\'' +
                ", blobList=" + blobList.toString() +
                ", author='" + author + '\'' +
                '}';
    }
}

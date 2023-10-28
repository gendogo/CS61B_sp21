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
    //get the list from index file which contains blob's name and location
    private Map<String,String> blobList;
    private String author;
    public static final String AUTHOR = "whj";
    public static final ZoneId ZONEID = ZoneId.of("America/Los_Angeles");
    //hash id of this commit


    public String getId() {
        return sha1(this.toString());
    }

    public Commit(String message) {
        this.message = message;
        this.parents = new ArrayList<>();
        this.blobList = new TreeMap<>();
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

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public String getCreatTime() {
        return creatTime;
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

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
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

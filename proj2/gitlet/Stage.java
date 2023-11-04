package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: similar to the index file in git
 * @Author: whj
 * @Date: 2023-11-03 23:15
 */
public class Stage implements Serializable {
    /*contains files has been added before commit,
    key is origin file name,value is path to the blob */
    private Map<String, String> blobList;

    public Stage() {
        blobList = new HashMap<>();
    }

    public Boolean addIntoStage(Blob blob){
        String originFileName = blob.getFileName();
        String path = blob.getBlobPath();
        // File with same origin name has been added to the staging area
        if (blobList.containsKey(originFileName)){
            String anotherPath = blobList.get(originFileName);
            Blob anotherBlob = Utils.readObject(Utils.join(Repository.GITLET_DIR, anotherPath), Blob.class);
            //File is totally same with another one
            if(path == anotherPath){
                //the file has been committed,should not be added to stage
                if(anotherBlob.isCommitted()){
                    blobList.remove(originFileName);
                }
                //the file has not been committed yet, do nothing.
               return false;
            }
        }
        //different file be added
        blobList.put(originFileName, path);
        return true;
    }
}

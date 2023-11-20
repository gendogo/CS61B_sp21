package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: similar to the index file in git
 * @Author: whj
 * @Date: 2023-11-03 23:15
 */
public class Stage implements Serializable {
    /*contains files has been added before commit,
    key is origin file name,value is hashID
     */
    private Map<String, String> blobPath;


    public Stage() {
        blobPath = new HashMap<>();
    }


    public Boolean addIntoStage(Blob blob){
        String originFileName = blob.getFileName();
        String path = blob.getFilePath();
        // File with same origin name has been added to the staging area
        if (blobPath.containsKey(originFileName)){
            String anotherPath = blobPath.get(originFileName);
            Blob anotherBlob = Utils.readObject(Utils.join(Repository.GITLET_DIR, anotherPath), Blob.class);
            //File is totally same with another one
            if(path == anotherPath){
               return false;
            }
        }
        //different file be added
        blobPath.put(originFileName, path);
        return true;
    }

    public Map<String, String> getBlobPath() {
        return blobPath;
    }
    public List<String> getBlobIDList(){
        List<String> list = new ArrayList<>(blobPath.values());
        return list;
    }


}

package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static gitlet.Utils.*;


/**
 * Represents a gitlet repository.
 * does at a high level.
 *
 * @author whj
 */
public class Repository {

    //The current working directory.
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    public static final File MASTERBRANCH = join(HEADS_DIR, "master");
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");
    public static final File STAGEFORADD = join(GITLET_DIR, "stage_add");
    public static final File STAGEFORREMOVE = join(GITLET_DIR, "stage_remove");


    //system-dependent file separator character
    public static final String FILESEPARATOR = System.getProperty("file.separator");
    // used to point to  a commit be created at this time
    public static Commit currentCommit;
    public static String currentBranchName;
    public static Stage currentStage;


    public static boolean isValidateLength(String[] args, int length) {
        if (args.length == length) {
            return true;
        }
        System.out.println("Incorrect operands.");
        return false;
    }

    public static void gitInitCommand(String[] args) {
        if (!isValidateLength(args, 1)) {
            System.exit(0);
        }
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        //Create .git folder
        GITLET_DIR.mkdir();
        //Create .git/objects folder which contains commits and blobs
        OBJECTS_DIR.mkdir();
        //Create .git/refs/ and .git/refs/heads/ folder which contains branch file
        HEADS_DIR.mkdirs();

        try {
            //Create HEAD  file
            HEAD_FILE.createNewFile();
            //create commit for init command
            currentCommit = new Commit("initial commit");
            currentCommit.setCreatTime(Commit.getInitTime());
            //get id of this commit
            currentCommit.setHashId();
            String blobId = currentCommit.getHashId();
            //create the path and blob
            saveCommitBlob(blobId);
            // Head points to master branch, master blob records recent commit
            setHeadAndHeadFolder(MASTERBRANCH, blobId, "master");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void gitAddCommand(String[] args) {
        if (!isValidateLength(args, 2)) {
            System.out.println("In gitlet, only one file may be added at a time.");
            System.exit(0);
        }
        checkInitializion();
        File file = getThisFile(args[1]);
        checkFileExistence(file);

        Blob blob = new Blob(file);
        updateStageArea(blob);
    }

    private static void updateStageArea(Blob blob) {
        currentCommit = getCurrentCommit();
        Stage addStage = getStage(STAGEFORADD);
        Stage removeStage = getStage(STAGEFORREMOVE);

        Map<String, String> blobPathInCurrentCommit = currentCommit.getBlobPath();
        Map<String, String> blobPathInAddStage = addStage.getBlobPath();
        Map<String, String> blobPathInRemoveStage = removeStage.getBlobPath();
        boolean isInCurrentCommit = blobPathInCurrentCommit.containsValue(blob.getHashID());
        boolean isInRemoveStage = removeStage.getBlobPath().containsValue(blob.getHashID());
        boolean isInAddStage = addStage.getBlobPath().containsValue(blob.getHashID());

        // Check if the blob is in the current commit
        if (isInCurrentCommit) {
            return;
        }
        // Check if the blob is already in the add stage area.
        if (!isInAddStage) { //new blob,need to store
            if (isInRemoveStage) {
                blobPathInRemoveStage.remove(blob.getFileName());
                //generate new removeStage file
                Utils.writeObject(STAGEFORREMOVE, removeStage);
            }
            blobPathInAddStage.put(blob.getFileName(), blob.getHashID());
            //update the addStage file and save the blob object
            //update the addStage file
            Utils.writeObject(STAGEFORADD, addStage);
            //save the blob object to this blob file in objects folder
            File blobFile = createBlobFile(blob.getHashID());
            Utils.writeObject(blobFile, blob);
        }
    }


    private static Stage getStage(File stageFile) {
        if (!stageFile.exists()) {
            return new Stage();
        }
        return readObject(stageFile, Stage.class);
    }


    public static void gitCommitCommand(String[] args) {
        //commit -m "message"
        if (!isValidateLength(args, 3)) {
            System.exit(0);
        }
        checkInitializion();

//        currentStage = getCurrentStage();
        Map<String, String> blobIDListFromStage = currentStage.getBlobPath();
        if (blobIDListFromStage == null || blobIDListFromStage.size() == 0) {
            //nothing need to be committed
            System.exit(0);
        }
        currentCommit = getCurrentCommit();
        String parentCommitId = currentCommit.getHashId();
        Map<String, String> parentBlobList = currentCommit.getBlobPath();
        Set<String> keySet = blobIDListFromStage.keySet();
        //combine list of blobs from parent commit and stage
        for (String subKey : keySet) {
            parentBlobList.put(subKey, blobIDListFromStage.get(subKey));
        }
        currentCommit = new Commit(args[2]);
        currentCommit.setBlobPath(parentBlobList);
        currentCommit.setParent(parentCommitId);
        currentCommit.setHashId();
        //remove message in stage file;
        Stage stage = new Stage();
//        Utils.writeObject(STAGE, stage);
        String currentCommitHashId = currentCommit.getHashId();
        //create commit blob in .git/object folder
        saveCommitBlob(currentCommitHashId);
        // Head points to master branch, master blob records recent commit
        currentBranchName = "master";
        setHeadAndHeadFolder(MASTERBRANCH, currentCommitHashId, currentBranchName);
    }


    private static File getThisFile(String file) {
        return Paths.get(file).isAbsolute()
                ? new File(file)
                : join(CWD, file);
    }

    private static void checkFileExistence(File file) {
        if (!file.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
    }

    private static void checkInitializion() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }


    private static void saveCommitBlob(String blobID) {
        File blobFile = createBlobFile(blobID);
        //save the Commit object to this blob file in objects folder
        Utils.writeObject(blobFile, currentCommit);
    }

    private static File createBlobFile(String blobID) {
        //create the folder of this blob
        File blobFolder = join(OBJECTS_DIR, blobID.substring(0, 2));
        if (!blobFolder.exists()) {
            blobFolder.mkdir();
        }
        //create  this blob file
        File blobFile = join(blobFolder, blobID.substring(2));
        try {
            if (!blobFile.exists()) {
                blobFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return blobFile;
    }

    private static Commit getCurrentCommit() {
        return getCommitFromHead();
    }

    private static Commit getCommitFromHead() {
        String headContent = readContents(HEAD_FILE).toString();
        //head is pointing to a branch
        if (headContent.contains("refs/heads/")) {
            String[] split = headContent.split("/");
            currentBranchName = split[split.length - 1];
            //get the hashId of this commit
            String id = readContents(join(HEADS_DIR, currentBranchName)).toString();
            //return commit object
            return readObject(join(OBJECTS_DIR, id.substring(0, 2), id.substring(2)), Commit.class);
        } else { //head is detached
            return readObject(join(OBJECTS_DIR, headContent.substring(0, 2), headContent.substring(2)), Commit.class);
        }
    }

    private static void saveBlob(Blob blob) {
        File blobFile = createBlobFile(blob.getHashID());
        //save the initCommit object to this blob file in objects folder
        Utils.writeObject(blobFile, blob);
    }


    private static void setHeadAndHeadFolder(File branch, String commitID, String currentBranchName) {
        //write the hash of this commit into this \refs\heads\branch file
        if (!branch.exists()) {
            try {
                branch.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Utils.writeContents(branch, commitID);
        // set HEAD point to this branch
        currentBranchName = "refs/heads/" + currentBranchName;
        Utils.writeContents(HEAD_FILE, currentBranchName);
    }
}

package gitlet;

import java.io.File;
import java.io.IOException;

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
    public static final File MASTERBRANCH = join(GITLET_DIR, "refs", "heads", "master");
    // //a list that contains every file that Git has been told to keep track of.
    public static final File INDEX = join(GITLET_DIR, "index");
    public static final File HEADBLOB = join(GITLET_DIR, "HEAD");
    public static final File HEADS = join(GITLET_DIR, "refs", "heads");
    public static final File OBJECTS = join(GITLET_DIR, "objects");
    //system-dependent file separator character
    public static final String FILESEPARATOR = System.getProperty("file.separator");
    // used to point to  a commit be created at this time
    public static Commit currentCommit;



    public static boolean isValidateLength(String[] args, int length) {
        if (args.length == length) {
            return true;
        }
        System.out.println("Incorrect operands.");
        return false;
    }

    public static void gitAddCommand(String[] args) {
        if (!isValidateLength(args, 2)) {
            System.out.println("In gitlet, only one file may be added at a time.");
            System.exit(0);
        }
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        File file = join(CWD, args[1]);
        if(!file.exists()){
            System.out.println("File does not exist");
            System.exit(0);
        }
        //TODO create stagingArea object and write it into index file
        //TODO generate blob folder
        //TODO copy the file in working area into blob folder with hash name
        //TODO generate blob name :hash into index file


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
        OBJECTS.mkdir();
        //Create .git/refs/ and .git/refs/heads/ folder which contains branch file
        HEADS.mkdirs();

        try {
            //Create HEAD and index file
            HEADBLOB.createNewFile();
            INDEX.createNewFile();
            //create commit for init command
            currentCommit = new Commit("initial commit");
            currentCommit.setCreatTime(Commit.getInitTime());
            //get id of this commit
            String blob = currentCommit.getId();
            //create the path and blob
            createCommitBlob(blob);
            // Head points to master branch, master blob records recent commit
            setHeadAndBranch(MASTERBRANCH, blob,"master");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static void createCommitBlob(String blob) throws IOException {
        //create the folder of this blob
        File blobFolder = join(GITLET_DIR, "objects", blob.substring(0, 2));
        if(!blobFolder.exists()){
            blobFolder.mkdir();
        }
        //create  this blob file
        File blobFile = join(blobFolder, blob.substring(2));
        if(!blobFile.exists()){
            blobFile.createNewFile();
        }
        //save the initCommit object to this blob file in objects folder
        Utils.writeObject(blobFile, currentCommit);
    }

    private static void setHeadAndBranch(File branch, String commitID, String messageInHeadBlob) throws IOException {
        updateBranchBlob(branch,commitID);
        // set HEAD point to this branch
        messageInHeadBlob = "refs/heads/" + messageInHeadBlob;
        setHeadMessage(messageInHeadBlob);
    }
    //write the hash of this commit into this \refs\heads\branch file
    private static void updateBranchBlob(File branch, String commitID) throws IOException {
        if(!branch.exists()){
            branch.createNewFile();
        }
        Utils.writeContents(branch, commitID);
    }

    // record the commit that the HEAD points to
    private static void setHeadMessage(String x) {
        Utils.writeContents(HEADBLOB, x);
    }


}

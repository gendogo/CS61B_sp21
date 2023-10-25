package gitlet;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    //system-dependent file separator character
    public static final String FILESEPARATOR = System.getProperty("file.separator");



    public static boolean isEmptyArgs(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter a command.");
            return true;
        }
        return false;
    }

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
            System.exit(-1);
        }
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(-1);
        }
        File file = join(CWD, args[1]);
        if(!file.exists()){
            System.out.println("File does not exist");
            System.exit(-1);
        }
        //TODO generate blob
        //TODO generate blob name :hash into index file


    }

    public static void gitInitCommand(String[] args) {
        if (!isValidateLength(args, 1)) {
            System.exit(-1);
        }
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        //Create .git folder
        GITLET_DIR.mkdir();
        //Create .git/objects folder which contains commits and blobs
        File objects = join(GITLET_DIR, "objects");
        objects.mkdir();
        //Create .git/refs/heads/ folder which contains branch file
        File refs = join(GITLET_DIR, "refs", "heads");
        refs.mkdirs();

        //Create HEAD and index file
        File HEAD = join(GITLET_DIR, "HEAD");
        //a list that contains every file that Git has been told to keep track of.
        File index = join(GITLET_DIR, "index");
        try {
            HEAD.createNewFile();
            index.createNewFile();
            gitInitCommandHelper(HEAD);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void gitInitCommandHelper(File HEAD) {
        //create commit for init command
        Commit initCommit = new Commit("initial commit");
        ZonedDateTime timeWithoutFormat = Instant.ofEpochSecond(0L).atZone(Commit.ZONEID);
        String timeWithFormat = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss yyyy Z").format(timeWithoutFormat);
        initCommit.setCreatTime(timeWithFormat);
        // set HEAD point to master branch
        Utils.writeContents(HEAD, "refs/heads/master");
        File masterFile = join(GITLET_DIR, "refs", "heads", "master");
        String blob = sha1(initCommit.toString());
        String blobFolderName = blob.substring(0, 2);
        //create blob
        File blobFolder = join(GITLET_DIR, "objects", blobFolderName);
        blobFolder.mkdir();
        File blobFile = join(blobFolder, blob.substring(2));
        try {
            //write the hash of this commit into this \refs\heads\master file
            masterFile.createNewFile();
            Utils.writeContents(masterFile, blob);
            blobFile.createNewFile();
            //save the initCommit object to this blob file in objects folder
            Utils.writeObject(blobFile, initCommit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

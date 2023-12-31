package gitlet;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.gitInitCommand(args);
                break;
            case "add":
                Repository.gitAddCommand(args);
                break;
            case "commit":
                Repository.gitCommitCommand(args);
                break;

            default:
                System.out.println("No command with that name exists.");
                break;
        }

    }
}

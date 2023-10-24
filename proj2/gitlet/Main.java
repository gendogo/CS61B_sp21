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
        if (Repository.isEmptyArgs(args)) {
            System.exit(-1);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.gitInitCommand(args);
                break;
            case "add":
                Repository.gitAddCommand(args);
                // TODO: handle the `add [filename]` command
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }

    }
}

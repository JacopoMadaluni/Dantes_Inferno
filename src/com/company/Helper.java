package com.company;
/**
 * This is the helper class of the Divina Commedia simulator application.
 * The Helper provides a manual of every command.
 * When the user types a keyword that is not recognised as such,
 * the helper tries to guess the right keyword the user was locking for in
 * the first place.
 */
public class Helper{
    Game game;
    private static Helper singleton;
    private Helper (Game game){
        this.game = game;
    }

    public static Helper getHelper(Game game){
        if (singleton == null){
            singleton = new Helper(game);
        }
        return singleton;

    }

    /**
     * Print out some help information.
     * The user can either type 'help' for general information or
     * type 'help <command>' for additional syntax and info about the command.
     * @param word2 The command that the user wants to know more about.
     */
    public void help(String word2){
        if (word2 == null){
            game.print("If you don't know how to use a command, type help <command> ");
            game.print("Since the game is in Alpha version, please use lower case to write, otherwise your commands");
            game.print("might not be recognised.");
            game.print("Your command words are:");
            game.print(CommandWords.getUserCommands());
        }else{
            switch (word2){
                case "go":
                    game.print("Type 'go <keyword>' to move, type 'exits' to see the keywords you can use.");
                    break;
                case "take":
                    game.print("Type 'take <item>' to take the an item.");
                    game.print("The item must be in the place you are currently in, in order to be taken.");
                    game.print("If you already have stuff in your inventory, it may happen that");
                    game.print("you will not be able to take the item, if the sum of your stuff is");
                    game.print("greater than 10.");
                    break;
                case "drop":
                    game.print("Type 'drop <item' to drop the item on the ground.");
                    game.print("you must have the item in your inventory in order to drop it.");
                    game.print("The item will remain where you dropped it, so don't worry, you will");
                    game.print("be able to take it back at any moment.");
                    break;
                case "use":
                    game.print("Type 'use <item>' or 'use <item> on <creature>' to produce the effect.");
                    game.print("Be carefull to not hurt anyone hahahaha.");
                    game.print("Note that some combinations may not produce any effect.");
                    break;
                case "give":
                    game.print("Type 'give <item> to <creature>' to give an item to someone.");
                    game.print("Note that some combinations may not produce any effect.");
                    break;
                case "talk":
                    game.print("Type 'talk to <creature>' in order to start a conversation.");
                    game.print("Note that there is no 'reply' command, just talk again to reply.");
                    break;
                case "examine":
                    game.print("Type 'examine <item>' in order to look carefully.");
                    game.print("Since this is the Alpha of the game, creatures cannot be examined");
                    break;
                default:
                    game.print("There are no additional informations about this command");
                    break;

            }
        }
    }

    /**
     * This method is supposed to try to guess what keyword the user is looking for.
     * Gets called when the word (which is referred to a creature) is not a keyword
     * that the game can use.
     * @param currentRoom The room the player is in.
     * @param userWord The (wrong) keyword the user typed.
     * @returns A help message to print.
     */
    public String helpUser(String currentRoom, String userWord){
        String word = userWord.toLowerCase();
        String room = currentRoom.toLowerCase();
        String help = "Did you mean ";
        switch (room){
            case "river":
                switch (word){
                    case "ferryman":
                        help = help + "charon?\n";
                        return help;
                    default:
                        return "";
                }
            case "nastynarrow":
                switch (word){
                    case "dog":
                        help = help + "cerberus?\n";
                        return help;
                    case "giant":
                        help = help + "cerberus?\n";
                        return help;
                    case "old":
                        help = help + "the alchemist.\n";
                        return help;
                    case "man":
                        help = help + "the alchemist.\n";
                        return help;
                    default:
                        return "";
                }
            case "swamp":
                switch (word){
                    case "man":
                        help = help + "the butcher?\n";
                        return help;
                    case "tormented":
                        help = help + "the butcher?\n";
                        return help;
                    default:
                        return "";
                }
            default:
                break;
        }
        return "";
    }

    /**
     * This method is supposed to try to guess what keyword the user is looking for.
     * Gets called when the word (which is referred to an Item) is not a keyword
     * that the game can use.
     * @param item The (wrong) keyword the user typed.
     * @returns A help message to print.
     */
    public String helpUserItem(String item){
        String help = "Try: ";
        switch (item){
            case "coin":
                return help + "goldencoin.\n";
            case "golden":
                return help + "goldencoin.\n";
            case "plate":
                return help + "ironplate.\n";
            case "iron":
                return help + "ironplate.\n";
            case "rock":
                return help + "rocks.\n";
            case "dead":
                return help + "corpse.\n";
            case "body":
                return help + "corpse.\n";
            default:
                return "";

        }
    }

    /**
     * Prints the wrong syntax error message.
     */
    public void printWrongSyntax(String command){
        game.print("Wrong syntax for the " + command + " command.\n" +
                "Use 'help <command> for additional help.\n");
    }
}

package com.company;
/**
 * This is the helper class of the Divina Commedia simulator application.
 * The Helper provides a manual of every command.
 * When the user types a keyword that is not recognised as such,
 * the helper tries to guess the right keyword the user was locking for in
 * the first place.
 */
public class Helper{
    public Helper (){
    }

    /**
     * Print out some help information.
     * The user can either type 'help' for general information or
     * type 'help <command>' for additional syntax and info about the command.
     * @param word2 The command that the user wants to know more about.
     */
    public void help(String word2){
        if (word2 == null){
            System.out.println("If you don't know how to use a command, type help <command> ");
            System.out.println("Since the game is in Alpha version, please use lower case to write, otherwise your commands");
            System.out.println("might not be recognised.");
            System.out.println("Your command words are:");
            CommandWords.printUserCommands();
        }else{
            switch (word2){
                case "go":
                    System.out.println("Type 'go <keyword>' to move, type 'exits' to see the keywords you can use.");
                    break;
                case "take":
                    System.out.println("Type 'take <item>' to take the an item.");
                    System.out.println("The item must be in the place you are currently in, in order to be taken.");
                    System.out.println("If you already have stuff in your inventory, it may happen that");
                    System.out.println("you will not be able to take the item, if the sum of your stuff is");
                    System.out.println("greater than 10.");
                    break;
                case "drop":
                    System.out.println("Type 'drop <item' to drop the item on the ground.");
                    System.out.println("you must have the item in your inventory in order to drop it.");
                    System.out.println("The item will remain where you dropped it, so don't worry, you will");
                    System.out.println("be able to take it back at any moment.");
                    break;
                case "use":
                    System.out.println("Type 'use <item>' or 'use <item> on <creature>' to produce the effect.");
                    System.out.println("Be carefull to not hurt anyone hahahaha.");
                    System.out.println("Note that some combinations may not produce any effect.");
                    break;
                case "give":
                    System.out.println("Type 'give <item> to <creature>' to give an item to someone.");
                    System.out.println("Note that some combinations may not produce any effect.");
                    break;
                case "talk":
                    System.out.println("Type 'talk to <creature>' in order to start a conversation.");
                    System.out.println("Note that there is no 'reply' command, just talk again to reply.");
                    break;
                case "examine":
                    System.out.println("Type 'examine <item>' in order to look carefully.");
                    System.out.println("Since this is the Alpha of the game, creatures cannot be examined");
                    break;
                default:
                    System.out.println("There are no additional informations about this command");
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
        System.out.println("Wrong syntax for the " + command + " command.\n" +
                "Use 'help <command> for additional help.\n");
    }
}

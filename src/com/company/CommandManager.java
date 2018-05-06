package com.company;
/**
 * The CommandManager is a class of the Divina Commedia simulator Application.
 * The task of the command manager is to check the correct syntax of a command.
 * This class has to make sure that no "null" value is gonna be used by the game
 * class as input.
 *
 * @author (Jacopo Madaluni)
 * @version (6.12.2017)
 */
public class CommandManager
{
    private Helper helper;
    private Game game;
    private boolean wantToFinish;

    /**
     * Creates a new command manager
     * @param game The game will input itself.
     */
    public CommandManager(Game game){
        helper = new Helper(game);
        wantToFinish = false;
        this.game = game;
    }

    /**
     * Sets the boolean that closes the game.
     * @param b The new boolean: true = quit the game.
     */
    public void setWantToFinish(boolean b){
        wantToFinish = b;
    }

    /**
     * Returns the number of not null words.
     */
    private int numberOfWords(String[] words){
        int numberOfWords = 0;
        for (int index = 0; index < words.length; index++){
            if (words[index] != null){
                numberOfWords++;
            }
        }
        return numberOfWords;
    }

    /**
     * The main method of processing the command.
     * @return true if the game must quit.
     */
    public boolean process(String commandWord,String word2, String word3, String word4){
        String[] words = new String[4];
        words[0] = commandWord;
        words[1] = word2;
        words[2] = word3;
        words[3] = word4;
        int numberOfWords = numberOfWords(words);

        if (commandWord.equals("help")){
            helper.help(word2);
        }else if(commandWord.equals("see")){
            game.see();
        }else if (commandWord.equals("look")){
            processLookCommand(word2);
        }else if(commandWord.equals("go")){
            processGoCommand(word2, word3, word4);
        }else if (commandWord.equals("back")){
            game.goBack();
        }else if (commandWord.equals("take")){
            processTakeCommand(word2);
        }else if (commandWord.equals("examine")){
            processExamineCommand(word2,word3);
        }else if (commandWord.equals("inventory") || commandWord.equals("inv")){
            game.printInventory();
        }else if (commandWord.equals("drop")){
            if (word2 != null){
                game.drop(word2);
            }else{
                game.print("Drop what?\n");
            }
        }else if (commandWord.equals("talk")){
            processTalkCommand(word2, word3,word4);
        }else if (commandWord.equals("use")){
            processUseCommand(word2,word3,word4);
        }else if (commandWord.equals("give")){
            processGiveCommand(word2,word3,word4);
        }else if (commandWord.equals("quit")){
            if (numberOfWords == 1){
                wantToFinish = true;
                return wantToFinish;
            }
        }else if (commandWord.equals("textSpeed")){
            if (word2 != null){
                game.setTextSpeed(word2);
            }else{
                System.out.print("Type textSpeed <value> please.\n");
            }
        }else if (commandWord.equals("exit") || commandWord.equals("exits")){
            game.printExits();
        }
        return wantToFinish;
    }

    /**
     * Processes the 'examine' command.
     */
    private void processExamineCommand(String word2, String word3){
        if (word2 != null){
            if (word2.equals("the")){
                game.examine(word3);
            }else{
                game.examine(word2);
            }
        }else{
            game.print("Examine what?\n");
        }
    }

    /**
     * Processes the 'take' command.
     */
    private void processTakeCommand(String word2){
        if (word2 == null){
            game.print("Take what?\n");
            return;
        }
        game.take(word2);
    }

    /**
     * Processes the 'go' command.
     */
    private void processGoCommand(String word2, String word3, String word4){
        if (word2 != null){
            if (word2.equals("to")){
                if (word3.equals("the")){
                    game.goRoom(word4);
                }else{
                    game.goRoom(word3);
                }
            }else{
                game.goRoom(word2);
            }
        }else{
            game.print("Go where?\n");
        }
    }

    /**
     * Processes the 'give' command.
     */
    private void processGiveCommand(String word2, String word3, String word4){
        if (word2 == null){
            game.print("Give what to whom?\n");
            return;
        }

        if (word3 != null && word4 != null){
            if (word3.equals("to")){
                game.give(word2,word4);
            }else{
                helper.printWrongSyntax("give");
            }
        }else{
            helper.printWrongSyntax("give");
        }
    }

    /**
     * Processes the 'look' command.
     */
    private void processLookCommand(String word2){
        if (word2 != null){
            if (word2.equals("around")){
                game.see();
            }else{
                game.print("You can only look around, use examine to look at things.\n");
            }
        }else{
            game.see();
        }
    }

    /**
     * Processes the talk command.
     */
    private void processTalkCommand(String word2, String word3, String word4){
        if (word2 != null){
            if (word2.equals("to")){
                if (word3 != null){
                    if (word3.equals("the")){
                        if (word4 != null){
                            game.talkTo(word4);
                        }else{
                            game.print("Talk to whom?\n");
                        }
                    }else{
                        game.talkTo(word3);
                    }
                }else{
                    game.print("Talk to whom?\n");
                }
            }else{
                game.talkTo(word2);
            }
        }else{
            game.print("Talk to whom?\n");
        }
    }

    /**
     * Processes the use command.
     * The 'use' command can either be: 'use <item> on <target>' or
     * 'use <item>'.
     */
    private void processUseCommand(String word2, String word3, String word4){
        if (word3 != null){
            if (word3.equals("on")){
                game.use(word2, word4);
            }else{
                helper.printWrongSyntax("use");
            }
        }else{
            if (word2 != null){
                game.useItem(word2);
            }else{
                game.print("Use what?\n");
            }
        }
    }
}


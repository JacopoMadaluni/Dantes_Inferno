package com.company;
import com.company.commands.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is part of the Divina Commedia simulator application.
 *
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a four word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author  Michael Kölling, David J. Barnes and Jacopo Madaluni.
 * @version 2016.02.29
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    private Game game;
    private String commandsHistory;

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser(Game game)
    {
        commands = new CommandWords(game);
        reader = new Scanner(System.in);
        this.game = game;
        commandsHistory = "";
    }

    public Command parseCommand(String string){
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;
        Scanner tokenizer = new Scanner(string);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();   // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();// get second word
                if (tokenizer.hasNext()){
                    word3 = tokenizer.next().toLowerCase();    // get third word
                    if (tokenizer.hasNext()){
                        word4 = tokenizer.next().toLowerCase();   //get fourth word
                        // note: it ignores all the words after the fourth word
                    }
                }
            }
        }
        if (word1 == null){
            word1 = "";   // if the player enters a null command nothing happens.
        }
        Command command  = getCorrectCommand(word1, word2, word3, word4);
        if (command.mustBeSaved()){
            commandsHistory = commandsHistory + string + ",";
        }
        return command;

    }




    public List<Command> loadSave(String commands){
        List<Command> toLoad = new ArrayList<>();
        if (commands == null){
            return toLoad;
        }
        Scanner loader = new Scanner(commands);
        loader.useDelimiter(",");
        while (loader.hasNext()){
            String inputLine;   // will hold the full input line
            String word1 = null;
            String word2 = null;
            String word3 = null;
            String word4 = null;
            inputLine = loader.next();
            Scanner tokenizer = new Scanner(inputLine);
            if(tokenizer.hasNext()) {
                word1 = tokenizer.next().toLowerCase();   // get first word
                if(tokenizer.hasNext()) {
                    word2 = tokenizer.next().toLowerCase();// get second word
                    if (tokenizer.hasNext()){
                        word3 = tokenizer.next().toLowerCase();    // get third word
                        if (tokenizer.hasNext()){
                            word4 = tokenizer.next().toLowerCase();   //get fourth word
                            // note: it ignores all the words after the fourth word
                        }
                    }
                }
            }
            if (word1 == null){
                word1 = "";   // if the player enters a null command nothing happens.
            }
            toLoad.add(getCorrectCommand(word1, word2, word3, word4));
        }
        return toLoad;
    }


    /**
     * @return The next command from the user.
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to four words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();   // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();// get second word
                if (tokenizer.hasNext()){
                    word3 = tokenizer.next().toLowerCase();    // get third word
                    if (tokenizer.hasNext()){
                        word4 = tokenizer.next().toLowerCase();   //get fourth word
                        // note: it ignores all the words after the fourth word
                    }
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if (word1 == null){
            word1 = "";   // if the player enters a null command nothing happens.
        }
        Command command = getCorrectCommand(word1, word2, word3, word4);
        if (command.mustBeSaved()){
            commandsHistory = commandsHistory + inputLine + ",";
        }
        return command;
        /*if(commands.isCommand(word1)) {
            Command testing = getCorrectCommand(word1,word2,word3, word4);
            if (testing != null){
                return testing;
            }
            return new Command(word1, word2, word3, word4);
        }
        else {
            return new Command(null, word2, word3, word4);
        }*/
    }

    public String getCommandsHistory(){
        return commandsHistory;
    }

    public void clearHistory(){
        commandsHistory = "";
    }

    private Command getCorrectCommand(String keyWord, String word2, String word3, String word4 ){
        switch(keyWord){
            case "":
                return new VoidCommand(game);
            case "help":
                return new Help(keyWord, word2, word3, word4, game);
            case "see":
                return new See(keyWord, word2, word3, word4, game);
            case "look":
                return new See(keyWord, word2, word3, word4, game);
            case "go":
                return new GoCommand(keyWord, word2, word3, word4, game);
            case "back":
                return new BackCommand(game);
            case "take":
                return new Take(keyWord, word2, word3, word4, game);
            case "examine":
                return new Examine(keyWord, word2, word3, word4, game);
            case "inventory":
                return new InventoryCommand(game);
            case "inv":
                return new InventoryCommand(game);
            case "drop":
                return new Drop(keyWord, word2, word3, word4, game);
            case "talk":
                return new TalkCommand(keyWord, word2, word3, word4, game);
            case "use":
                return new UseCommand(keyWord, word2, word3, word4, game);
            case "give":
                return new GiveCommand(keyWord, word2, word3, word4, game);
            case "save":
                return new SaveCommand(game);
            case "quit":
                return new QuitCommand(keyWord, word2, word3, word4, game);
            case "textspeed":
                return new TextSpeedCommand(keyWord, word2, game);
            case "exit":
                return new GetExitsCommand(game);
            case "exits":
                return new GetExitsCommand(game);
            default:
                return new UnknownCommand(keyWord, word2, word3, word4, game);
        }
    }


}


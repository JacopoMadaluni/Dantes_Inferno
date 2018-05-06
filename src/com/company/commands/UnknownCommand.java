package com.company.commands;

import com.company.Game;

public class UnknownCommand extends Command {
    public UnknownCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        game.print("I don't know what you mean..");
        return false;
    }

    @Override
    public boolean mustBeSaved(){
        return false;
    }
}

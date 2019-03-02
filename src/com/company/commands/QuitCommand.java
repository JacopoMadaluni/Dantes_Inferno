package com.company.commands;

import com.company.Game;

public class QuitCommand extends Command{
    public QuitCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        return true;
    }

    @Override
    public boolean mustBeSaved(){
        return false;
    }
}

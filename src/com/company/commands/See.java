package com.company.commands;

import com.company.Game;

public class See extends Command {

    public See(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        game.see();
        return false;
    }

    @Override
    public boolean mustBeSaved(){
        return false;
    }
}

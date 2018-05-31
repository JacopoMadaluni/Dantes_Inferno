package com.company.commands;

import com.company.Game;

public class TextSpeedCommand extends Command {
    public TextSpeedCommand(String firstWord, String secondWord, Game game){
        super(firstWord, secondWord, game);
    }

    public boolean mustBeSaved(){
        return false;
    }

    @Override
    public boolean execute(){
        game.setTextSpeed(secondWord);
        return false;
    }
}

package com.company.commands;

import com.company.Game;

public class Examine extends Command {

    public Examine(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        if (secondWord != null){
            game.examine(secondWord);
        }else{
            game.print("Examine what?");
        }
        return false;
    }

    @Override
    public boolean mustBeSaved(){
        return false;
    }
}

package com.company.commands;

import com.company.Game;

public class Drop extends Command {
    public Drop(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        if (secondWord != null){
            game.drop(secondWord);
        }else{
            game.print("Drop what?");
        }
        return false;
    }

    @Override
    public boolean mustBeSaved(){
        return true;
    }
}

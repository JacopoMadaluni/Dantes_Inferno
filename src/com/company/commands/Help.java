package com.company.commands;

import com.company.Game;
import com.company.Helper;

public class Help extends Command {

    private Helper helper;
    public Help(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
        helper = Helper.getHelper(game);
    }

    @Override
    public boolean execute(){
        helper.help(secondWord);
        return false;
    }
    @Override
    public boolean mustBeSaved(){
        return false;
    }
}

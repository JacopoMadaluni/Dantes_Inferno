package com.company.commands;

import com.company.Game;
import com.company.Helper;

public class Help extends Command {
    public Help(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        Helper helper = new Helper();
        helper.help(secondWord);
        return false;
    }
}

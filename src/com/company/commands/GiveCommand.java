package com.company.commands;

import com.company.Game;
import com.company.Helper;

public class GiveCommand extends Command {

    private Helper helper;

    public GiveCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
        helper = Helper.getHelper(game);
    }

    public boolean mustBeSaved(){
        return true;
    }

    @Override
    public boolean execute(){
        if (secondWord == null){
            game.print("Give what to whom?\n");
            return false;
        }

        if (thirdWord != null && fourthWord != null){
            if (thirdWord.equals("to")){
                game.give(secondWord,fourthWord);
            }else{
                helper.printWrongSyntax("give");
            }
        }else{
            helper.printWrongSyntax("give");
        }
        return false;
    }
}

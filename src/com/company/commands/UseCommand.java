package com.company.commands;

import com.company.Game;
import com.company.Helper;

public class UseCommand extends Command {

    private Helper helper;

    public UseCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
        helper = Helper.getHelper(game);
    }

    @Override
    public boolean mustBeSaved(){
        return true;
    }

    @Override
    public boolean execute(){

        if (thirdWord != null){
            if (thirdWord.equals("on") && fourthWord != null){
                game.use(secondWord, fourthWord);
            }else{
                helper.printWrongSyntax("use");
            }
        }else{
            if (secondWord != null){
                game.useItem(secondWord);
            }else{
                game.print("Use what?\n");
            }
        }
        return false;
    }
}

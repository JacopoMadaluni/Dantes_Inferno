package com.company.commands;

import com.company.Game;

public class GoCommand extends Command {

    public GoCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        try {
            if (secondWord.equals("back")) {
                game.goBack();
            } else {
                game.goRoom(secondWord);
            }
        }catch(NullPointerException ex){
            game.print("Go where?");
        }finally {
            return false;
        }
    }

}

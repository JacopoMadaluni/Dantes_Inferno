package com.company.commands;

import com.company.Game;

public class TalkCommand extends Command {

    public TalkCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, Game game){
        super(firstWord, secondWord, thirdWord, fourthWord, game);
    }

    @Override
    public boolean execute(){
        if (secondWord != null){
            if (secondWord.equals("to")){
                if (thirdWord != null){
                    if (thirdWord.equals("the")){
                        if (fourthWord != null){
                            game.talkTo(fourthWord);
                        }else{
                            game.print("Talk to whom?\n");
                        }
                    }else{
                        game.talkTo(thirdWord);
                    }
                }else{
                    game.print("Talk to whom?\n");
                }
            }else{
                game.talkTo(secondWord);
            }
        }else{
            game.print("Talk to whom?\n");
        }

        return false;
    }

    @Override
    public boolean mustBeSaved(){
        return true;
    }
}


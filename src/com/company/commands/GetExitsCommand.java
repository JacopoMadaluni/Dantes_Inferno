package com.company.commands;

import com.company.Game;

public class GetExitsCommand extends Command {

    public GetExitsCommand(Game game){
        super(game);
    }

    public boolean mustBeSaved(){
        return false;
    }

    @Override
    public boolean execute(){
        game.printExits();
        return false;
    }
}

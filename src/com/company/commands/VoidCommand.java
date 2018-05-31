package com.company.commands;

import com.company.Game;

public class VoidCommand extends Command {

    public VoidCommand(Game game){
        super(game);
    }

    public boolean mustBeSaved(){
        return false;
    }

    @Override
    public boolean execute(){
        return false;
    }
}

package com.company.commands;

import com.company.Game;

public class SaveCommand extends Command{
    public SaveCommand(Game game){
        super(game);
    }

    @Override
    public boolean execute(){
        game.save();
        return false;
    }
}

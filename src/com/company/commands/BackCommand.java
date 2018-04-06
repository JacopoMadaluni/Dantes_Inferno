package com.company.commands;

import com.company.Game;

public class BackCommand extends Command{
    public BackCommand(Game game){
        super(game);
    }

    @Override
    public boolean execute(){
        game.goBack();
        return false;
    }
}

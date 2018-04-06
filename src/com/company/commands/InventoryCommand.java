package com.company.commands;

import com.company.Game;

public class InventoryCommand extends Command {
    public InventoryCommand(Game game){
        super(game);
    }

    @Override
    public boolean execute(){
        game.printInventory();
        return false;
    }
}

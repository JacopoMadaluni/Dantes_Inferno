package com.company.commands;

import com.company.Game;
import com.company.Gui.Printer;

public class VoidCommand extends Command {
    Printer printer;

    public VoidCommand(Game game){
        super(game);
        printer = Printer.getPrinter();
    }

    public boolean mustBeSaved(){
        return false;
    }

    @Override
    public boolean execute(){
        if (printer.isPrinting()){
            game.setTextSpeed(1);
        }
        return false;
    }
}

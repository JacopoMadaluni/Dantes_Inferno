package com.company;

/**
 * The status manager is a class of the Divina Commedia simulator application.
 * The StatusManager updates the status of every dynamic creature in the game.
 * In this class are stored all the automata of the dynamic creatures.
 * Every dynamic creature has her own automaton.
 *
 * @author (Jacopo Madaluni)
 * @version (6.12.2017)
 */
public class StatusManager
{
    private Game game;

    /**
     * Creates a new status manager.
     * @param game The current Game.
     */
    public StatusManager(Game game){
        this.game = game;
    }

    /**
     * @return The status of a creature.
     * @param creature The creature.
     */
    public int getStatus(Creature creature){
        return creature.getStatus();
    }

    /**
     * Method that changes the status of a creature.
     * @param creature The creature to change.
     * @param newStatus The new status of the creature.
     */
    public void setStatus(Creature creature, int newStatus){
        creature.setStatus(newStatus);
    }

    /**
     * Method that gets called every time the player talks to a creature.
     * The method updates the right creature.
     * @param creature The creature the player talked with.
     */
    public void dialogueUpdate(Creature creature){
        if (creature.getName().equals("stranger")){
            processStrangerStatus(creature);
        }else if(creature.getName().equals("guardian")){
            processGuardianStatus(creature);

        }else if (creature.getName().equals("alchemist")){
            processAlchemistStatus(creature);
        }else if (creature.getName().equals("charon")){
            processCharonStatus(creature);
        }else if (creature.getName().equals("butcher")){
            processButcherStatus(creature);
        }
    }

    /**
     * Automaton of the creature: butcher.
     * Updates the status of the butcher.
     */
    private void processButcherStatus(Creature butcher){
        int status = getStatus(butcher);
        switch (status){
            case 1:
                butcher.advanceStatus();
                break;
            case 2:
                butcher.advanceStatus();
                break;
            case 3:
                butcher.advanceStatus();
                break;
            case 4:
                butcher.setStatus(6);
                break;
            case 5:
                butcher.setStatus(10);
                break;
            default:
                break;
        }
    }

    /**
     * Automaton of the creature: stranger.
     * Updates the status of the stranger.
     */
    private void processStrangerStatus(Creature stranger){
        int status = getStatus(stranger);
        switch (status){
            case 1:
                stranger.advanceStatus();
                break;
            case 2:
                stranger.advanceStatus();
                break;
            case 3:
                stranger.setStatus(5);
                break;
            case 5:
                stranger.setStatus(10);
            default:
                break;

        }
    }

    /**
     * Automaton of the creature: guardian.
     * Updates the status of the guardian.
     */
    private void processGuardianStatus(Creature guardian){
        int status = getStatus(guardian);
        switch (status){
            case 1:
                if(game.playerHasVirgil()){
                    guardian.setStatus(5);
                }else{
                    guardian.advanceStatus();
                }
                break;
            case 2:
                if(game.playerHasVirgil()){
                    guardian.setStatus(5);
                }else{
                    guardian.advanceStatus();
                }
                break;
            case 3:
                if(game.playerHasVirgil()){
                    guardian.setStatus(5);
                }else{
                    guardian.advanceStatus();
                }
                break;
            case 4:
                if (game.playerHasVirgil()){
                    guardian.advanceStatus();
                }
                break;
            case 5:
                guardian.setStatus(10);
                break;
            default:
                break;
        }
    }

    /**
     * Automaton of the creature: alchemist.
     * Updates the status of the alchemist.
     */
    private void processAlchemistStatus(Creature alchemist){
        int status = getStatus(alchemist);
        int charonStatus = getStatus(game.getCreature("charon"));
        switch (status){
            case 1:
                alchemist.advanceStatus();
                break;
            case 2:
                if (charonStatus != 1){
                    alchemist.setStatus(4);
                    break;
                }else{
                    alchemist.advanceStatus();
                    break;
                }
            case 3:
                if (charonStatus != 1){
                    alchemist.advanceStatus();
                }
                break;
            case 4:
                alchemist.setStatus(6);
                break;
            case 5:
                alchemist.setStatus(10);
                break;
            default:
                break;
        }
    }

    /**
     * Automaton of the creature: charon.
     * Updates the status of charon.
     */
    private void processCharonStatus(Creature charon){
        int status = getStatus(charon);
        switch (status){
            case 1:
                charon.advanceStatus();
                break;
            case 2:
                charon.advanceStatus();
                break;
            default:
                break;
        }
    }
    private void processCiaccoStatus(Creature ciacco){
        int status = getStatus(ciacco);
        switch (status){
            case 1:
                ciacco.advanceStatus();
                break;
            case 2:
                ciacco.advanceStatus();
                break;
            case 3:
                ciacco.advanceStatus();
                break;
            default:
                break;
        }

    }

}

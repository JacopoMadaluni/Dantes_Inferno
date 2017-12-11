package com.company;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * This is the Virgilius class, Virgil is the special character of the game.
 * Taken from the Divina Commedia, Virgil is the guide of the player.
 * Virgil has dialogues depending on the room he's currently in.
 * The dialogues can eventually provide a more involving description of the place
 * the player is visiting.
 * Virgil must be revealed by talking to the ghost in the clear near the starting room.
 */
public class Virgilius extends Creature{
    private HashMap<String, ArrayList<String>> dialogues;
    private int dialogueIndex;

    private String currentRoom;

    /**
     * Creates a new Virgilius.
     * @param name The name of Virgilius, should be set to virgil.
     * @presentation The presentation of Virgil, should be set to "Virgil is standing silent near you".
     */
    public Virgilius(String name, String presentation){
        super(name, presentation);
        currentRoom = "misteriousClear";
        dialogues = new HashMap<>();

        dialogues.put("darkWood", new ArrayList<>());
        dialogues.put("misteriousClear", new ArrayList<>());
        dialogues.put("hill", new ArrayList<>());
        dialogues.put("infernosEntrance", new ArrayList<>());
        dialogues.put("limbo", new ArrayList<>());
        dialogues.put("river", new ArrayList<>());
        dialogues.put("infernalForest", new ArrayList<>());
        dialogues.put("swamp", new ArrayList<>());
        dialogues.put("nastyNarrow", new ArrayList<>());
        dialogues.put("luciferPlatform", new ArrayList<>());

        addDialogue("darkWood", "This is the dark wood, an ancient place outside the doors of the hell, there is a big hill\n" +
                " not far away, you should be able to see it.\n");

        addDialogue("misteriousClear", "God himself gave me this mission, to be your guide through this passage into the inferno.\n");
        addDialogue("misteriousClear", "I will guide you into your journey.\n");

        addDialogue("hill", "The entrance of the inferno is not far away.\n");

        addDialogue("infernosEntrance", "Virgil is standing silent near you.\n");

        addDialogue("limbo", "This is the limbo, where I belong.\n");
        addDialogue("limbo", "Here all the atheists are spending their eternity.\n");
        addDialogue("limbo", "The people that stay here are not damned, since they committed no crimes, but we are not\n" +
                "accepted in paradise, since we didn't believe in life.\n");

        addDialogue("river", "The river is called Acheron, separetes this area from the rest of the Inferno.\n");
        addDialogue("river", "The old man that you see is Charon, the ferryman of souls.\n" +
                "He brings the souls to the other part of the river.\n" +
                "With some luck, he can bring us to Luciferus.\n");

        addDialogue("swamp", "Virgil is standing silent near you.\n");
        addDialogue("infernalForest", "Virgil is standing silent near you.\n");

        addDialogue("nastyNarrow", "Virgil is standing silent near you.\n");

        addDialogue("luciferPlatform", "This place is called Cocitus, the frozen river where Lucifer, the damned\n" +
                "of all damned, has been exiled by God himself.\n");
        addDialogue("luciferPlatform", "Face him..\n");
    }

    /**
     * Reveals that the ghost is the special character.
     * @return The reveal message to be printed.
     */
    public String reveal(){
        setName("virgil");
        setPresentation("Virgilius is standing silent near you");
        return ("Ghost: Hello Dante, I am Virgil, you find yourself at the doors of the Inferno.\n" +
                "If you want to escape from here you will have to find Lucifer, the pagan God that rules this realm.\n" +
                "Don't worry I have been sent here from God himself to help you, I will be your guide.\n");
    }

    /**
     * Sets the name of the room Virgil is currently in.
     * @param room The name keyword of the room.
     */
    public void setCurrentRoom(String room){
        currentRoom = room;
    }

    /**
     * Sets the dialogueIndex to 0
     */
    public void resetIndex(){
        dialogueIndex = 0;
    }

    /**
     * @return The right dialogue to print.
     */
    public String getDialogue(String currentRoom){
        return dialogues.get(currentRoom).get(dialogueIndex);
    }

    @Override
    /**
     * Overrided method: the dialogue indexing of Virgil works slightly differently.
     * @return the maximum index to be compared with the current dialogue index.
     */
    public int getMaxDialogueIndex(){
        return this.dialogues.get(currentRoom).size();
    }

    @Override
    /**
     * Overrided method: the dialogue indexing of Virgil works slightly differently.
     * Increases the dialogue index if possible.
     */
    public void increaseDialogueIndex(){
        int maxDialogueIndex = getMaxDialogueIndex();
        if (!((dialogueIndex + 1) == maxDialogueIndex)){
            dialogueIndex += 1;

        }else{
            dialogueIndex = 0;
        }
    }

    /**
     * Adds a dialogue to Virgil.
     * @param room The room where the dialogue can be get.
     * @param dialogue The string to be printed.
     */
    private void addDialogue(String room, String dialogue){
        dialogues.get(room).add(dialogue);
    }
}


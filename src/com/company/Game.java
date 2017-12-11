package com.company;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;
import java.lang.Math;
/**
 *  This class is the main class of the Divina Commedia simulator application.
 *  The game is inspired to a famus Italian poem by Dante Alighieri: Divinia Commedia.
 *  The setting and the characters are taken from the poem.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method or call the Main class.
 *
 *  The game class : - initializes and creates all the items, characters and rooms of the game.
 *                   - performs actions and events.
 *                   - is supported by the managers that tell the game when to perform an event or action.
 *
 * @author  Michael Kölling, David J. Barnes and Jacopo Madaluni.
 * @version 2016.02.29
 */

public class Game
{
    private Helper helper;
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Player player;
    private HashSet<Creature> creatures;
    private HashSet<Creature> movingCreatures;
    private HashSet<Item> items;
    private HashSet<Room> rooms;
    private HashSet<Room> infernalRooms;
    private CommandManager commandManager;
    private EventManager eventManager;
    private Virgilius virgil;
    private boolean finished;
    private static int textSpeed;
    private Bucket bucket;


    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        this.finished = false;
        this.textSpeed = 25;
        helper = new Helper();
        creatures = new HashSet<>();
        movingCreatures = new HashSet<>();
        items = new HashSet<>();
        rooms = new HashSet<>();
        infernalRooms = new HashSet<>();
        parser = new Parser();
        player = new Player();
        commandManager = new CommandManager(this);
        eventManager = new EventManager(this);
        initializeGame();
        previousRoom = currentRoom;
    }

    /**
     * Set the delay of the dynamic printing.
     * Input must be between 0 and 100 (ms).
     * @param newSpeed The delay between characters.
     */
    public void setTextSpeed(String newSpeed){
        if (isNumeric(newSpeed)){
            int speed = Integer.parseInt(newSpeed);
            if (speed >= 0 && speed < 100){
                textSpeed = speed;
                System.out.println("Text speed updated");
            }else{
                System.out.println("The speed must be between 0 and 100");
            }
        }else{
            System.out.println("Invalid speed");
        }
    }

    private boolean isNumeric(String string){
        return string != null && string.matches("[-+]?\\d*\\.?\\d+");
    }

    /** Getters (accessors) */

    /**
     * @return true if the player has the special character with him.
     */
    public boolean playerHasVirgil(){
        return player.hasVirgil();
    }

    /**
     * @return the bucket which is a special item.
     */
    //public Bucket getBucket(){
    //    return bucket;
    //  }

    /**
     * Given the name of the creature returns the creature.
     * @param creature The name of the creature.
     * @return The corrisponding creature.
     */
    public Creature getCreature(String creature){
        for (Creature c : creatures){
            if (c.getName().equals(creature)){
                return c;
            }
        }
        return null;
    }

    /**
     *  Returns the room the player is in.
     *  @return The current room.
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    /**
     * Given the name of the room, returns the room.
     * @param name The name of the room.
     * @return The corresponding room.
     */
    public Room getRoom(String name){
        for (Room room : rooms){
            if (room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }

    /**
     * Given the name of the item, returns the item.
     * @param name The name of the item.
     * @return The corresponding item.
     */
    public Item getItem(String name){
        for (Item i : items){
            if (i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }

    /** Setters */

    /**
     * Changes the presentation of a creature. Called by the event manager when necessary.
     * @param name The name of the creature.
     * @param newPresentation The new presentation.
     */
    public void setCreaturePresentation(String name, String newPresentation){
        Creature creature = getCreature(name);
        creature.setPresentation(newPresentation);
    }

    /** Core methods */

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play()
    {
        printStart();
        while (! this.finished) {
            Command command = parser.getCommand();
            this.finished = processCommand(command);
            moveCreatures();
            if (finished){
                break;
            }
        }
        print("Thank you for playing.  Good bye.");
    }

    /**
     * Given a command, if the command is known, calls the CommandManager to process it.
     * Return true if the player quits or the end of the game is reached.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown() && command.getCommandWord() != null) {
            print("I don't know what you mean...\n");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        String fourthWord = command.getFourthWord();

        wantToQuit = commandManager.process(commandWord, secondWord, thirdWord, fourthWord);

        return wantToQuit;
    }

    /** implementations of user commands */
    /** All the user commands are called by the CommandManager when it's the case */

    /**
     * Prints the description of everithing around the player.
     * Gets called with the commands: 'see', 'look', 'look around'
     */
    public void see(){
        print("\n" + currentRoom.updateDescription());
    }

    /**
     * Prints all the items the player has and the relatives information.
     * Gets called with the command: 'inventory'
     */
    public void printInventory(){
        print(player.getInventory());
    }

    /**
     * Goes back to the previous room, works only once.
     * Gets called with the commands: 'go back', 'back'
     */
    public void goBack(){
        if (!previousRoom.equals(currentRoom)){
            currentRoom = previousRoom;
            print("\n" + currentRoom.getLongDescription());
            moveVirgil(previousRoom, currentRoom);
        }else{
            print("You didn't go anywhere!\n");
        }
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * Gets called with the command: 'go <keyword>'
     * @param room The exit keyword.
     */
    public void goRoom(String room)
    {
        String direction = room.toLowerCase();
        Room nextRoom = currentRoom.getExit(direction.toLowerCase());
        if (room.equals("back")){
            goBack();
        }
        else if (nextRoom == null) {
            print("There is no such place.\n");
            print(helper.helpUser(currentRoom.getName(), room));
        }else{
            if (nextRoom.isLocked()){
                print(nextRoom.getLockedMessage());
            }else if(nextRoom.getName().equals("vortex")){              /** vortex = random room */
                currentRoom.removeCreature(virgil);
                goRandomRoom();
                moveVirgil(currentRoom, currentRoom);
            }else{
                moveVirgil(currentRoom, nextRoom);
                previousRoom = currentRoom;
                currentRoom = nextRoom;
                print("\n" + currentRoom.getLongDescription());
            }
        }
    }

    /**
     * Prints all the keywords of the exits of the current room.
     * Gets called by the commands: 'exit', 'exits'
     */
    public void printExits(){
        print(currentRoom.getExitString());
    }

    /**
     * Processes the take command, tries to take the item if possible.
     * Gets called with the command: 'take <keyword>'
     * @param item The name of the item to take.
     */
    public void take(String item){
        if (currentRoom.getItem(item) == null){
            print("There is no " + item + " to take.\n");
            print(helper.helpUserItem(item));
        }else{
            Item itemToTake = currentRoom.getItem(item);
            if (itemToTake.isGrabable()){
                if (player.canTake(itemToTake)){
                    try{
                        player.takeItem(itemToTake);
                        currentRoom.removeItem(item);
                        if (item.equals("bucket")){
                            bucket.reveal();
                        }
                        print("You took a " + item + ".\n");
                    }catch(Exception concurrentModificationException){
                        print("Ups, that's embarrassing, it seems that an error occurred.\n" +
                                "Don't worry, you took the item anyway.\n");
                    }
                }else{
                    print("You can't take the " + item + ", it would be too much weight in your inventory!\n");
                }
            }else{
                print("You can't take the " + item + ".\n");
            }
        }
    }

    /**
     * Tries to give the item to the creature if possible.
     * Calls the eventManager to check the implications of the action.
     * Gets called with the command: 'give <item> to <creature>'
     * @param item The name of the item the user wants to give.
     * @param creatureName The name of the creature the user want to give the item.
     */
    public void give(String item, String creatureName){
        if (player.has(item)){
            if (currentRoom.getCreature(creatureName) != null){
                Creature creature = currentRoom.getCreature(creatureName);
                if (creature.isLocked()){
                    print(creature.getLockedMessage());
                }else{
                    eventManager.processGiveEvent(player.getItem(item), creature);
                }
            }else{
                print("There is no " + creatureName + " here.\n");
                print(helper.helpUser(currentRoom.getName(), creatureName));
            }
        }else{
            print("You don't have any " + item + ".\n");
            print(helper.helpUserItem(item));
        }
    }

    /**
     * Tries to drop the Item of the player, if he has one.
     * Gets called with the command: 'drop <item>'
     * @param item The name of the item the user wants to drop.
     */
    public void drop(String item){
        if (player.getItem(item) == null){
            print("You don't have any " + item + ".\n");
            print(helper.helpUserItem(item));
        }else{
            try{
                currentRoom.putItem(player.getItem(item));
                player.removeItem(item);
                print("You dropped a " + item + ".\n");
            }catch(Exception concurrentModificationException){
                print("Ups, that's embarrassing, it seems that an error occurred.\n" +
                        "Don't worry, you dropped the item anyway.\n");
            }
        }
    }

    /**
     * If there is the item to examine, prints the relative message.
     * Gets called with the command: 'examine <item>'
     * (Creatures cannot be examined in this version of the game).
     * @param item The name of the item the user wants to examine.
     */
    public void examine(String item){
        if (currentRoom.getItem(item) != null || player.has(item)){
            Item i = getItem(item);
            print(i.getExamineMessage() + "");
        }else{
            print("There is no such item to examine.\n");
            print(helper.helpUserItem(item));
        }
    }

    /**
     * If there is the creature, prints the dialogue of the creature.
     * Calls EventManager for the implications of the action.
     * Gets called with the command: 'talk <creature>'
     * @param creatureName The name of the creature the user wants to talk with.
     */
    public void talkTo(String creatureName){
        Creature creature = currentRoom.getCreature(creatureName);
        if (creature == null){
            print("There is no " + creatureName + " here.\n");
            print(helper.helpUser(currentRoom.getName(), creatureName));
        }else if (creature.getName().equals("virgil")){
            print(virgil.getDialogue(currentRoom.getName()));
            virgil.increaseDialogueIndex();
        }else if(creature.isLocked()){
            print(creature.getLockedMessage());
        }else if (creature.hasDialogue()){
            eventManager.processDialogueEvent(creature);
            print(creature.getDialogue());
            creature.increaseDialogueIndex();
        }else{
            print("The " + creatureName + " doesn't seem to understand you..\n");
        }
    }

    /**
     * If it's possible, uses an item on a creature or another item.
     * The eventManager does the work.
     * Gets called with the command: 'use <keyword> on <keyword>'
     * @param item The name of the item the user wants to use.
     * @param target The target can be either a creature or another item.
     */
    public void use(String item, String target){
        if (player.has(item)){
            if (currentRoom.getCreature(target) != null){
                eventManager.processUseEvent(player.getItem(item), currentRoom.getCreature(target));
            }else if (currentRoom.getItem(target) != null){
                eventManager.processUseEvent(player.getItem(item), currentRoom.getItem(target));
            }else{
                print("There is no " + target + " here.\n");
                print(helper.helpUserItem(target));
                print(helper.helpUser(currentRoom.getName(), target));
            }
        }else{
            print("You don't have any " + item + ".\n");
        }
    }

    /**
     * Just uses an item. For now the only case is "use boat" to go to the ending, multiple cases can be added.
     * The eventManager does the work.
     * Gets called with the command: use <keyword>
     */
    public void useItem(String itemName){
        if (currentRoom.getItem(itemName) != null){
            Item item = currentRoom.getItem(itemName);
            if (item.isLocked()){
                print(item.getLockedMessage());
            }else{
                eventManager.processUseEvent(currentRoom.getItem(itemName));
            }
        }else{
            if (player.has(itemName)){
                print("Use " + itemName + " on what?\n");
            }else if (currentRoom.getCreature(itemName) != null){
                print("You cannot use characters come on!");
            }else{
                print("There is no " + itemName + " here.\n");
            }
        }
    }

    /** Events, every event method is called by the EventManager when it's the case. */

    /**
     * Kills a creature. All its items are dropped.
     * @param creature The creature that is getting killed.
     */
    public void killCreature(Creature creature){
        currentRoom.removeCreature(creature);
        print("You killed the " + creature.getName() + ".\n");
        dropAll(creature);
        print("");
        player.setWorthy(false);
    }

    /**
     * Unlocks a room.
     * @param room The name of the room that will be unlocked.
     */
    public void unlockRoom(String room){
        getRoom(room).unlock();
    }

    /**
     * Unlocks a creature
     * @param creature The name of the creature that will be unlocked.
     */
    public void unlockCreature(String creature){
        getCreature(creature).unlock();
    }

    /**
     * Unlocks an item.
     * @param item The name of the item that will be unlocked.
     */
    public void unlockItem(String item){
        getItem(item).unlock();
    }

    /**
     * Gives the knife to the player if his weight allows it, otherwise the knife is dropped.
     */
    public void butcherGivesKnifeEvent(Creature butcher){
        Item knife = butcher.getItem("knife");
        butcher.removeItem("knife");
        bucket.empty();
        print("(You empty the bucket on the head of the butcher)\n");
        print("Butcher: Oh my god, thank you, I didn't remember it was so good!\n" +
                "It ain't much, but I want you to keep my knife, at least you will keep it clean.\n");
        if (player.canTake(knife)){
            player.takeItem(knife);
            print("(The bucther gives you his knife)\n");
        }else{
            currentRoom.putItem(knife);
            print("The butcher drops his knife on the ground");
        }
    }

    /**
     * The special character is revealed. Now the player has his guide.
     */
    public void revealVirgiliusEvent(Creature ghost){
        currentRoom.removeCreature(ghost);
        currentRoom.putCreature(virgil);
        player.setVirgil(true);
    }

    /**
     * Drops the iron plate.
     */
    public void mineIronEvent(){
        currentRoom.putItem(getItem("ironplate"));
        print("You mine a huge iron plate that falls on the ground.\n");
    }

    /**
     * Drops the meat.
     * As a result of the action the knife used will be broken.
     */
    public void cutMeatEvent(Item knife){
        currentRoom.putItem(getItem("meat"));
        knife.setExamineMessage("A broken knife.\n");
        print("You cut a big piece of meat that falls on the ground. You broke the knife in the process.\n");
    }

    /**
     * The cerberus doesn't hinder the player anymore.
     */
    public void cerberusFallsAsleep(){
        Creature alchemist = currentRoom.getCreature("alchemist");
        Creature cerberus = currentRoom.getCreature("cerberus");

        alchemist.unlock();
        cerberus.setPresentation("There is cerberus sleeping in tranquillity.\n");
        player.removeItem("meat");
        print("You give the meat to cerberus, who eats it and falls asleep.\n");
    }

    /**
     * Gives the golden coin to the player.
     */
    public void alchemistGivesGoldenCoinEvent(Item materialGiven, Creature alchemist){
        String material = materialGiven.getName();
        player.removeItem(material);

        Item goldenCoin = getItem("goldencoin");
        player.takeItem(goldenCoin);
        alchemist.removeItem(goldenCoin);


        print("You give the " + material + " to the alchemist.\n" +
                "With his philosopher's stone, tranforms it into gold.\n" +
                "(gives you a golden coin)\n");

    }

    /**
     * The player goes to the final room (winning scenario)
     */
    public void goToFinalPlatformEvent(){
        Room river = currentRoom;
        player.removeItem("goldencoin");
        print("Charon: your passage is payed, get on board, I will bring you to Lucifer.\n");
        print("It will be a long crossing, get some rest.\n");
        wait(2000);
        print("Zzz\n");
        wait(2000);
        print("Zzz\n");
        wait(2000);
        currentRoom = getRoom("luciferPlatform");
        previousRoom = getRoom("luciferPlatform");
        if (player.hasVirgil()){
            moveVirgil(river, currentRoom);
        }
        print(currentRoom.getLongDescription());
    }

    /**
     * The player goes to the final room (loosing scenario)
     */
    public void useBoatEvent(){
        print("You take the boat and start crossing the river, you are going straight to Lucifer.\n");
        currentRoom = getRoom("luciferPlatform");
        previousRoom = getRoom("luciferPlatform");
        print(currentRoom.getLongDescription());
    }

    /**
     * The player has now access to all the other rooms.
     */
    public void guardianEvent(Creature guardian){
        unlockRoom("limbo");
        guardian.setPresentation("The guardian is standing beside the door.\n");
    }

    /**
     * Gives the pickaxe to the player if his weight allows it, otherwise the pickaxe is dropped.
     */
    public void strangerGivesAxeEvent(Creature stranger){
        Item pickaxe = stranger.getItem("pickaxe");
        stranger.removeItem(pickaxe);
        if (player.canTake(pickaxe)){
            player.takeItem(pickaxe);
            print("Stranger: Take my pickaxe, I don't need it.\n"+
                    "(The stranger gives you a pickaxe)\n");
        }else{
            currentRoom.putItem(pickaxe);
            print("Stranger: I wish to give you my pickaxe, I don't need it.\n"+
                    "(The stranger drops a pickaxe)\n");
        }
    }

    /**
     * The player gets killed (game quits)
     */
    public void luciferKillsYouEvent(){
        print("Lucifer disarms you and kills you.\n");
        wait(2000);
        print("You lost.\n");
        commandManager.setWantToFinish(true);
        finished = commandManager.process("quit", "","","");
    }

    /**
     * This is the end of the game, checking the winning conditions.
     */
    public void endingEvent(){
        if (player.isWorthy()){
            printWin();
            currentRoom = getRoom("ending");
        }else{
            printLose();
            commandManager.setWantToFinish(true);
            finished = commandManager.process("quit", "","","");
        }
    }

    /**  private methods */

    /**
     * Add a creature to the game global collection.
     * @param collection Can be either creatures or movingCreatures
     */
    private void addCreature(HashSet collection, Creature creature){
        collection.add(creature);
    }

    /**
     * Add a room to the game global collection.
     * @param collection Can be either rooms or infernalRooms(teleportable rooms)
     */
    private void addRoom(HashSet collection,Room room){
        collection.add(room);
    }

    /**
     * Add an item to the game global collection.
     */
    private void addItem(Item item){
        items.add(item);
    }

    /**
     * A creature drops every item it has. Called when a creature gets killed.
     */
    private void dropAll(Creature creature){
        if (creature.hasItems()){
            HashSet<Item> creatureInventory = creature.getInventory();
            Iterator<Item> it = creatureInventory.iterator();
            while (it.hasNext()){
                Item i = it.next();
                creature.removeItem(i);
                currentRoom.putItem(i);
            }
            print("The " + creature.getName() + " dropped all its items.\n");
        }else{
            print("The " + creature.getName() + " had no items to drop.\n");
        }
    }
    /**
     * Moves the special character.
     */
    private void moveVirgil(Room room1, Room room2){
        if (player.hasVirgil()){
            room1.removeCreature(virgil);
            room2.putCreature(virgil);
            virgil.setCurrentRoom(room2.getName());
            virgil.resetIndex();
        }
    }

    /**
     * Gets printed at the very start of the game.
     */
    private void printStart(){
        print("\nIn the middle of the journey of your life\n" +
                "You came to yourself in a dark wood\nWhere the direct way was lost..\n\n");
        print("Welcome to this adventure game developed by Jacopo Madaluni, the setting is inspired by\n" +
                "a poem called: Divina Commedia. Your character name is 'Dante'.\n");
        print("He woke up in this strange place, you have to help him escape.\n");
        print("I suggest you to start looking around.\n");

        print("Type textSpeed <value> to set the speed of the dynamic printing.\n");
        print("If you are marking the assignment I would suggest to set 'textSpeed 0' to save time.\n");

        print("Type 'help' for instructions.\n");
    }

    private void printWin(){
        print("Lucifer: Traveler! How dare you to come in my presence, here in my realm?\n\n");
        wait(3000);
        print("Dante: I do not belong here Lucifer! I am still alive, and you know that!\n\n");
        wait(3000);
        if (player.hasVirgil()){
            print("Virgil: If your are a real ruler, you will let him leave this place, the laws of this place leave you no choice!\n\n");
        }else{
            print("Dante: If your are a real ruler, you will let me leave this place, the laws of this place leave you no choice!\n");
        }
        wait(3000);
        print("Lucifer: ...\n");
        wait(2000);
        print("Lucifer: So It will be, mortal, you are free to leave the inferno, but don't think you changed your fate..\n");
        wait(3000);
        print("Lucifer: One day death will take you, and then we will see, if you'll be unworthy of the circles of heaven you\n" +
                "         will stay here for the eternity.\n\n");

        wait(3000);
        print ("Virgil: goodbye Dante, be good on the world of mortals.\n\n");
        wait(3000);
        print("(Lucifer shows you the stairs to leave the Inferno, you climb them.)\n\n");
        wait(2000);
        print("You are looking at the stars once again.\n\n\n");
        wait(5000);
        print("Congratulations! You won the game.\n");

    }

    private void printLose(){
        print("Lucifer: Traveler! How dare you to come in my presence, here in my realm?\n\n");
        wait(3000);
        print("Dante: I do not belong here Lucifer! I am still alive, and you know that!\n\n");
        wait(3000);
        print("Lucifer: You are wrong, mortal, your soul is as corrupted as mine.\n");
        wait(3000);
        print("From now to eternity you will be confined in the Inferno, this is the law of this place!\n");
        print("(Lucifer stuns you and you faint)\n");
        wait(3000);
        print("...\n");
        wait(3000);
        print("...\n");
        wait(3000);
        print("You woke up chained, you will remain in the Inferno for ever!\n");
        print("                            You lost.\n");
    }

    /**
     * Teleports to the random room (user friendly).
     * The implementation of getRandomRoom() follows
     */
    private void goRandomRoom(){
        currentRoom = getRandomRoom();
        previousRoom = currentRoom;
        print("You jump into the vortex and faint.\n");
        wait(1000);
        print("...\n");
        wait(1000);
        print("...\n");
        wait(1000);
        print("You woke up in the " + currentRoom.getName() + ".\n\n");
    }

    /**
     * @return A random room from the collection of random rooms
     */
    private Room getRandomRoom(){
        int size = infernalRooms.size();
        int randomRoomIndex = new Random().nextInt(size);
        int index = 0;
        for (Room room : infernalRooms){
            if (randomRoomIndex == index){
                return room;
            }
            index++;
        }
        return null;
    }

    private void moveCreatures(){
        for (Creature movingCreature : movingCreatures){
            if (Math.random() > 0.7){                         /** some random chance to move the creature */
                for (Room room : infernalRooms){              /** find where the creature is  */
                    if (room.getCreature(movingCreature.getName()) != null){
                        Room randomRoom = getRandomRoom();
                        if (randomRoom.equals(room)){
                            break;     // don't move to the same room.
                        }
                        room.removeCreature(movingCreature);
                        randomRoom.putCreature(movingCreature);
                        if (room.equals(currentRoom)){
                            print("\nThe " + movingCreature.getName() + " moved.\n"); // let the player know who moved.
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method creates the actual game.
     */
    private void initializeGame(){
        /** virgil */

        Creature ghost = new Creature("ghost", "Near a tree you see a ghost. He's looking at you.\n");
        addCreature(creatures, ghost);
        ghost.addDialogue(1, "Virgil: Hello Dante, I am Virgil. You find yourself at the doors of the Inferno.\n" +
                "If you want to escape from here you will have to find Lucifer, the pagan God that rules this realm.\n" +
                "I have been sent here from God himself to help you, I will be your guide.\n");

        Virgilius virgil = new Virgilius("virgil", "Virgil is standing silent near you.\n");
        this.virgil = virgil;

        /** creatures */

        Creature tormentedSouls = new Creature("souls", "You see a group of tormented souls.\n");
        addCreature(creatures, tormentedSouls);
        addCreature(movingCreatures, tormentedSouls);
        tormentedSouls.addDialogue(1, "Souls: Stranger, stay away from this place, it belongs to the people who are dead..\n");
        tormentedSouls.addDialogue(1, "Souls: Go away..\n");
        tormentedSouls.addDialogue(1, "Souls: Let us go..\n");
        tormentedSouls.addDialogue(1, "Souls: Don't talk to us..\n");

        Creature stranger = new Creature("stranger", "A stranger is sitting near the hut.\n");
        addCreature(creatures, stranger);
        stranger.addDialogue(2, "Stranger: Hello traveler, what brings you in this desolate place?\n");
        stranger.addDialogue(3, "Stranger: Oh.. so you woke up here and you don't know why, well  all the people here have the same story.\n");
        stranger.addDialogue(5,"");
        stranger.addDialogue(10,"Stranger: You still got my pickaxe right? It is so precious to me, don't lose it!\n");

        Creature lion = new Creature("lion", "You see a lion.\n");
        addCreature(creatures, lion);

        Creature guardian = new Creature("guardian", "A guardian is standing in front of the door.\n");
        addCreature(creatures, guardian);
        guardian.addDialogue(2, "Guardian : Hey you! You don't belong to this place, turn back and don't come again.\n");
        guardian.addDialogue(3, "Guardian : Don't talk to me, you have no right to get into the realm of those who are dead.\n");
        guardian.addDialogue(4, "Guardian : You can stay here the whole day, I won't let you pass through.\n");
        guardian.addDialogue(4, "Guardian : I won't let you pass.\n");
        guardian.addDialogue(4, "Guardian : I won't change my mind, I will not let you pass.\n");
        guardian.addDialogue(5, "Virgil: guardian, let us pass, this is the will of god.\n" +
                "(The guardian goes aside).\n");
        guardian.addDialogue(10, "The guardian is silent.\n");

        Creature cerberus = new Creature("cerberus", "There is a terrifing giant dog with three heads: Cerberus, he seems hungry.\n");
        addCreature(creatures, cerberus);
        cerberus.addDialogue(1, "The terrifing dog growls at you.\n");

        Creature charon = new Creature("charon", "You see Charon, the ferryman of souls, ready to leave the river with his load.\n");
        addCreature(creatures, charon);
        charon.addDialogue(2, "Traveler, if you want me to bring you to Lucifer you will have to pay your passage " +
                "with a golden coin.\n");
        charon.addDialogue(3, "If you don't pay I'm not gonna bring you , this is the rule.\n");
        charon.addDialogue(4, "");

        Creature butcher = new Creature("butcher", "You see a tormented man in the middle of the swamp. It looks like a\n" +
                "butcher for how he's dressed.\n");
        addCreature(creatures, butcher);
        butcher.addDialogue(2, "Butcher: Traveler, what brings you in this place of agony?\n");
        butcher.addDialogue(3, "Butcher: I see, you woke up here and you don't know why, well,\n" +
                "at least you are not into ethernal punishment.\n");
        butcher.addDialogue(4, "Butcher: My punishment is to stay here in this muddy swamp for ever, since\n" +
                "I enjoyed to much the privilege of luxury in life.\n");

        butcher.addDialogue(6, "Butcher: I hate this swamp, I can feel my dirty body.\n");
        butcher.addDialogue(6, "Butcher: I almost forgot how it feels to have a clean body.\n");
        butcher.addDialogue(6, "Butcher: So dirty, so revolting, disgusting.\n");
        butcher.addDialogue(5, "Butcher: Oh my god, thank you, I didn't remember it was so good!\n" +
                "It ain't much, but I want you to keep my knife, at least you will keep it clean.\n");
        butcher.addDialogue(10,"Thank you again, you are a good man.\n");

        Creature alchemist = new Creature("alchemist", "Behind the beast you can see an old man that looks like an alchemist..\n");
        addCreature(creatures, alchemist);
        alchemist.lock();
        alchemist.setLockedMessage("Cerberus growls at you and doesn't let you pass.\n");

        alchemist.addDialogue(2, "Alchemist: I can read by the sparkle of your eyes that you want to get out from this place..\n");
        alchemist.addDialogue(3, "Alchemist: I am sorry traveler, I cannot help you. No one leaves this realm.\n  This is the will of Lucifer..\n");
        alchemist.addDialogue(4, "Alchemist: Oh, very well, so you need a golden coin.. Bring me any material,\n " +
                "and i will transform it into gold.\n");
        alchemist.addDialogue(6,  "Alchemist: You have to bring me some kind of material, otherwise I won't be able to help you.\n");
        alchemist.addDialogue(10, "Alchemist: I gave you the golden coin, go face Luciferus now, may the force be with you..\n");

        Creature lucifer = new Creature("lucifer", "In the very middle of the platform stands Lucifer, a huge demon with three heads.\n");
        lucifer.addDialogue(1, "");
        addCreature(creatures, lucifer);

        /** items */

        Item key = new Item("key", 1, "There is a key on the ground.\n",true);
        addItem(key);
        key.setExamineMessage("A key");

        Item pickaxe = new Item("pickaxe",3, "You see a pickaxe on the ground.\n", true);
        pickaxe.setWeapon();
        stranger.takeItem(pickaxe);
        addItem(pickaxe);
        pickaxe.setType("Weapon");
        pickaxe.setExamineMessage("A pickaxe, seems a very good tool. It can probably hurt people.\n");

        Item meat = new Item("meat", 4, "There is some meat on the ground.\n", true);
        addItem(meat);
        meat.setType("MISC");
        meat.setExamineMessage("A piece of raw meat.\n");

        Item ironPlate = new Item("ironplate", 7, "You see an iron plate on the ground.\n", true);
        addItem(ironPlate);
        ironPlate.setType("material");
        ironPlate.setExamineMessage("A heavy piece of rough iron.\n");

        Item goldenCoin = new Item ("goldencoin", 0, "The golden coin is on the ground.\n", true);
        addItem(goldenCoin);
        goldenCoin.setType("MISC");
        alchemist.takeItem(goldenCoin);
        goldenCoin.setExamineMessage("A coin made of gold.\n");

        Item boat = new Item("boat", 0, "The Charon's boat moored on the banks of the river.\n", false);
        addItem(boat);
        boat.lock();
        boat.setLockedMessage("Charon : What do you think, that you can take my boat?!\n");
        boat.setExamineMessage("An old boat made of wood, how does it even float without sinking?\n");

        Item knife = new Item("knife", 1, "You see a knife on the ground.\n", true);
        addItem(knife);
        knife.setType("Weapon");
        knife.setWeapon();
        knife.setExamineMessage("This knife looks like a perfect tool for a butcher, very sharp indeed.\n");
        butcher.takeItem(knife);

        Item door = new Item("door", 0, "", false);
        addItem(door);
        door.setExamineMessage("The door brings to the limbo, the first part of the inferno.\n" +
                "You can read a message: Through me you come to the sore city.\n");

        Item corpse = new Item("corpse",0, "In the grass you can see a dead corpse of a strange huge animal.\n", false);
        addItem(corpse);
        corpse.setExamineMessage("This corpse'smell is disgusting.\n");

        Item riverItem = new Item("river",0, "",false);
        addItem(riverItem);
        riverItem.setExamineMessage("The river is so black you cannot see the end of it, it seems it is made of true water though.\n");

        Item rocks = new Item("rocks",0, "There are many huge and massive rocks around the forest.\n", false);
        addItem(rocks);
        rocks.setExamineMessage("You look carefully, it seems that the rocks are actually made of iron.\n");

        Item grass = new Item("grass",0, "", false);
        addItem(grass);
        grass.setExamineMessage("The grass has a strange shade of orange, like the orange of a sunset, shouldn't it be spring?\n");

        Item trees = new Item("trees",0, "The forest is full of red trees.\n",false);
        addItem(trees);
        trees.setExamineMessage("You get closer to the trees, they seem ancient, milions of years old..charming.\n");

        Item stars = new Item("stars", 0, "You look at the stars from the earth.\nYou would need a spaceship to go ahead, but that's another game..\nType 'quit' to quit.\n",false);
        addItem(stars);
        stars.setExamineMessage("The stars are beautiful.\n");

        Item hut = new Item("hut",0, "At the foot of the hill you see a little hut, made of straw.\n",false);
        addItem(hut);
        hut.setExamineMessage("You look inside the hut: you see a bunch of tools and a bucket.\n");

        Bucket bucket = new Bucket("bucket", 2, "");
        addItem(bucket);
        bucket.setExamineMessage("The bucket seems very large.\n");
        this.bucket = bucket;

        /** rooms */

        Room darkWood = new Room("darkWood", "You are back at the beginning of your journey.\n");
        addRoom(rooms, darkWood);
        darkWood.addExitPresentation("Not far away you see a misterious clear.\n");
        darkWood.addExitPresentation("You see a hill at the end of the dark wood.\n");

        Room misteriousClear = new Room ("misteriousClear" , "You arrive in a wonderful clear surrounded by blue trees.\n");
        addRoom(rooms, misteriousClear);
        misteriousClear.addExitPresentation("The dark wood is not far away.\n");

        Room hill = new Room ("hill", "You arrive at the hill.\n");
        addRoom(rooms, hill);
        hill.addExitPresentation("Behind you you see the entrance of the dark wood.\n");
        hill.addExitPresentation("You see a strange entrance down the hill.\n");

        Room infernosEntrance = new Room ("infernosEntrance" , "You arrive at the door of the Inferno.\n");
        addRoom(rooms, infernosEntrance);
        infernosEntrance.addExitPresentation("The hill makes shadow behind you.\n");
        infernosEntrance.addExitPresentation("Over the entrance you see the limbo.\n");

        Room limbo = new Room("limbo" , "You follow the cliff and get into the limbo.\n");
        addRoom(rooms, limbo);
        addRoom(infernalRooms, limbo);
        limbo.lock();
        limbo.setLockedMessage("The guardian doesn't let you pass.\n");
        limbo.addExitPresentation("The entrance where you came from is overhead.\n");
        limbo.addExitPresentation("You can see a swamp down some creepy stairs.\n");
        limbo.addExitPresentation("You see a path leading to a red forest.\n");
        limbo.addExitPresentation("Down a cliff you see a black river.\n");

        Room infernalForest = new Room("infernalForest", "You follow a bumpy pathway, you are now into an infernal forest.\n");
        addRoom(rooms, infernalForest);
        addRoom(infernalRooms, infernalForest);
        infernalForest.addExitPresentation("You can see the limbo at the end of the bumpy pathway.\n");
        infernalForest.addExitPresentation("Beyond this infernal forest there is a nasty narrow.\n");

        Room nastyNarrow = new Room("nastyNarrow", "You arrive at the nasty narrow.\n");
        addRoom(rooms, nastyNarrow);
        addRoom(infernalRooms, nastyNarrow);
        nastyNarrow.addExitPresentation("The forest behind you scares you.\n");

        Room swamp = new Room("swamp","You come to a disgusting swamp.\n");
        addRoom(rooms, swamp);
        addRoom(infernalRooms, swamp);
        swamp.addExitPresentation("Back on your steps there is the limbo.\n");
        swamp.addExitPresentation("You see an infernal vortex, it seems that you can go into it.\n");

        Room vortex = new Room("vortex", "");  // random room

        Room river = new Room("river", "You go further down the cliff and arrive at the river, its cold there.\n");
        addRoom(rooms, river);
        addRoom(infernalRooms, river);
        river.addExitPresentation("On the top of the cliff you can see the limbo where you came from.\n");

        Room luciferPlatform = new Room("luciferPlatform", "You arrive to a huge frozen platform.\n");
        addRoom(rooms, luciferPlatform);

        Room ending = new Room("ending", "");
        addRoom(rooms, ending);

        /** Putting everything into the rooms */

        misteriousClear.putCreature(ghost);

        hill.putCreature(lion);
        hill.putItem(hut);
        hill.putItem(bucket);
        hill.putCreature(stranger);
        hill.putItem(corpse);
        hill.putItem(grass);

        infernosEntrance.putCreature(guardian);
        infernosEntrance.putItem(door);

        limbo.putCreature(tormentedSouls);

        infernalForest.putItem(trees);
        infernalForest.putItem(rocks);
        infernalForest.putItem(grass);

        nastyNarrow.putCreature(cerberus);
        nastyNarrow.putCreature(alchemist);

        swamp.putCreature(butcher);

        river.putCreature(charon);
        river.putItem(boat);
        river.putItem(riverItem);

        luciferPlatform.putCreature(lucifer);

        ending.putItem(stars);

        /** Setting the exits keywords */

        darkWood.setExit("misteriousclear", misteriousClear);
        darkWood.setExit("clear", misteriousClear);
        darkWood.setExit("misterious", misteriousClear);
        darkWood.setExit("hill", hill);

        misteriousClear.setExit("darkwood", darkWood);
        misteriousClear.setExit("wood", darkWood);
        misteriousClear.setExit("woods", darkWood);
        misteriousClear.setExit("dark", darkWood);

        hill.setExit("entrance", infernosEntrance);
        hill.setExit("strange", infernosEntrance);
        hill.setExit("darkwood", darkWood);
        hill.setExit("wood", darkWood);
        hill.setExit("woods", darkWood);
        hill.setExit("dark", darkWood);

        infernosEntrance.setExit("hill", hill);
        infernosEntrance.setExit("limbo", limbo);
        infernosEntrance.setExit("entrance", limbo);
        infernosEntrance.setExit("door", limbo);
        infernosEntrance.setExit("inferno", limbo);
        infernosEntrance.setExit("hell", limbo);

        limbo.setExit("entrance", infernosEntrance);
        limbo.setExit("red", infernalForest);
        limbo.setExit("infernalforest", infernalForest);
        limbo.setExit("infernal", infernalForest);
        limbo.setExit("forest", infernalForest);
        limbo.setExit("swamp", swamp);
        limbo.setExit("disgustingswamp", swamp);
        limbo.setExit("disgusting", swamp);
        limbo.setExit("river", river);
        limbo.setExit("black", river);

        infernalForest.setExit("limbo", limbo);
        infernalForest.setExit("nastynarrow", nastyNarrow);
        infernalForest.setExit("narrow", nastyNarrow);
        infernalForest.setExit("nasty", nastyNarrow);

        nastyNarrow.setExit("infernalforest", infernalForest);
        nastyNarrow.setExit("forest", infernalForest);
        nastyNarrow.setExit("infernal", infernalForest);

        swamp.setExit("limbo", limbo);
        swamp.setExit("vortex",vortex);

        river.setExit("limbo", limbo);

        /** Set the starting room */
        currentRoom = darkWood;
    }

    /**
     * This method is a shortcut that just waits.
     */
    private void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Prints the string character by character with a delay of 25 ms.
     * @param s The string to be printed dynamically.
     */
    public static void print(String s){
        if (textSpeed != 0 ){
            char[] charArray = s.toCharArray();
            for (char c : charArray){
                System.out.print(c);
                try{
                    Thread.sleep(textSpeed);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            System.out.println("");
        }else{
            System.out.print(s);
        }
    }
}


package com.company;

import java.util.HashSet;
import java.util.Random;

public class TransporterRoom extends Room {

    private HashSet<Room> possibleRooms;
    public TransporterRoom(String name, String description){
        super(name, description);
        possibleRooms = new HashSet<>();
    }

    public void addPossibleRoom(Room newRoom){
        possibleRooms.add(newRoom);
    }

    @Override
    public Room getExit(String direction){
        return getRandomRoom();
    }

    private Room getRandomRoom(){
        int size = possibleRooms.size();
        int randomRoomIndex = new Random().nextInt(size);
        int index = 0;
        for (Room room : possibleRooms){
            if (randomRoomIndex == index){
                return room;
            }
            index++;
        }
        return null;
    }
}

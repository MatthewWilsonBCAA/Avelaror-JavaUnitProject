package com.company;
import java.util.ArrayList;
public class Room implements java.io.Serializable {
    //Ref variables are used in world generation only
    String title;
    String baseDescription;
    ArrayList<Entity> entities = new ArrayList<Entity>();
    String entitiesRef;
    ArrayList<Item> items = new ArrayList<Item>();
    String itemsRef;
    //these are going to be parallel lists
    int[] rooms;


    public String getItemsRef() {
        return itemsRef;
    }

    public void setItemsRef(String itemsRef) {
        this.itemsRef = itemsRef;
    }

    public String getRoomsRef() {
        return roomsRef;
    }

    public void setRoomsRef(String roomsRef) {
        this.roomsRef = roomsRef;
    }

    public String getRoomCommandsRef() {
        return roomCommandsRef;
    }

    public void setRoomCommandsRef(String roomCommandsRef) {
        this.roomCommandsRef = roomCommandsRef;
    }

    public String getEntitiesRef() {
        return entitiesRef;
    }

    public void setEntitiesRef(String entitiesRef) {
        this.entitiesRef = entitiesRef;
    }

    String roomsRef;
    String[] roomCommands;
    String roomCommandsRef;
    public void SetTitle (String t) {
        title = t;
    }
    public String GetTitle() {
        return title;
    }
    public void SetBaseDescription(String d) {
        baseDescription = d;
    }
    public String GetBaseDescription() {
        return baseDescription;
    }
    public void SetEntities(ArrayList<Entity> e) {
        if (e != null) {
            for (Entity reg : e) {
                Entity temp = new Entity();
                temp.setName(reg.getName());
                temp.setInventory(reg.getInventory());
                temp.setHp(reg.getHp());
                temp.setVitality(reg.getVitality());
                temp.setStrength(reg.getStrength());
                temp.setDexterity(reg.getDexterity());
                temp.setPower(reg.getPower());
                temp.setWill(reg.getWill());
                temp.setAgility(reg.getAgility());
                temp.SetDefaultLine(reg.getOpeningLine());
                temp.SetAggroLine(reg.getAggroLine());
                temp.SetPrimaryAttack(reg.GetPrimaryAttack());
                temp.SetPrimaryValue(reg.GetPrimaryValue());
                entities.add(temp);

            }
        }
    }
    public void SetItems(ArrayList<Item> i) {
        for (Item rex : i) {
            Item temp = new Item();
            temp.setName(rex.getName());
            temp.setFlavorText(rex.getFlavorText());
            temp.setEffect(rex.getEffect());
            temp.setEffectRating(rex.getEffectRating());
            temp.setStrengthRequirement(rex.getStrengthRequirement());
            temp.setStrengthScaling(rex.getStrengthScaling());
            temp.setDexterityRequirement(rex.getDexterityRequirement());
            temp.setDexterityScaling(rex.getDexterityScaling());
            temp.setPowerRequirement(rex.getPowerRequirement());
            temp.setPowerScaling(rex.getPowerScaling());
            temp.setWillRequirement(rex.getWillRequirement());
            temp.setWillScaling(rex.getWillScaling());
            temp.setWeight(rex.getWeight());
            temp.setValue(rex.getValue());
            items.add(temp);
        }
    }
    public ArrayList<Entity> GetEntities() {
        return entities;
    }
    public ArrayList<Item> GetItems() {
        return items;
    }

    public int[] GetRooms() {
        return rooms;
    }

    public void SetRooms(int[] rooms) {
        this.rooms = rooms;
    }

    public String[] GetRoomCommands() {
        return roomCommands;
    }

    public void SetRoomCommands(String[] roomCommands) {
        this.roomCommands = roomCommands;
    }

    public Room () {

    }
    public int CheckDirection(String in) {
        int i;
        for (i = 0; i < rooms.length; i++) {
            if (in.equals(roomCommands[i])) {
                return rooms[i];
            }
        }
        return -1;
    }
}

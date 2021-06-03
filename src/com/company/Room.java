package com.company;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Room implements java.io.Serializable {
    //Ref variables are used in world generation only
    String title;
    String baseDescription;
    ArrayList<Entity> entities = new ArrayList<>();
    String entitiesRef;
    ArrayList<Item> items = new ArrayList<>();
    String itemsRef;
    //these are going to be parallel lists
    int[] rooms;

    String roomsRef;
    String[] roomCommands;
    String roomCommandsRef;
    public void setEntities(ArrayList<Entity> e) {
        if (e != null) {
            for (Entity reg : e) {
                Entity temp = new Entity();
                temp.setName(reg.getName());
                temp.setInventory(reg.getInventory());
                temp.setHP(reg.getHp());
                temp.setVitality(reg.getVitality());
                temp.setStrength(reg.getStrength());
                temp.setDexterity(reg.getDexterity());
                temp.setPower(reg.getPower());
                temp.setWill(reg.getWill());
                temp.setAgility(reg.getAgility());
                temp.setOpeningLine(reg.getOpeningLine());
                temp.setAggroLine(reg.getAggroLine());
                temp.setPrimaryAttack(reg.getPrimaryAttack());
                temp.setPrimaryValue(reg.getPrimaryValue());
                temp.setBecomeHostile(reg.getBecomeHostile());
                entities.add(temp);

            }
        }
    }
    public void setItems(ArrayList<Item> i) {
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

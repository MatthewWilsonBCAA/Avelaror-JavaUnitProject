package com.company;
import java.util.ArrayList;
public class Room {
    public String title;
    public String baseDescription;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Item> items = new ArrayList<Item>();
    //these are going to be parallel lists
    int[] rooms;
    String[] roomCommands;
    public Room (String t, String b, ArrayList<Entity> e, ArrayList<Item> i, int[] r, String[] rc) {
        title = t;
        baseDescription = b;
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
        for (Item rex : i) {
            Item temp = new Item();
            temp.setName(rex.getName());
            temp.setFlavorText(rex.getFlavorText());
            temp.setEffect(rex.getEffect());
            temp.setEffectRating(rex.getEffectRating());
            temp.setStrengthRequirement(rex.getStrengthRequirement());
            temp.setStrengthScaling(rex.getStrengthScaling());
            temp.setDexterityRequirement(rex.getDexterityRequirement());
            temp.setStrengthScaling(rex.getDexterityScaling());
            temp.setPowerRequirement(rex.getPowerRequirement());
            temp.setPowerScaling(rex.getPowerScaling());
            temp.setWillRequirement(rex.getWillRequirement());
            temp.setWillScaling(rex.getWillScaling());
            temp.setWeight(rex.getWeight());
            temp.setValue(rex.getValue());
            items.add(temp);
        }
        rooms = r;
        roomCommands = rc;
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

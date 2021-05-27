package com.company;

import java.util.ArrayList;

public class Entity implements java.io.Serializable {
    String name;
    ArrayList<Item> inventory = new ArrayList<Item>();

    int hp;
    int maxHp;
    int vitality;
    int strength;
    int dexterity;
    int power;
    int will;
    int agility;
    //only for NPCs, not the player
    String openingLine;
    String aggroLine;
    boolean isHostile = false;
    int primaryAttack;
    int primaryValue;

    public int getBecomeHostile() {
        return becomeHostile;
    }

    public void setBecomeHostile(int becomeHostile) {
        this.becomeHostile = becomeHostile;
    }

    int becomeHostile;//0, no. 1, yes.
    public void SetPrimaryAttack(int p) {
        primaryAttack = p;
    }
    public int GetPrimaryAttack() {
        return primaryAttack;
    }
    public void SetPrimaryValue(int p) {
        primaryValue = p;
    }
    public int GetPrimaryValue() {
        return primaryValue;
    }
    public Entity() {

    }
    public int getLevel() {
        return vitality + strength + dexterity + power + will + agility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
        this.maxHp = vitality * 10;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getWill() {
        return will;
    }

    public void setWill(int will) {
        this.will = will;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public String getOpeningLine() {
        return openingLine;
    }

    public void setOpeningLine(String openingLine) {
        this.openingLine = openingLine;
    }

    public String getAggroLine() {
        return aggroLine;
    }

    public void setAggroLine(String aggroLine) {
        this.aggroLine = aggroLine;
    }

    public boolean isHostile() {
        return isHostile;
    }

    public void setHostile(boolean hostile) {
        isHostile = hostile;
    }

    public int getPrimaryAttack() {
        return primaryAttack;
    }

    public void setPrimaryAttack(int primaryAttack) {
        this.primaryAttack = primaryAttack;
    }

    public int getPrimaryValue() {
        return primaryValue;
    }

    public void setPrimaryValue(int primaryValue) {
        this.primaryValue = primaryValue;
    }

    public String applyEffect(int effect, int value, String attacker, String target) {
        if (effect > 0 && effect < 6) {
            isHostile = true;
            int dmg = value;
            //System.out.println("before: " + hp);
            hp -= Math.max(dmg, 1);
            //System.out.println("after: " + hp);
            if (hp <= 0) {
                return "DEFEAT";
            }
            return attacker + " dealt " + dmg + " damage to " + target + "\n";
        }
        else if (effect == -1) {
            isHostile = true;
        }
        return "The effect failed";
    }
    public boolean checkAggro() {
        return isHostile;
    }
    public void SetDefaultLine(String line) {
        openingLine = line;
    }
    public void SetAggroLine(String line) {
        aggroLine = line;
    }
    public String GetDefaultLine() {
        if (isHostile) {
            return aggroLine;
        }
        return openingLine;
    }
    public void SetHP(int h) {
        hp = h;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
}

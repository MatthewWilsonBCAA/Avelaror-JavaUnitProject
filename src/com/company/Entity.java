package com.company;

import java.util.ArrayList;

public class Entity {
    public String name;
    public ArrayList<Item> inventory = new ArrayList<Item>();

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
    public int primaryAttack;
    public int primaryValue;
    public Entity(String tName, int vit, int str, int dex, int pow, int wil, int agi, int pri, int val) {
        name = tName;
        vitality = vit;
        strength = str;
        dexterity = dex;
        power = pow;
        will = wil;
        agility = agi;
        maxHp = vitality * 10;
        primaryAttack = pri;
        primaryValue = val;
    }
    public int getLevel() {
        return vitality + strength + dexterity + power + will + agility;
    }
    public String applyEffect(int effect, int value, String attacker, String target) {
        if (effect > 0 && effect < 4) {
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

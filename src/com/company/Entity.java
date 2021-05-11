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
    String openingLine;
    public Entity(String tName, int vit, int str, int dex, int pow, int wil, int agi) {
        name = tName;
        vitality = vit;
        strength = str;
        dexterity = dex;
        power = pow;
        will = wil;
        agility = agi;
        maxHp = vitality * 10;
    }
    public int getLevel() {
        return vitality + strength + dexterity + power + will + agility;
    }
    public String applyEffect(int effect, int value, String attacker, String target) {
        int dmg = value;
        //System.out.println("before: " + hp);
        hp -= dmg;
        //System.out.println("after: " + hp);
        if (hp <= 0) {
            return "DEFEAT";
        }
        return attacker + " dealt " + dmg + " damage to " + target;
    }
    public void SetDefaultLine(String line) {
        openingLine = line;
    }
    public String GetDefaultLine() {
        return openingLine;
    }
    public void SetHP(int h) {
        hp = h;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
}

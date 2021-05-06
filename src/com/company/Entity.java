package com.company;

public class Entity {
    public String name;
    int hp;
    int maxHp;
    int vitality;
    int strength;
    int dexterity;
    int power;
    int will;
    int agility;
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
    public void applyEffect(int effect, int value) {
        //this will be where an entity reacts to an action
    }
    public void takeDamage(int damageType, int damage) {
        //this will be called from applyEffect: used to apply damage
    }
}

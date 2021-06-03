package com.company;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
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

    int becomeHostile;//0, no. 1, yes.
    public Entity() {

    }
    public int getLevel() {
        return vitality + strength + dexterity + power + will + agility;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
        this.maxHp = vitality * 10;
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
    public String GetDefaultLine() {
        if (isHostile) {
            return aggroLine;
        }
        return openingLine;
    }
    public void setHP(int h) {
        hp = h;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
}

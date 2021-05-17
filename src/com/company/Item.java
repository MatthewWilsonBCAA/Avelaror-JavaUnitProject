package com.company;

public class Item implements java.io.Serializable {
    public String name;
    public String flavorText;
    public int effect;
    public int effectRating;
    public int strengthRequirement;
    public float strengthScaling;
    public int dexterityRequirement;
    public float dexterityScaling;
    public int powerRequirement;
    public float powerScaling;
    public int willRequirement;
    public float willScaling;
    public float weight;
    public int value;

    public String getName() {
        return name;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public int getEffect() {
        return effect;
    }

    public int getEffectRating() {
        return effectRating;
    }

    public int getStrengthRequirement() {
        return strengthRequirement;
    }

    public float getStrengthScaling() {
        return strengthScaling;
    }

    public int getDexterityRequirement() {
        return dexterityRequirement;
    }

    public float getDexterityScaling() {
        return dexterityScaling;
    }

    public int getPowerRequirement() {
        return powerRequirement;
    }

    public float getPowerScaling() {
        return powerScaling;
    }

    public int getWillRequirement() {
        return willRequirement;
    }

    public float getWillScaling() {
        return willScaling;
    }

    public float getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setEffectRating(int effectRating) {
        this.effectRating = effectRating;
    }

    public void setStrengthRequirement(int strengthRequirement) {
        this.strengthRequirement = strengthRequirement;
    }

    public void setStrengthScaling(float strengthScaling) {
        this.strengthScaling = strengthScaling;
    }

    public void setDexterityRequirement(int dexterityRequirement) {
        this.dexterityRequirement = dexterityRequirement;
    }

    public void setDexterityScaling(float dexterityScaling) {
        this.dexterityScaling = dexterityScaling;
    }

    public void setPowerRequirement(int powerRequirement) {
        this.powerRequirement = powerRequirement;
    }

    public void setPowerScaling(float powerScaling) {
        this.powerScaling = powerScaling;
    }

    public void setWillRequirement(int willRequirement) {
        this.willRequirement = willRequirement;
    }

    public void setWillScaling(float willScaling) {
        this.willScaling = willScaling;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Item() {

    }

}

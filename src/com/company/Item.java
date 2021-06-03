package com.company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item implements java.io.Serializable {
    String name;
    String flavorText;
    int effect;
    int effectRating;
    int strengthRequirement;
    float strengthScaling;
    int dexterityRequirement;
    float dexterityScaling;
    int powerRequirement;
    float powerScaling;
    int willRequirement;
    float willScaling;
    float weight;
    int value;

    public Item() {

    }

}

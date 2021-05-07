package com.company;

public class Item {
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
    public Item(String n, String f, int e, int er, int sr, float ss, int dr, float ds, int pr, float ps, int wr, int ws, float w, int v) {
        name = n;
        flavorText = f;
        effect = e;
        effectRating = er;
        strengthRequirement = sr;
        strengthScaling = ss;
        dexterityRequirement = dr;
        dexterityScaling = ds;
        powerRequirement = pr;
        powerScaling = ps;
        willRequirement = wr;
        willScaling = ws;
        weight = w;
        value = v;
    }
}

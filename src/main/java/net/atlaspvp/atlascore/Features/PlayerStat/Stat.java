package net.atlaspvp.atlascore.Features.PlayerStat;

import net.atlaspvp.atlascore.AtlasCore;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.HashSet;

public class Stat {

    private final String key;

    private final double maxValue;

    private final double minValue;

    private final double baseValue;


    //modifiers
    private final HashSet<Double> flatModifiers = new HashSet<>();
    private final HashMap<Long, Double> tempFlatModifiers = new HashMap<>();

    private final HashSet<Double> multiplicatorModifiers = new HashSet<>();

    private final HashMap<Long, Double> tempMultiplicatorModifiers = new HashMap<>();


    public Stat(String id, double maxValue, double minValue, double baseValue) {
        this.key = id;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.baseValue = baseValue;
    }

    public Stat(String id, double maxValue, double minValue) {
        this.key = id;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.baseValue = 0;
    }

    public String getKey() {
        return key;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public double getValue(){
        double value = baseValue;
        cleanTemp();

        for(double i : flatModifiers){
            value += i;
        }

        for(double i : tempFlatModifiers.values()){
            value += i;
        }

        for(double i : multiplicatorModifiers){
            value *= i;
        }

        for(double i : tempMultiplicatorModifiers.values()){
            value *= i;
        }
        return value;
    }




    public void addFlatModifier(double modifier){
        flatModifiers.add(modifier);
    }

    public void addTempFlatModifier(double timeSeconds, double modifier){ //in seconds
        long time = (long) (System.currentTimeMillis() + (timeSeconds / 1000));
        tempFlatModifiers.put(time, modifier);
    }

    public void addMultiplicatorModifier(double modifier){
        multiplicatorModifiers.add(modifier);
    }

    public void addTempMultiplicatorModifier(double timeSeconds, double modifier){ //in seconds
        long time = (long) (System.currentTimeMillis() + (timeSeconds / 1000));
        tempMultiplicatorModifiers.put(time, modifier);
    }



    private void cleanTemp(){
        long time = System.currentTimeMillis();

        for(long i : tempFlatModifiers.keySet()){
            if(i < time){
                tempFlatModifiers.remove(i);
            }
        }

        for(long i : tempMultiplicatorModifiers.keySet()){
            if(i < time){
                tempMultiplicatorModifiers.remove(i);
            }
        }

    }
}

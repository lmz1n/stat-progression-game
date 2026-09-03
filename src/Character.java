/* LUIZ MIGUEL VICENTE MASSOLINI - 5719215
P10 - CHARACTER CLASS */

import java.time.LocalDate;
import java.util.*;

public class Character{
    
    private String name; 
    private HashMap<String, Stat> stats;  // String refers to stat name, don't store duplicates
    private double exp;
    private int level;
    private String[] titles; 
    private int days;
    private int T;
    private int[] thresholds; // contain amount of exp to reach a level
    private double avgCount;
    private LocalDate startDate; 
    private LocalDate endDate;

    // Constructor for character
    public Character(String n, int d, LocalDate sD) throws StatException {
        titles = new String[]{"Novice", "Intermediate", "Advanced", "Expert", "Master"};
        avgCount = 0;
        name = n;
        days = d;
        exp = 0;
        level = 1;
        startDate = sD;
        endDate = startDate.plusDays(days); // getting endDate by adding days to startDate
        stats = new HashMap<>();
        T = 5 * days;
        thresholds = createLevels();
        initStats();
    }

    // Create all stats
    private void initStats()  throws StatException {
        stats.put("KNOWLEDGE", new Stat("KNOWLEDGE", T));
        stats.put("SKILLS", new Stat("SKILLS", T));
        stats.put("KINDNESS", new Stat("KINDNESS", T));
        stats.put("GUTS", new Stat("GUTS", T));
        stats.put("CHARM", new Stat("CHARM", T));
        stats.put("WEALTH", new Stat("WEALTH", T));
        stats.put("HEALTH", new Stat("HEALTH", T));
    }

    // Adds exp based on avgCount for all stats
    // If count < avgCount, 1.2x. If count = avgCount, 1.0x. If count > avgCount, 0.8x
    public void addExp(String n){

        double mult;
        Stat stat = stats.get(n.toUpperCase());
        calculateAvgCount();
        if(stat.getCount() < avgCount) mult = 1.2;
        else if(stat.getCount() > avgCount) mult = 0.8;
        else mult = 1;
        exp += mult;
        System.out.println("\nYou gained " + mult  + " EXP for your character!");
        stat.increaseCount();
        updateLevel();
    }

    // Called when loading data for characters
    public void setExp(double amount){
        exp = amount;
        for(int i = 0; i < thresholds.length; i++) if(exp >= thresholds[i]) level = i + 2;
    }

    // Calculates mean for all stats Counts
    public void calculateAvgCount(){
        int total = 0;
        for(String key : stats.keySet()) total += stats.get(key).getCount();
        avgCount = total / 7.0;
    }

    // Updates level
    public void updateLevel(){

        int oldLevel = level;

        for(int i = 0; i < thresholds.length; i++) if(exp >= thresholds[i]) level = i + 2;
        
        // Only print when character actually leveled up
        if(level > oldLevel){
            System.out.println("\nCongratulations! You leveled up to level " + level);
            System.out.println("You are now considered \"" + getTitle() + "\" at " + name);
        }       
    } 

    // Intialize and fill thresholds with formula 
    public int[] createLevels(){
        int[] thresholds = new int[4];
        for(int i = 1; i <= 4; i++){
            thresholds[i - 1] = T * (i*i) / 30 ;
        }
        return thresholds;
    }

    // Getters 
    public LocalDate getEndDate(){
        return endDate;
    }

    public String getName(){
        return name;
    }

    public double getExp(){
        return exp;
    }


    public int getDays(){
        return days;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public String getTitle(){
        return titles[level - 1];
    }

    public Stat getStat(String key){
        return stats.get(key);
    }

    // Prints a character
    public void printCharacter(){
        System.out.println("_________________________________________________");
        System.out.println("\nCharacter: " + name + " - " + getTitle());
        System.out.println("Run: " + startDate + " to " + endDate);
        System.out.println("Level: " + level + " - Total EXP: " + String.format("%.2f", exp));
        for(String key : stats.keySet()){
            System.out.println(" - " + stats.get(key));
        }
        System.out.println("_________________________________________________");
    }

}

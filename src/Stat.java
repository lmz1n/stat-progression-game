/* LUIZ MIGUEL VICENTE MASSOLINI - 5719215
P10 - STAT CLASS + STATEXCEPTION CLASS */

public class Stat {
  
    private int count;
    private int level;
    private String name;
    private String[] titles;
    private int[] thresholds;  // contain amount of exp to reach a level

    // Constructor for a stat where n is stat name like "KNOWLEDGE" and T refers to days * 5
    public Stat(String n, int T) throws StatException{
        name = n;
        count = 0;
        level = 1;
        titles = initTitles(name);
        thresholds = createLevels(T);
    }

    // Initialize titles for specific stat name
    private String[] initTitles(String n) throws StatException{
        switch(n.toUpperCase()){
            case "KNOWLEDGE" : return new String[]{"Ignorant", "Informed", "Educated", "Insightful", "Erudite"};
            case "SKILLS" : return new String[]{"Untrained", "Practiced", "Capable", "Proficient", "Masterful"};
            case "KINDNESS" : return new String[]{"Selfish", "Considerate", "Caring", "Compassionate", "Altruistic"};
            case "GUTS" : return new String[]{"Fearful", "Hesitant", "Brave", "Daring", "Fearless"};
            case "CHARM" : return new String[]{"Awkward", "Pleasant", "Likable", "Charismatic", "Magnetic"};
            case "WEALTH" : return new String[]{"Broke", "Stable", "Comfortable", "Prosperous", "Affluent"};
            case "HEALTH" : return new String[]{"Sedentary", "Recovering", "Stable", "Fit", "Peak"};
            default : throw new StatException("Error. Could not create a stat."); // Java required this. Should never run :(
        }
    }

    // Increases count for a stat and checks if it reached next level
    public void increaseCount(){
        count++;
        System.out.println("You increased your count for " + name + "!");
        updateLevel();
    }

    // Updates level
    public void updateLevel(){

        int oldLevel = level;

        for(int i = 0; i < thresholds.length; i++) if(count >= thresholds[i]) level = i + 2;
        
        // Only print when character actually leveled up
        if(level > oldLevel){
            System.out.println("\nCongratulations! You leveled up to level " + level);
            System.out.println("You are now considered \"" + getTitle() + "\" at " + name);
        }    
    }

    // Creates levels for stat
    public int[] createLevels(int T){

        int[] thresholds = new int[4];
        int TC = T / 7;

        // In case it is health, we do 2 * Formula for other stats levels
        if(name.equalsIgnoreCase("HEALTH")){
            for(int i = 1; i <= 4; i++)
                thresholds[i - 1] = Math.round(2 * ((float) TC * (i * i) / 30));
            return thresholds;
        }

        for(int i = 1; i <= 4; i++)
            thresholds[i - 1] = Math.round((float) TC * (i * i) / 30);

        return thresholds;
    }

    // Getters
    public String getTitle(){
        return titles[level - 1];
    }

    public int getCount(){
        return count;
    }

    public void setCount(int amount){
        count = amount;
        for(int i = 0; i < thresholds.length; i++) if(count >= thresholds[i]) level = i + 2;
    }

    public String toString(){
        return name + ": " + getTitle() + " - Level " + level + " -  " + "Count: " + count;
    }

}

// I just wanted to practice/play with an inherited exception since I needed an exception for initTitles() anyways 
class StatException extends Exception{
    public StatException(String message){
        super(message);
    }
}

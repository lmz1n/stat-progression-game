/* LUIZ MIGUEL VICENTE MASSOLINI - 5719215
P10 - GAME CLASS (MAIN) */

import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Game {

    // Global variables. Used to identify date and data persistence
    public static Scanner stdin = new Scanner(System.in);
    public static Character activeCharacter = null; // Store active character only
    public static HashSet <Character> characters = new HashSet<>(); // Store all characters, no duplicates
    public static int pastCharacterCount; // Keep track of how many past characters for loading later
    public static LocalDate today = LocalDate.now(); // Today's date
    static LocalDate lastDate;  // Last logged time date
    public static int activitiesToday = 0; // Keep track of how many activities already logged
    public static boolean ans = false; // Check if health questions already answered


    public static void main(String[] args) throws StatException {
        
        // Try and catch for opening save.txt
        try{

            File file = new File("save.txt");
            Scanner fin = new Scanner(file);

            // If file is empty, we assume user never played game
            if(!fin.hasNext()){
                System.out.println("\nWelcome to Stat Progression Game!");
                System.out.println("Save file is empty..."); 
                fin.close();
                createCharacter();
                menu();
                return;
            }

            System.out.println("File loaded. Initializing...");
            // Read those here since we will need to check them when loging activities and answering questions  
            lastDate = LocalDate.parse(fin.next());
            activitiesToday = fin.nextInt();
            ans = fin.nextBoolean();
            // These will make us now whether there is an active character or not
            String first = fin.next().trim();

            // No active character, load past characters and go to menu
            if(first.equalsIgnoreCase("PAST")){
                System.out.println("Welcome back!");
                System.out.println("No active character found. Redirecting to main menu... ");
                loadPast(fin);
                menu();
            // Active character exists, load characters and go to menu
            } else if(first.equalsIgnoreCase("ACTIVE")){
                System.out.println("Welcome back!");
                System.out.println("Active character found. Loading...");
                loadActive(fin);
                menu();
            // Save file is corrupted, we assume user never played game
            } else {
                System.out.println("Welcome to Stat Progression Game!");
                System.out.println("Invalid save file...");
                createCharacter();
            }

            fin.close();

        } catch(FileNotFoundException e){
            // No save file, we assume user never played game
            System.out.println("Welcome to Stat Progression Game!");
            System.out.println("No save file found..."); 
            createCharacter();
            menu();
            return;   

        }
    }

    public static void createCharacter() throws StatException {
        System.out.println("\nLet's create a new character!");
        System.out.println("\nWhat is the new character's name?");
        String name = stdin.next();
        // Keep looping until it is an actual unique character name and 
        while(true){

            boolean exists = false;

            for(Character c : characters){
                if(c.getName().equalsIgnoreCase(name)){
                    exists = true;
                    break;
                }
            }

            if(!exists) break;

            System.out.println("Name " + name + " already exists. Try again!");
            System.out.println("\nWhat is the new character's name?");
            name = stdin.next();

        }

        System.out.println("For how many days will the character progression be active?");
        System.out.println("Note: It has to be between 7 and 365 days.");
        int days = getInt();
        while(days < 7 || days > 365){
            System.out.println("Invalid input. The number of days has to be between 1 and 365.");
            days = getInt();
        }
        // Add activeCharacter to hashSet
        activeCharacter = new Character(name, days, today);
        System.out.println("Your character was successfully created.");
        activeCharacter.printCharacter();
        characters.add(activeCharacter);
    }

    // Keeps looping until valid integer is typed
    public static int getInt() {
        while (true) {
            if (stdin.hasNextInt()) {
                int value = stdin.nextInt();
                return value;
            } else {
                System.out.print("\nInvalid input. Try again: ");
                stdin.next(); 
            }
        }
    }

    // Run's main menu logic
    public static void menu() throws StatException {
        // Ends active character run based on today compared to endDate
        if(activeCharacter != null){
            if(today.isAfter(activeCharacter.getEndDate())){
                System.out.println("\nYour active character run ended.");
                activeCharacter = null;
            }
        }
        // Resets if new day
        if (lastDate == null || !today.equals(lastDate)) {
            activitiesToday = 0;
            ans = false;
            lastDate = today;
        }
        // Menu logic
        printMenu();
        int choice = getChoice(5);
        while(choice != 5){
            if (choice == 1){
                logActivity();
            }
            else if (choice == 2){
                lookCharacter();
            }
            else if (choice == 3){
                ans = answerHealthQuestions();
            } else if (choice == 4){
                // Only run if active character does not exist
                if(activeCharacter == null) createCharacter();
                else System.out.println("There is an active character already. Try something else.");
            }

            printMenu();
            choice = getChoice(5);
        }
        save();
        System.out.println("Progress saved.");
        System.out.println("Quitting...\n");
        return;
    }
    // Loops until is gets an int between 1 and n
    public static int getChoice(int n){
        System.out.println("\nChoose an option between 1 and " + n + ":");
        int choice = getInt();
        if (choice < 1 || choice > n){
            choice = getChoice(n);
        }
        return choice;
    }
    // Prints menu
    public static void printMenu(){
        System.out.println("\nWhat are you up to?");
        System.out.println(" 1. Log an activity");
        System.out.println(" 2. Look at a character stats");
        System.out.println(" 3. Answer health questions for extra health points");
        System.out.println(" 4. Create a new active character");
        System.out.println(" 5. Save and Quit");
    }
    // Logs an activity to add count and exp for active character
    public static void logActivity() throws StatException {
        // Check if there is an active character
        if(activeCharacter == null){
            System.out.println("\nNot possible to log an activity. You have no active characters...");
            return;
        }
        // Check if activities today <= 3
        if(activitiesToday >= 3) {
            System.out.println("\nMaximum number of activities (3) already reached today.");
            System.out.println("Come back tomorrow!");
            return;
        }
        // Add the stats yielded by activity
        System.out.println("\nHow many stats did you activity yield (1-2)?");
        int choice = getChoice(2);
        for(int i = 0; i < choice; i++){
            System.out.println("\nStat #" + (i + 1));
            increaseStat();
        }
        activitiesToday++;

    }
    // Reads an int that maps to a stat and increase that stat's count
    public static void increaseStat(){
        System.out.println("\nWhich of these stats did you increase?");
        System.out.println(" 1. Knowledge");
        System.out.println(" 2. Skills");
        System.out.println(" 3. Kindness");
        System.out.println(" 4. Guts");
        System.out.println(" 5. Charm");
        System.out.println(" 6. Wealth");
        System.out.println(" 7. Health");
        int choice = getChoice(7);
        
        if(choice == 1){
            activeCharacter.addExp("Knowledge");
        } else if (choice == 2){
            activeCharacter.addExp("Skills");
        } else if (choice == 3){
            activeCharacter.addExp("Kindness");
        } else if (choice == 4){
            activeCharacter.addExp("Guts");
        } else if (choice == 5){
            activeCharacter.addExp("Charm");
        } else if (choice == 6){
            activeCharacter.addExp("Wealth");
        } else if (choice == 7){
            activeCharacter.addExp("Health");
        }
    }
    // Ask health questions once a day for extra health points
    public static boolean answerHealthQuestions() throws StatException{
        // Check if there is an active character
        if(activeCharacter == null){
            System.out.println("\nNot possible to answer health questions. You have no active characters...");
            return ans;
        }
        // Check if first time answering the questions
        if(ans){
            System.out.println("\nYou already answered the questions for today.");
            System.out.println("Come back tomorrow!");
            return true;
        }
        System.out.println("\nDid you eat well today? (y/n)");
        computeHealthAnswer();
        System.out.println("\nDid you sleep well today? (y/n)");
        computeHealthAnswer();
        return true;
    }
    // Keeps looping until answer is y or n
    public static void computeHealthAnswer(){
        String answer = stdin.next();
        while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")){
            System.out.println("\nInvalid answer. Please type only y or n:");
            answer = stdin.next();
        }
        if(answer.equalsIgnoreCase("y")) activeCharacter.addExp("Health");  
    }
    // Looks up character's data based on name
    public static void lookCharacter() throws StatException{
        // Print all character's name
        System.out.println("\nThese are all the characters available:");
        for(Character c : characters){
            if(characters.contains(c)){
                System.out.println(" - " + c.getName());
            }
        }
        // Find character logic
        System.out.println("\nEnter the name of the character you would like to look:");
        String name = stdin.next();
        for(Character c : characters){
            if(c.getName().equalsIgnoreCase(name)){
                System.out.println("\n");
                c.printCharacter();
                return;
            }
        }
        // Character name does not exist
        System.out.println("Character named " + name + " does not exist.");
        System.out.println("Redirecting to menu ...");
        menu();
    }
    // Loads active character and constructs it
    public static void loadActive(Scanner fin) throws StatException{
        String name = fin.next();
        int days = fin.nextInt();
        LocalDate startDate = LocalDate.parse(fin.next());
        activeCharacter = new Character(name, days, startDate);
        activeCharacter.setExp(fin.nextDouble());
        for(int i = 0; i < 7; i++){
            activeCharacter.getStat(fin.next()).setCount(fin.nextInt());  
        }
        characters.add(activeCharacter);
        fin.next();
        loadPast(fin);
    }
    // Loads all past characters and constructs them
    public static void loadPast(Scanner fin) throws StatException{
        pastCharacterCount = fin.nextInt();
        for(int i = 0; i < pastCharacterCount; i++){
            String name = fin.next();
            int days = fin.nextInt();
            LocalDate startDate = LocalDate.parse(fin.next());
            Character c = new Character(name, days, startDate);
            c.setExp(fin.nextDouble());
            for(int j = 0; j < 7; j++){
                c.getStat(fin.next()).setCount(fin.nextInt()); 
            }
            characters.add(c);
        }
    }
    // Saves everything to save.txt
    public static void save(){
        // Try and catch for opening save.txt for writing
        try{
            PrintWriter writer = new PrintWriter(new FileWriter ("save.txt"));
            writer.println(lastDate);
            writer.println(activitiesToday);
            writer.println(ans);
            writer.println();
            // We will save active and past characters
            saveActive(writer);
            savePast(writer);
            writer.close();
        } catch (IOException e){
            System.out.println("Error when trying to save file... ");
        }
    }
    // Saves an active character to save.txt
    public static void saveActive(PrintWriter writer){
        if(activeCharacter == null) return;
        writer.println("ACTIVE");
        writer.println(activeCharacter.getName());
        writer.println(activeCharacter.getDays());
        writer.println(activeCharacter.getStartDate());
        writer.println(activeCharacter.getExp());
        writer.println("KNOWLEDGE " + activeCharacter.getStat("KNOWLEDGE").getCount());
        writer.println("SKILLS " + activeCharacter.getStat("SKILLS").getCount());
        writer.println("KINDNESS " + activeCharacter.getStat("KINDNESS").getCount());
        writer.println("GUTS " + activeCharacter.getStat("GUTS").getCount());
        writer.println("CHARM " + activeCharacter.getStat("CHARM").getCount());
        writer.println("WEALTH " + activeCharacter.getStat("WEALTH").getCount());
        writer.println("HEALTH " + activeCharacter.getStat("HEALTH").getCount());
        writer.println();
    }
    // Saves an active character to save.txt
    public static void savePast(PrintWriter writer){
        writer.println("PAST");
        // Writes pastCharacterCount
        int count = 0;
        for(Character c : characters){
            if(c != activeCharacter) count++;
        }
        writer.println(count);
        for(Character c : characters){
            if(c == activeCharacter && activeCharacter != null) continue; // skip active character
            writer.println(c.getName());
            writer.println(c.getDays());
            writer.println(c.getStartDate());
            writer.println(c.getExp());
            writer.println("KNOWLEDGE " + c.getStat("KNOWLEDGE").getCount());
            writer.println("SKILLS " + c.getStat("SKILLS").getCount());
            writer.println("KINDNESS " + c.getStat("KINDNESS").getCount());
            writer.println("GUTS " + c.getStat("GUTS").getCount());
            writer.println("CHARM " + c.getStat("CHARM").getCount());
            writer.println("WEALTH " + c.getStat("WEALTH").getCount());
            writer.println("HEALTH " + c.getStat("HEALTH").getCount());
            writer.println();
        }
    }

}

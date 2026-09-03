# stat-progression-game
A Java-based progression game inspired by the social stat system in Persona 5. The program allows users to track real-life activities and translate them into character progression across multiple stats.

# Features
Create characters with a custom progression period measured in days.
Track up to three activities per day.
Gain progression in up to two stats from each activity.
Answer daily health questions about sleep and nutrition.
Track seven different character stats.
Level up both the overall character and individual stats.
Use a balancing system that rewards underdeveloped stats.
Save active and previous characters using a text file.

# Stats
The game tracks seven stats:

1. Knowledge
2. Skills
3. Kindness
4. Guts
5. Charm
6. Wealth
7. Health

Activities can improve up to two stats. For example:

Workout → Health +1, Guts +1
Job → Wealth +1, Skills +1
Date → Charm +1

The game also asks the player whether they slept well and ate well, with affirmative answers providing additional Health progression.

# Character Progression
Each character has five overall levels. The experience required for progression depends on the number of days selected when creating the character.

The total experience available is calculated using:

Total EXP = 5 × Days

Experience requirements increase quadratically as the character progresses through higher levels.

The game also uses a balancing system for individual stats:

 - Stats below the average receive a 1.2× EXP multiplier.
 - Stats above the average receive a 0.8× EXP multiplier.
 - Stats equal to the average receive a 1.0× multiplier.

This encourages balanced development instead of repeatedly improving only one stat.

# Stat Levels
Each stat has five progression levels and corresponding titles.

*Stat*	     *Level 1*	*Level 2*	   *Level 3*	  *Level 4*	     *Level 5*
Knowledge	    Ignorant	 Informed	    Educated	   Insightful	    Erudite
Skills	      Untrained	 Practiced	  Capable	     Proficient	    Masterful
Kindness	    Selfish	   Considerate  Caring	     Compassionate	Altruistic
Guts	        Fearful	   Hesitant	    Brave	       Daring       	Fearless
Charm	        Awkward	   Pleasant	    Likable	     Charismatic	  Magnetic
Wealth	      Broke	     Stable	      Comfortable	 Prosperous	    Affluent
Health	      Unhealthy	 Recovering	  Stable	     Fit	          Peak

| Stat      | Level 1   | Level 2     | Level 3     | Level 4       | Level 5    |
| --------- | --------- | ----------- | ----------- | ------------- | ---------- |
| Knowledge | Ignorant  | Informed    | Educated    | Insightful    | Erudite    |
| Skills    | Untrained | Practiced   | Capable     | Proficient    | Masterful  |
| Kindness  | Selfish   | Considerate | Caring      | Compassionate | Altruistic |
| Guts      | Fearful   | Hesitant    | Brave       | Daring        | Fearless   |
| Charm     | Awkward   | Pleasant    | Likable     | Charismatic   | Magnetic   |
| Wealth    | Broke     | Stable      | Comfortable | Prosperous    | Affluent   |
| Health    | Unhealthy | Recovering  | Stable      | Fit           | Peak       |


Individual stat progression is based on a separate Count system, while overall character progression uses EXP. These systems operate independently.

# Project Structure

Stat-Progression-Game/
│
├── src/
│   ├── Game.java
│   ├── Character.java
│   └── Stat.java
│
├── Save.txt
│
└── README.md


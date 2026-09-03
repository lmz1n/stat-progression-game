# stat-progression-game
A Java-based progression game inspired by the social stat system in Persona 5. The program allows users to track real-life activities and translate them into character progression across multiple stats.

# Features
- Create characters with a custom progression period measured in days.
- Track up to three activities per day.
- Gain progression in up to two stats from each activity.
- Answer daily health questions about sleep and nutrition.
- Track seven different character stats.
- Level up both the overall character and individual stats.
- Use a balancing system that rewards underdeveloped stats.
- Save active and previous characters using a text file.

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

# Classes

Game.java

- Contains the main method.
- Manages the active character.
- Stores previous characters using an ArrayList.

Character.java

- Represents a character.
- Stores stats using a HashMap<String, Stat>.
- Handles overall EXP progression.
 Uses LocalDate to manage progression dates.

Stat.java

- Stores stat information.
- Handles individual stat progression and levels.

Save.txt

- Stores character data and game state between program executions.

# Technologies Used
- Java
- Object-Oriented Programming
- ArrayList
- HashMap
- LocalDate
- File I/O

# Testing
The project was tested by checking scenarios such as:

- Creating characters with short and long progression periods.
- Modifying saved dates to test daily activity resets.
- Changing character start dates to test completed characters.
- Testing modifications to the save file while preserving its structure.

# Known Limitations
This project was developed during a limited timeframe and may still contain bugs or untested edge cases. The program was designed to handle most invalid user input, although there are limitations involving names or strings containing spaces.


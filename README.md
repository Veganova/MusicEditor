# MusicEditor

A simple Music editor in Java with a intuitive display of notes and MIDI for sound processing.

Features:
•	Designed features including: importing songs, play through, adding notes, displaying notes, a functioning piano to add notes, navigating song with arrow keys, etc.

Concepts:
•	Applied object oriented design co¬ncepts and design patterns such as the MVC in this project.
•	Basic multithreading.


### Running

Navigate to the resources and run the command.
```
java -jar file-name.txt type
java -jar hw07.jar mary-little-lamb.txt composite
```
type can be visual, midi, console, composite. 'composite' will be mix of the three before.

### Prerequisites and Installing
Built using Java 8. Uses MIDI to play music notes (System may need to be configured to support this).

Code:
Source code is located at:
```
src/cs3500/music/
```

Visuals/UI:

Uses Java Swing to display all the UI elements. The view of the MVC folders can be found under src/cs3500/music/view.

How the project looks:
```
resources\mystery-2-screenshot.png
```
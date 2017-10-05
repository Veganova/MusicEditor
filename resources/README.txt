My Design Decisions for Homework 7 - A music editor
Viraj Patil
Caden Shelman

IMusicModel 
I made the model structure similar to that of FreeCell where there is an interface model that outlines all behavior necessary from models of that type.'
My model interface - IMusicModel outlines the expected functionality
	- I can add a note, remove a note, overlay notes, append notes and print out the editor.
  - Added extra getters and setters for usage purposes by other external classes

EditorModel 
The class EditorModel implemented IMusicModel and has some extra helper methods within.
	- It has one field of type Song.
	- This houses most of the functionality.
	- This choice is very much like the Delegation pattern with all the functionality in the Song class and usage of it in the editor. This is good because it allows for further adaptations easy to implement without needing to refactor the song class.
	- Seeing as how this will interact with the controller - the inputs to the add and remove methods are note the Note itself but rather the fields of the Note. The note is assembled inside of my editor model and then passed to the song.

Song
This class takes care of most of the complex tasks (for example adding, removing or overlaying nodes). It has a Range object and other fields to keep track of important facts about the notes it houses. It keeps everything is structured order.
	- Structuring notes according to notes per beat/over time. Assuming that the editor, at some point, will be used to play music, this format of storing the notes will be very useful because the notes will be played out in chronological order.
	- It contains all the notes
	- Invariant - the notes are kept in sorted order by inserting elements accordingly
	- Decisions such as behavior when an overlap occurs when a node is added, etc. were chosen in this class.
	- Many helper methods were made for maximum abstractions and to keep code simple to read.
	- Since there are many methods, most of which are helper methods, I made sure to pay attention visibility and kept most of the methods private, only exposing the 


Notes
This is the class that represents a music note. The class contains information about the sound and the length and time at which the note is played. The fields are chosen as they are the intuitive properties of a musical note.
	- Added extra fields to accomodate for the required midi parameters. This includes volume and instrument which intrincally make sense to be tied to a note. 
  - Enumeration of pitches
	- end and start time 
	- Equality: as one who listens to music a note sounds the same regardless of length. Thus equality works the as this - looking at only those sound (pitch and octave). There is also an extra equality method to test for exactEquality between two notes.
	- Hashcode - the hashcode is basically a number that starts at 0 for a note of lowest pitch and octave and goes up by 1s for every next note. It is efficient and intuitive.

Range 
This is a helper class to make keeping track of the bounds of the song's notes and to print out the first line of the editor display as well. It allows for easy storage and deals with updates, reducing the code in the Song class.

Range iterator
As the name suggest, this is a class that is a iterator for the Range class, going from the start to end note in that class.

IMusicController
Interface that describes the behavior of the Music controllers. The controller holds responsibility for taking user inputs and affecting the view with it.
	- addView: adds a view to this controller.
	- addViews: adds a Midi view and a Gui/visual view in the form of a CompositeView to this controller.

KeyboardController
Specific controller that takes relevant keyboard key events as inputs. Takes the left and right arrows to move the current reference time forwards and backwards. Takes the space bar to toggle playing and pausing of both the visual and audio views. Takes the home and end keys to jump the current time to the beginning and end of the song.

KeyHandler
A class implementing KeyListener which sets up the relevant key events in a map with runnables. When it detects a key event, if the map has the key pressed then this class will run the associated runnable. KeyHandler is meant to be passed to a view as a KeyListener.


MockKeyHandler
A mock key handler that emulates the key handler used for the GuiViewFrame. This helps with testing as it logs information about what key event was passed to the handler and logs information about what would happen as a result of this key event. Contains a mockKeyPressed method for ease of use while testing, so a mock key event would not have to be constructed.

MockMidiDevice
A mock midi device that emulates the midi synthesizer device. This helps with testing as it produces the desired MockMidiReceiver instead of a Reciever object.
It does so in getReceiver(). Shares a log object with the rest of the mock devices

MockMidiReceiver
A mock midi device that emulates the midi receiver device. This helps with testing as it logs the desired information for later reference. It does so in send().
Shares a log object with the rest of the mock devices.

MockMidiView
A mock midi view that simulates the regular chain of events as done by the usages of MidiView.
Creates the appropriate mock midi synthesizer which in turn creats a mock receiver. The rest of the class is the same as MidiViewImpl.

IViewFactory
A view factory interface that outlines the required behavior of view factories. It produces a IMusicController Generic type over the different types.
It allows for the addition of any single view or a composite view containing a midi/audio view and a gui/visual view.
It also allows for the addition of a view to the controller. This means that it is able to add several views easily. Although the requirements for this project said that this was not necessary, this method allows for a more general controller.

KeyViewFactory
Produces a factory for a specific implementation of the IMusicControllers - KeyViewFactory. This factory implements the behavior of the IViewFactory.
It builds a KeyboardController which allows for controlling the view with every relevant keyboard key.

MusicComposition
The class representing the Builder of concrete IMusicModels. Can add notes to itself, set its own tempo, and build an IMusicModel at the end of its build cycle.


IMusicView
The interface representing an abstract music view.
Implemented by MidiViewImpl, MusicTextView, and GuiViewFrame to represent the audio, console based, and visual views respectively. Allows these views to play, pause, or play the current notes.

GuiViewFrame
The JFrame representing the window that the gui visual view pops up in.
It sets the behavior of the window and adds a ConcreteGuiViewPanel (which houses the NoteGridComponent) and KeyboardComponent directly into itself.

ConcreteGuiViewPanel
This JPanel is used as a container for the NoteGridComponent and sets the NoteGridComponent as a scrollPane, enabling scrolling vertically in the grid.

NoteGridComponent
The JComponent representing the upper half of the visual display window.
It shows the labels of time along the top, floating labels of pitch/octave along the left, and a grid containing each note and the current time of the model. It also scrolls horizontally as controlled by the arrow keys and vertically as controlled by the scroll bar along the right side of the component. Additional GridColumnComponents can be added by adding a note to the end of the song.

PitchLabelComponent
The JComponent representing the one column at the far left of the NoteGrid containing the labels of the pitch/octaves of each row. We used a separate JComponent so we could put it into the NoteGrid's gridLayout and let it float on the left side even while scrolling to the right.

GridColumnComponent
The JComponent with grid layout to represent each column of notes at a given time.
There is a GridColumnComponent for each time of the song. each GridColumnComponent is contained within a cell of the NoteGridComponent's gridLayout.

KeyboardComponent
The JComponent to show the piano keyboard in the visual view.
We used a separate component so we could have the noteGrid directly above the keyboard.
It contains 120 PianoKeyComponents to represent and draw each key on the piano keyboard.

PianoKeyComponent
The JComponent used to show each key of the piano's keyboard in the visual view.
It implements MouseListener so that this component can detect when the cursor is over it and when it is clicked by the user. We changed our implementation to using JComponents instead of graphics for easy detection of the cursor position.

PlayRest
Puts the given view into a synchronous thread.

MidiViewImpl
The view associated with playing the representation of notes through the MIDI, playing the notes aloud.

MusicTextView
The view associated with printing the representation of notes in text form to the console.
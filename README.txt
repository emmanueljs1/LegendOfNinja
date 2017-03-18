=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: emsu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. Appropriate modeling of the state of the game: the enemies in a stage are represented
  by a linked list, as are the stages in the game court. A linked list of enemies is fine to
  represent the collection of enemies because it is iterable and so removing an enemy from
  the list is easy, in addition checking the size of the list is a good way to check if the
  stage is cleared. A linked list of stages is good because the LinkedList class implements
  the Queue interface, so you can always remove the head of the linked list and have that
  be your current stage. Also, checking to see whether the player has won only means
  checking to see if our list is empty or not. If you want to erase all the stages you
  can also conveniently just clear the list.

  2. Inheritance/Subtyping: I made a new GameObj class, roughly based on the one provided
  in Mushroom of Doom. The important class, though, is my Character class, which extends
  the GameObj class. This class is extended by my Player class, and my enemy classes,
  which are RedNinja, BlueNinja, and YellowNinja. They all have a new function to check if
  the character is intersecting with another object, to walk, to jump, and to behave automatically.
  A Character object has automatic behavior, and the enemy classes have different automatic 
  behavior. A Player object also has a Weapon object, which extends GamObj, that it takes into 
  account for collisions, walking, and jumping.

  3. I/O: I am implementing I/O with high scores. I have a text file with high scores saved that
  my game updates accordingly. This is a good way of storing high scores because that way
  they can be saved through separate game instances.

  4. JUnit Testing: For JUnit testing I tested the collisions of my GameObj objects, and by
  extension my Character and enemy objects. This is an important thing to test because the state
  of my game always depends on the collisions between objects. 


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

	GameObj - a game object, has a (x, y) coordinate, two dimensions, bounds, 
	and intersection detection.
	
	Weapon - an extension of GameObj, is an object that can be drawn.
	
	Character - an extension of GameObj, it can behave automatically, walk, jump,
	has intersection detection, and draws itself.
	
	Player - an extension of Character, has a Weapon object that moves with it and
	is considered for intersection detection as well.
	
	RedNinja, BlueNinja, YellowNinja - extensions of Character, all have different
	automatic behaviors.
	
	Enemy - an enum with the possible enemies, when loading a stage with enemies this
	enum decides which enemy class all the enemies in the stage will share
	
	Stage - a JPanel that has the player, the enemies in the stage, can remove enemies
	from the stage, can check how many enemies are on the stage, and draws the background
	and all the characters on the stage.
	
	GameCourt - A JPanel that contains a linked list of stages and updates the state of the game.
	Depending on its state, it draws the current stage, the title screen, an info screen, a game 
	over screen, a victory screen, and a high score screen. It also checks if the user is trying
	to move the player or attack, and updates the state of the game accordingly.
	
	Game - a runnable object that contains a GameCourt and a JTextField for writing the name
	of the player, the name of the player is only saved into the high score file if the GameCourt
	is currently in the victory screen. This class also brings the focus back to the GameCourt
	if the user accidentally moves it to the JTextField. 
	
- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?

  I ended up not implementing the boss stage, but only because I did not have enough time
  and had to focus on implementing the main concepts.
  
  In the end instead of making GameObj and Character interfaces, I found that it was more efficient
  to make them classes that could be extended, so I could have general shared code.
  
  I also did not have health being stored in the player class, because it made more sense to
  store this with the state of the game, which was in GameCourt.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I decided to make my GameCourt change what it is drawing depending on the state of the game,
  and this made it difficult to add a JTextField because I had to make it so the game is
  constantly checking if the game court has to request focus again.
  
  I also decided to translate my graphics context so everything was being drawn with respect to the
  initial position of the player at the bottom of the stage. I ended up having to implement
  my function for jumping ( fall() ) in a different way so everything would work. It was also 
  kind of difficult to make sure the player's sword was moving in accordance with the player (not
  just being drawn in accordance with the player.) 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  Overall I believe I kept things separate, however, my GameCourt has a lot of code for drawing
  itself depending on what the state of the game is, so perhaps I could have made more Stage
  objects that represented the different screens depending on state and had these be the current
  stage if the state was not 'playing'. Then my game court would only be drawing the current stage.
  Similarly, my game court has a lot of code dedicated to calculating high scores, so this
  could go in an extension of a stage perhaps called HighScoreStage? I definitely would
  change the game so I was not translating my graphics context, because this would make it
  so the code could be more general.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
  
  Javadocs for Writer, Reader, JComponent and some of their subtypes.
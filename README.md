# TicTacToe
This project provides a full infrastructure for Students to implement a TicTacToe game that offers a console and GUI version. The intention of this infrastructure is for the students to do the following:
1. Create modeless, Swing UI dialogs as JPanes
2. Create an AI based on the Cups learning algorithm [Make Dumb Cups Learn](https://youtu.be/AAQChJC2Rug) 
3. Create an AI based on Trees
<p>
There is the opportunity to expand this project to include an AI based on MiniMax Algorithm,
but that is not currently provided.


There are several major milestones for the students to complete. These milestones don't have to 
necessarily be done in this order.
1. Create Swing UI dialogs that are triggered from the menu system.
2. Verify that a two player game works using GUI.
3. Implement an AI using Cups as the learning algorithm.
4. Implement an AI using a Game Tree.
<p>
The student can extend the GUI by replacing the JOptionPane popups with dialogs that
inherit from DialogBase. The student can also choose to add other UI elements such
as a ProgressDialog when the AI is learning, and more.


# Code to Update
Organized by package:
1. **AI Package** - The student should implement everything in this package.
2. **Console Package** - Nothing to do here. No need to make any changes.
3. **Fundamentals Package** - Students should not need to change any of the code in the fundamentals package.
However, if the student wants to upgrade functionality, additions/changes can be made.
It is currently fully operational.
    * A good update would be to update Board.toString() to print out a nicer board.
4. **GUI Package** - Students should not need to update:
	* SwingUI.java - No need to update.
	* DialogBase.java - No need to update
	* The rest of the files should be updated

## Dialogs to Create
There are several dialogs to create. These are NOT popup/modal dialogs. Instead,
These dialogs are added to the JFrame directly.

1. Pick how many players to have. One player implies an AI player is available.
2. Get the user's move. This must also display the game board.
3. Pick the AI algorithm: GameTree or Cups.

The classes to update for the dialogs are:
1. PlayerCountDialog.java
2. GetUserMoveDialog.java
3. AIAlgorithmDialog.java

## Implementing GUI Dialogs
Each dialog class has a constructor that is sufficient. If the student adds more fields,
they can be initialize in the constructor. But, the code can be left as:
```
    public AIAlgorithmDialog(Object sem) {
		super(sem);
		setUp();
	}
```
The student should then implement setUp() by creating all the components, choosing the
LayoutManager, setting up event handlers, and then assuring that the dialog is NOT visible.
<p>
It is **important** to call _notifyMain()_ in the event handler. This lets the main thread know
that it can stop waiting. The first thing it will do is ask the dialog for the result by
calling getResult. So, don't forget to call it!<p>
Each dialog needs to provide the result of the user interaction by implementing the
method, getResult().
```
    public int getResult() {
    	// result needs to be set in the event handler
		return result;
	}
```
The value returned needs to reflect what the user did.

# Design Overview
The application is run in 1 of 3 modes:
1. Console UI mode. This allows the student/class to avoid GUI altogether.
2. Dialogs mode. This allows the student to test out dialogs in a way that avoids
having to play the game. The user uses the menu system to trigger a dialog.
This is a Test Mode.
3. GUI mode. This runs the game using Swing UI.
<p>
The game play is fully implemented for the student and is done in a synchronous manner
on the main thread. This is how students have learned to code and should be natural.
However, this also means that the GUI needs to take extra steps to synchronize user
input back to the main thread. This is done with Java' keyword [synchronized](https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html) and
synchronization methods on object [wait() and notify()](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/lang/Object.html).
All the synchronization is handled for the student as long as they inherit from DialogBase
and follow the pattern of showing the dialog and then calling DialogBase::notifyMain().

## Packages
There are 4 different packages in this infrastructure.
1. tictactoe.AI - All code related to creating an AI belongs in this package.
2. tictactoe.console - This is the implemenation for the console UI.
3. tictactoe.fundamentals - All TicTacToe smarts live here. 
4. tictactoe.gui - All code related to creating SwingUI dialogs belong in here.

## UML Diagram of base classes
This is a bit outdated and mislabeled, but it is close.

![Image](UML%20TicTacToe.png)

## Synchronization of Threads
Here are the steps.
1. main thread: calls a method (e.g. SwingUI.getPlayerCount())
2. main thread: triggers a method call on EDT
3. EDT: SwingUI will show the dialog requested and then continue its
message pump to allow for user interactions.
4. main thread: waits for EDT to notifyMain
5. EDT: User completes the interaction and notifies the main thread
6. main thread: asks the dialog for the value provided by the user and
then continues to run the game
<p>
Here are the steps in Table format (Displays well only on GitHub.com)

Main Thread | EDT Thread
------------|-----------
calls a method | _pumps messages_
triggers a method call on EDT | _pumps messages_
_wait_ | SwingUI will show the dialog
_wait_ | pumps messages until user interaction
_wait_ | User provides answer
_wait_ | notify main thread
ask dialog for value | _pumps messages_
continue to run game | _pumps messages_
<p>




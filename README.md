# TicTacToe
This project provides a full infrastructure for Students to implement a TicTacToe game that offers a console and GUI version. The intention of this infrastructure is for the students to do the following:
1. Create modeless, Swing UI dialogs as JPanes
2. Create an AI based on the Cups learning algorithm [Make Dumb Cups Learn](https://youtu.be/AAQChJC2Rug) 
3. Create an AI based on Trees
<p>
There is the opportunity to expand this project to include an AI based on MiniMax Algorithm,
but that is not currently provided.
<p>
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
<p> 

There are several major milestones for the students to complete. These milestones don't have to 
necessarily be done in this order. However, it is highly recommended that a two-player version of
the game be working before starting on any AI.
1. Create Swing UI dialogs that are triggered from the menu system.
2. Verify that a two player game works using GUI.
3. Implement an AI using Cups as the learning algorithm.
4. Implement an AI using a Game Tree.
<p>
The student can extend the GUI by replacing the JOptionPane popups with dialogs that
inherit from DialogBase. The student can also choose to add other UI elements such
as a ProgressDialog when the AI is learning, and more.

## Design Overview
### UML Diagram of base classes
![Image](UML%20TicTacToe.png)
### Synchronization of Threads
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

Main Thread | EDT Thread
------------|-----------
calls a method | <pumps messages>
triggers a method call on EDT | SwingUI will show the dialog
waits for the EDT | pumps messages until user interaction
<wait> | User provides answer
ask dialog for value | <pumps messages>
continue to run game | <pumps messages>
<p>


## Dialogs to Create
There are several dialogs to create. These are NOT popup/modal dialogs. Instead,
These dialogs are added to the JFrame directly.

1. Pick how many players to have. 1 Player implies an AI version is available.
2. Get the user's move. This must display the game board and receive a click event
3. Pick the AI algorithm: GameTree or Cups.


# TicTacToe_part3_Student
Provides a full infrastructure to Students for a TicTacToe game that offers a console and GUI version.

There are several major milestones for the students to complete. These milestones don't have to 
necessarily be done in this order. However, it is highly recommended that a two-player version of
the game be working before starting on any AI.
1. Create Swing UI dialogs that are triggered from the menu system.
2. Create a working game of TicTacToe using the console as the IO.
3. Create a working game of TicTacToe using the GUI as the IO.
4. Implement an AI using Cups as the learning algorithm.
5. Implement an AI using a fully complete Game Tree.
6. Implement an AI using a minimax algorithm.

## Design Overview
### UML Diagram of base classes
![image]
### Synchronization of Threads

## Dialogs to Create
There are several dialogs to create. These are NOT popup/modal dialogs. Instead,
These dialogs are overlayed into the Frame directly.

1. Pick how many players to have. 1 Player implies an AI version is available.
2. Get the user's move. This must display the game board and receive a click event
3. Pick the AI algorithm: GameTree or Cups or MiniMax.
or some other event that receives the input from the human player.

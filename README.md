# Java Swing Tic-Tac-Toe Game

A simple graphical Tic-Tac-Toe game implemented in Java using Swing for the user interface and sockets for multiplayer functionality.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [Game Rules](#game-rules)
- [Contributing](#contributing)
- [License](#license)

## Overview

This project is a multiplayer Tic-Tac-Toe game with a graphical user interface (GUI) built using Java Swing. Players can connect over a network and enjoy a game of Tic-Tac-Toe.

## Features

- **Graphical User Interface (GUI):** Enjoy the game with a visually appealing Swing-based interface.
- **Multiplayer:** Play against a friend over a network.
- **Java Sockets:** Utilizes Java sockets for network communication.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed on your machine.

## How to Run

1. Clone the repository.

2. Compile the Java files:

    ```bash
   cd src && javac com/soe/tictactoe/*.java
   ```

3. Run the server:

    ```bash
    java com.soe.tictactoe.GameServer
    ```

4. Run two instances of the client for Player 1 and Player 2, each by the command:

    ```bash
    java com.soe.tictactoe.GameClient
    ```

## Game Rules

- The game follows the standard Tic-Tac-Toe rules.
- Players take turns to make a move by clicking on the desired cell in the GUI.
- The game announces the winner or declares a draw when the game is over.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your improvements.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

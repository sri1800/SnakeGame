package com.sri;

// Importing Swing library for GUI components
import javax.swing.*;

public class Snake {

	public static void main(String[] args) {
		
		// Define the width and height of the game board
		int boardWidth = 600;
		int boardHeight = boardWidth; // Making it a square board

		// Create a new JFrame (main game window) with the title "Snake"
		JFrame frame = new JFrame("Snake");

		// Make the frame visible
		frame.setVisible(true);

		// Set the initial size of the frame (600x600)
		frame.setSize(boardWidth, boardHeight);

		// Center the window on the screen
		frame.setLocationRelativeTo(null);

		// Prevent window resizing to maintain consistent game dimensions
		frame.setResizable(false);

		// Ensure the application exits when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create an instance of the Game panel (which contains the snake game)
		Game game = new Game(boardWidth, boardHeight);

		// Add the game panel to the frame
		frame.add(game);

		// Resize the window so the frame fits the preferred size of the Game panel
		// (useful because the panel may have padding/margins)
		frame.pack();

		// Request keyboard focus for the game panel so it can receive key events
		game.requestFocus();
	}
}

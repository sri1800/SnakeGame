package com.sri;

// Importing necessary libraries for GUI and event handling
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {

	// Game board dimensions and tile size
	int boardWidth;
	int boardHeight;
	int tileSize = 25; // Each tile is 25x25 pixels

	// Snake's head and body
	Tile snakeHead;
	ArrayList<Tile> snakeBody;

	// Food tile
	Tile food;

	// For generating random food positions
	Random random;

	// Timer to run the game loop periodically
	Timer gameloop;

	// Velocity of snake (vx for horizontal, vy for vertical)
	int vx;
	int vy;

	// Flag to determine if the game is over
	boolean gameOver = false;
	
	int lives = 3; // Number of lives
	
	int totalScore = 0; // Final score across all lives


	// Constructor - initializes the game
	public Game(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;

		// Set preferred panel size and background color
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);

		// Add key listener to capture keyboard input
		addKeyListener(this);
		setFocusable(true); // Allow this component to receive key events

		// Initialize snake head at position (5, 5)
		snakeHead = new Tile(5, 5);

		// Initialize empty body
		snakeBody = new ArrayList<Tile>();

		// Initialize food tile (temporary position, will be randomized)
		food = new Tile(10, 10);

		// Random number generator for food placement
		random = new Random();

		// Place food at random location not occupied by the snake
		placeFood();

		// Start with no movement
		vx = 0;
		vy = 0;

		// Start game loop timer with 100ms delay
		gameloop = new Timer(100, this);
		gameloop.start();

	}

	// Called automatically when the panel needs to be redrawn
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Clear previous frame
		draw(g); // Custom drawing
	}

	// Draw snake, food, and score
	public void draw(Graphics g) {
		// Draw snake head
		g.setColor(Color.green);
		g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

		// Draw food
		g.setColor(Color.red);
		g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

		// Draw each body part
		for (int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			g.setColor(Color.yellow);
			g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
		}

		// Display score or Game Over
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.setColor(Color.white);
		g.drawString("Score: " + (totalScore + snakeBody.size()), tileSize - 16, tileSize); // Live + previous

		// Show lives remaining
		g.drawString("Lives: " + lives, tileSize - 16, tileSize * 2);

		if (gameOver) {
		    g.setColor(Color.red);
		    g.drawString("Game Over!", tileSize - 16, tileSize * 3);
		    g.drawString("Final Score: " + (totalScore + snakeBody.size()), tileSize - 16, tileSize * 4);
		}

	}

	// Randomly place food in a position not occupied by the snake
	public void placeFood() {
		while (true) {
			// Random tile position
			food.x = random.nextInt(boardWidth / tileSize);
			food.y = random.nextInt(boardHeight / tileSize);

			// Check for collision with snake
			boolean onSnake = false;
			if (collision(food, snakeHead)) {
				onSnake = true;
			}
			for (Tile part : snakeBody) {
				if (collision(food, part)) {
					onSnake = true;
					break;
				}
			}
			// Place food if position is free
			if (!onSnake) break;
		}
	}

	// Check whether two tiles overlap
	public boolean collision(Tile a, Tile b) {
		return a.x == b.x && a.y == b.y;
	}

	// Move the snake forward, check for food, collisions, and update body
	public void move() {
		// Check if snake eats food
		if (collision(snakeHead, food)) {
			// Grow body at food position
			snakeBody.add(new Tile(food.x, food.y));
			// Re-place food
			placeFood();
		}

		// Move snake body from tail to head
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if (i == 0) {
				// First body part takes head's previous position
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} else {
				// Other parts follow the one before them
				Tile prevPart = snakeBody.get(i - 1);
				snakePart.x = prevPart.x;
				snakePart.y = prevPart.y;
			}
		}

		// Move the snake head by adding velocity
		snakeHead.x += vx;
		snakeHead.y += vy;

		boolean collided = false;

		// Check for collision with body
		for (Tile part : snakeBody) {
		    if (collision(snakeHead, part)) {
		        collided = true;
		        break;
		    }
		}

		// Check for collision with walls
		if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
		    snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
		    collided = true;
		}

		// If a collision happened, reduce a life,Add score
		if (collided) {
		    totalScore += snakeBody.size(); // Add current life score to total
		    lives--;
		    if (lives <= 0) {
		        gameOver = true;
		    } else {
		        resetPosition(); // Reset snake position and body for next life
		    }
		}


	}
	
	// Reset snake to initial position
	public void resetPosition() {
	    snakeHead = new Tile(5, 5); // Reset head to center
	    vx = 0;
	    vy = 0;
	    snakeBody.clear(); // Clear body
	    placeFood();       // Place new food
	}



	// Triggered every 100ms by the timer: update and repaint
	@Override
	public void actionPerformed(ActionEvent e) {
		move(); // Update logic
		repaint(); // Redraw screen
		if (gameOver) {
			gameloop.stop(); // Stop game loop on Game Over
		}
	}

	// Keyboard controls to change direction
	@Override
	public void keyPressed(KeyEvent e) {
		// Prevent reverse direction
		if (e.getKeyCode() == KeyEvent.VK_UP && vy != 1) {
			vx = 0;
			vy = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && vy != -1) {
			vx = 0;
			vy = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && vx != 1) {
			vx = -1;
			vy = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && vx != -1) {
			vx = 1;
			vy = 0;
		}
	}

	// Not used but required by KeyListener
	@Override
	public void keyTyped(KeyEvent e) {}

	// Not used but required by KeyListener
	@Override
	public void keyReleased(KeyEvent e) {}

}

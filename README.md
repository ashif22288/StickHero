# Stick Hero (JavaFX Implementation)

A high-fidelity recreation of the popular "Stick Hero" game, built using **JavaFX**. This project demonstrates the application of Object-Oriented Programming (OOP) principles, implementation of classic Design Patterns, and automated testing in a graphical environment.

## ## Game Overview

The player controls a hero who must cross a series of hills by creating ladders of the perfect length. 

* **Mechanics:** Press and hold the **Spacebar** to grow a ladder. Release it to drop the ladder and walk across.
* **Challenges:** If the ladder is too short or too long, the hero falls.
* **Bonus Actions:** Players can flip the hero upside down while walking to collect rewards or avoid obstacles, adding a layer of timing and skill.

## ## Features

* **Dynamic Terrain:** Hills are procedurally generated with varying widths and distances.
* **Smooth Animations:** Utilizes `TranslateTransition` and `FadeTransition` for fluid movement and UI effects.
* **Score Management:** Tracks current session scores and saves high scores.
* **State Management:** Features a clean flow from the Main Menu to Gameplay and finally to the Game Over/Statistics screen.

## ## Design Patterns Applied

To ensure the codebase is maintainable and scalable, the following patterns were used:

1.  **Singleton Pattern:** Implemented in the `HelloApplication` class to ensure a single, consistent instance of the application and stage management.
2.  **Factory Pattern:** Used for hill generation. A factory logic handles the creation of `Rectangle` objects, allowing for decoupled generation of game obstacles with varying properties.
3.  **MVC (Model-View-Controller):** Strict separation of concerns using FXML for the View and `HelloController` for the game logic.

## ## Technical Stack

* **Language:** Java 17+
* **Framework:** JavaFX
* **Build Tool:** Maven
* **Testing:** JUnit & Mockito

## ## Getting Started

### ### Prerequisites
* JDK 17 or higher
* Maven installed (or use the integrated Maven wrapper in your IDE)

### ### Running the Game
1.  Navigate to the `HelloApplication.java` file.
2.  Run the `start` method.
3.  **Controls:**
    * **Play/Exit:** Main menu buttons.
    * **Spacebar:** Hold to grow the ladder.
    * **Up/Down Arrow Keys:** Flip the hero while crossing.

## ## Testing
The project includes unit tests using **Mockito** to verify the application lifecycle and ensure the FXML loaders and Stages initialize correctly without side effects.

```bash
mvn test
```

## ## Author

* **Md Ashif**
    * *ashif22288@iiitd.ac.in*
    * *Final-year B.Tech, Computer Science and Engineering*
    * *Indraprastha Institute of Information Technology Delhi (IIIT Delhi)*

---

*This project was developed as part of a Computer Science curriculum, focusing on Software Engineering best practices and Design Patterns.*# StickHero

[README.md](https://github.com/user-attachments/files/28628465/README.md)
# AWACS Controller

An AWACS-inspired Java missile defense game built with Swing. Hostile contacts appear on a radar display and move toward your base. Click incoming enemies to launch interceptor missiles from defense batteries, survive escalating waves, and protect the base.

## Features

- Radar-style display with animated sweep line
- Mouse-click interceptor launches
- Missiles launch from random defense batteries
- Random hit/miss chance for interceptors
- Wave-based difficulty scaling
- Increasing enemy speed and contact count across waves
- Score, kill count, base health, and wave HUD
- Sound effects for radar sweeps, launches, hits, misses, base damage, and victory
- Object-oriented structure with separate classes for the world, controller, entities, waves, scoring, and audio

## How to Set Up in Eclipse

1. Unzip the project folder.
2. Open Eclipse.
3. Create a new Java project.
4. Open the unzipped project folder.
5. Drag all `.java` files into the Eclipse project's `src` folder.
6. Drag the `sounds` folder into the main project level, not inside `src`.
7. If using sprites, drag the `sprites` folder into the main project level, not inside `src`.
8. Refresh the project in Eclipse if the files do not appear immediately.

The project structure should look similar to this:

```text
ProjectName/
├── src/
│   ├── GameApp.java
│   ├── GameFrame.java
│   ├── GamePanel.java
│   ├── GameWorld.java
│   └── other Java files
├── sounds/
└── sprites/   optional, if sprite graphics are enabled
```

## How to Run

Run:

```text
GameApp.java
```

When the game starts, enter any callsign. The system will use that callsign in the interface. If no callsign is entered, the default callsign is `HOMEPLATE`.

## How to Play

Your goal is to defend the base through all enemy waves.

- Incoming hostile contacts appear on the radar.
- Click an enemy contact to launch an interceptor missile.
- Only one missile can track a target at a time, so clicking the same enemy repeatedly will not spam interceptors.
- Interceptors have a random hit chance, so some missiles may miss.
- If an enemy reaches the base, the base loses health.
- The base starts with 100 HP.
- The game ends when the base is destroyed or when all waves are cleared.

## Sound Effects

Turn your volume on for the full experience. Warning: there are funny sounds.

- Radar "bing": radar sweep completes a circle
- Launch sound: interceptor missile launched
- Explosion: interceptor hit(big boom)
- Miss sound: interceptor missed(yell at Enrique)
- Alert sound: base takes damage(Euh oh)
- Victory sound: all waves cleared(Very Nice!)
- Loss sound: one wave lost(It is the end)

## Radar Note

The radar sweep is visual and atmospheric. Since the game represents an AESA-style radar system, targets appear immediately instead of only appearing when the sweep line passes over them.

## Technical Highlights

This project uses several core Java and game programming concepts:

- Swing `JFrame` and `JPanel` for the game window and custom drawing
- `Timer`-based game loop for animation
- `Graphics2D` for radar, HUD, entities, and effects
- Trigonometry with `Math.sin` and `Math.cos` for the radar sweep
- Vector movement for missiles and enemy contacts
- Mouse input for target selection
- Randomized missile hit chance
- Wave progression and difficulty scaling
- Object-oriented inheritance through `GameEntity`, `Aircraft`, `EnemyUnit`, `FriendlyUnit`, and `Missile`

## Main Classes

- `GameApp` starts the program.
- `GameFrame` creates the window.
- `GamePanel` draws the game and handles timer/mouse events.
- `GameWorld` stores and updates game state.
- `GameController` handles player clicks.
- `WaveManager` controls enemy spawning and wave progression.
- `ScoreManager` tracks score and kills.
- `SoundManager` loads and plays sound effects.
- `GameEntity` provides shared position, movement, and collision behavior.
- `EnemyUnit`, `FriendlyUnit`, `Missile`, and `Base` represent the main gameplay objects.

## Objective

Defend the base, manage incoming threats, and survive all waves.

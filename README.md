# fatbird

Playground to learn Korge.

This doesn't do much right now. You can control your bird as follows:

* Left arrow: run left
* Right arrow: run right
* Space: start flapping. Press Space a lot.
* Space + arrow key: jump

Some sort of fruit things fall from the sky. They don't do anything.

## Build & Run

In a JVM:

```
./gradlew runJvm
```

Web:

```
./gradlew runJs
```

Prepare for web deployment:

```
./gradlew jsBrowserDistribution
```

## TODO

- Change favicon.ico to fatbird.
- Change app name (page name) to fatbird.

### Ezra's and Waylon's Guidance

- Make crab enemies.
- Make an animation where if you touch a crab it claws your beak.
- Add a point counter for collecting the fruit.
- Add fun sounds to the game.
- Fix the 'standing on nothing' problem.
- Make a more interesting level layout.
- Add a background.
- Show a HUD - points, menu settings, etc.
- Figure out a mobile app interface. Touch controls, not just keyboard.
- Collecting fruit spawns coins. 
- Coins can be spent at the shop for power-ups.
- Boss is King Crab.
- You can get a shovel and dig the blocks.
- Level creator


### Product

- Stick with Korge? Or move to Unity?
- Persist state? Saved games. Do it cheaply. Ezra suggested how scratch gives you a hash that can be used to restore state.
- 

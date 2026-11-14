# Kingdom Management Game (Java)

A console-based kingdom management game developed as my first Java programming assignment at university.

> **Project background:** The assignment was given by my instructor. This repository documents my original coursework implementation and will also track improvements I make as I continue learning Java and object-oriented programming.

## Overview

The player manages a medieval kingdom over a maximum of **10 turns**, balancing gold, food, public satisfaction, security, and military strength. Three city types have different production characteristics:

- **Trade city** (`Ticaret_sehri`): focuses on gold production.
- **Agricultural city** (`Tarim_sehri`): focuses on food production.
- **Industrial city** (`Sanayi_sehri`): provides more balanced production.

During a turn, the player can view cities, invest gold in a city, adjust a city's tax rate, or end the turn. At the end of each turn, cities produce resources and random raids may occur.

## Java concepts practiced

- Classes and objects
- Constructors and encapsulation
- Inheritance (`extends`)
- Method overriding and polymorphism
- Arrays, loops, and conditional statements
- Console input with `Scanner`
- Random events using `Random`

## Project structure

```text
src/
├── Main.java               # Application entry point
├── Krallık.java             # Kingdom state and turn management
├── Şehir.java               # Base city class
├── Tarim_sehri.java         # Agricultural city
├── Ticaret_sehri.java       # Trade city
└── Sanayi_sehri.java        # Industrial city
```

## How to run

You need a Java Development Kit (JDK). The original source was verified to compile and run with **JDK 21**.

From the repository's root directory, run:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

On Windows PowerShell, use:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem src/*.java).FullName
java -cp out Main
```

The source code contains Turkish characters in some Java class and file names. Keep the filenames unchanged and use UTF-8 encoding.

## Current status — Version 1.0

This release preserves my original university assignment implementation. Some input handling and gameplay interactions can be improved in future versions; for example, non-numeric console input is not handled gracefully.

## Planned improvements (Version 2.0)

- More robust input validation
- Multiple management actions within a turn
- Clearer console menus and status summaries
- Save/load game progress using file I/O
- More varied random events and gameplay balancing
- Unit tests and refactoring

These are **planned features**, not features of Version 1.0.

## Academic transparency

This project originated from a university assignment provided by my instructor. The original solution is preserved as a learning milestone. Future enhancements will be documented in version history.

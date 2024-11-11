# Poker Hand Monte Carlo Simulation (Multithreaded)

This project implements a Monte Carlo simulation for evaluating poker hands using multiple threads to speed up the simulation process.
The simulation estimates the probabilities of winning a poker hand by running numerous trials of random poker hands,
and it is optimized for performance using Java's multithreading capabilities.

## Features

- **Monte Carlo Simulation:** Simulates a large number of poker hands to estimate the winning probability of a given hand.
- **Multithreading:** Utilizes Java's multithreading to speed up the simulation, dividing the work across multiple threads.
- **Poker Hands Evaluation:** Implements the logic to evaluate the strength of poker hands.
- **Configurable Trials:** Allows you to set the number of trials for the simulation.

## Example

```Your hand:
A of Spades
Q of Spades

How many simulations?
1234567

Results:
Player won 156,841 out of 1,234,567 simulations.
Win percentage: 12.70%
Execution time: 6688.19ms
Speed: 184589.14 simulations/second
```

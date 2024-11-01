# Poker Monte Carlo Simulation

This project is a Monte Carlo simulation for evaluating poker hand winning probabilities. 
The program calculates the likelihood of winning for a given hand, simulating poker rounds against 4 opponents, considering that no player folds and that there are 5 cards on the table.

## Features

- Multithreaded simulation to maximize performance.
- Configurable number of simulations and threads based on CPU cores.
- Simulates multiple poker rounds to determine hand win probability.

## Example Output

**Player's Hand**  
`8 of Diamonds`  
`9 of Diamonds`

**Simulation Settings**  
`Number of Simulations`: 10,000,000

**Results**  
- **Player won**: `1,269,811` out of `10,000,000` simulations  
- **Win percentage**: `12.70%`  
- **Execution time**: `57706.86 ms`  
- **Speed**: `173,289.61 simulations/second`

  

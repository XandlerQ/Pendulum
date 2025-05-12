# Pendulum

**Pendulum** is a Java + Processing application that numerically simulates the motion of a **double compound pendulum** system.

## Description

The system consists of two uniform rods:

- The **first rod** is pivotally attached to a fixed support.
- The **second rod** is connected to the free end of the first rod, and its opposite end is free (not fixed).

This configuration results in a chaotic, nonlinear dynamical system, ideal for numerical analysis.

## Numerical Methods

The solver supports the following time integration methods:

- **Euler Method**
- **Runge-Kutta 4th Order Method (RK4)**

## System Formulation

The simulation is based on the Lagrangian formulation of the double pendulum:

### System Illustration

![System Illustration](https://github.com/user-attachments/assets/a9c818f0-df2d-41ff-9d13-14cf935db71b)

### Lagrangian Equations

![Lagrangian 1](https://github.com/user-attachments/assets/938b5a65-71f5-41fb-b807-d319e31d3dfb)  
![Lagrangian 2](https://github.com/user-attachments/assets/35b6598a-9abb-4d12-8b29-876c22ac5c44)

## UML Scheme

![UML Diagram](https://github.com/user-attachments/assets/6a18123c-3565-472b-8626-1c1f6c76ede0)

## Screenshot

![Simulation Screenshot](https://github.com/user-attachments/assets/f1f41575-4445-4cf1-ac97-c1185a6beb0d)

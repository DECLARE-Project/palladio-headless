# Palladio LQNS Headless

Run Palladio LQNS headless and integrate it into your application.

> **Attention:** This project is a work in progress and as such, the API is unstable and may change anytime.

## Installation

- Follow the [installation steps](https://sdqweb.ipd.kit.edu/wiki/PCM2LQN) described in the Palladio Wiki. It is important that you have a working installation of the PCM which includes the PCMSolver and the PCM2LQN tool. Additionally, for PCM2LQN to work, you will need the [LQNSolvers](http://www.sce.carleton.ca/rads/lqns/) executables on your PATH.
- Check out this project from source and open it in your favorite IDE. Add the `C:\...\Palladio\plugins` folder to the classpath of this project.
- Copy the configuration file in `src/main/resources/config.properties.dist` to `src/main/resources/config.properties` and adjust its values accordingly.

## Running

The `de.fabiankeller.palladio.RunLQNS` class offers a main method that reads the `config.properties` file and invokes the PCM2LQN solver.

The actual output of the tool will appear upon successful completion in your defined `Output_Path`.

## ToDo

- Design a nice API to call LQNS. Currently the project only provides a `main(...)` method within `de.fabiankeller.palladio.RunLQNS`.
- Extract LQNS results. Ideally, results are mapped to the PCM model. Maybe this is already part of Palladio?

## Wiki

For more information [check out the wiki](https://github.com/SQuAT-Team/palladio-lqns-headless/wiki) of this project. It contains behind the scenes information how the headless approach works.

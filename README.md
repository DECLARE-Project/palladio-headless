# Palladio LQNS Headless

Run Palladio LQNS headless (i.e. without Eclipse) and integrate it into your application.

> **Attention:** This project is a work in progress and as such, the API is unstable and may change anytime. For recent changes refer to the change log.

## Installation

**Prerequisites:**

- Follow the [installation steps](https://sdqweb.ipd.kit.edu/wiki/PCM2LQN) described in the Palladio Wiki. It is important that you have a working installation of the PCM which includes the PCMSolver and the PCM2LQN tool. 
- Additionally, for PCM2LQN to work, you will need the [LQNSolvers](http://www.sce.carleton.ca/rads/lqns/) executables on your PATH.

**Installation Steps:**

- Check out this project from source.
- Hop on a shell and run `./buildLocalPalladioMavenRepo.sh C:/<pathTo>/Palladio`. That builds a local maven repository in `./pathing/.m2` containing all required Palladio JAR files.
- Copy the configuration file in `src/main/resources/config.properties.dist` to `src/main/resources/config.properties` and adjust its values accordingly.

**Export as Library:**

You may export the palladio-headless project as standalone JAR library including all required dependencies by running `mvn clean package`. The JAR files are then created in `./target`.

## Running

The `de.fabiankeller.palladio.RunLQNS` class offers a main method that reads the `config.properties` file and invokes the PCM2LQN solver.

The actual output of the tool will appear upon successful completion in your defined `Output_Path`.

## ToDo

- ~~Design a nice API to call LQNS. Currently the project only provides a `main(...)` method within `de.fabiankeller.palladio.RunLQNS`.~~
- ~~Provide a builder API to construct PCM models.~~
- Extract LQNS results. Ideally, results are mapped to the PCM model. Maybe this is already part of Palladio?

## Wiki

For more information [check out the wiki](https://github.com/SQuAT-Team/palladio-lqns-headless/wiki) of this project. It contains behind the scenes information how the headless approach works.


## [Change Log](CHANGELOG.md)

See all changes made to this project in the [change log](CHANGELOG.md). This project follows [semantic versioning](http://semver.org/).


## [License](LICENSE)

This project is licensed under the terms of the [MIT license](LICENSE).
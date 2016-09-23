# Palladio Headless

[![GitHub tag](https://img.shields.io/github/tag/SQuAT-Team/palladio-headless.svg?maxAge=3600)](https://github.com/SQuAT-Team/palladio-headless/releases)
[![Travis](https://img.shields.io/travis/SQuAT-Team/palladio-headless.svg?maxAge=3600)](https://travis-ci.org/SQuAT-Team/palladio-headless)
[![Coverage](https://img.shields.io/codecov/c/github/SQuAT-Team/palladio-headless.svg?maxAge=3600)](https://codecov.io/gh/SQuAT-Team/palladio-headless)
[![license](https://img.shields.io/github/license/SQuAT-Team/palladio-headless.svg?maxAge=3600)](LICENSE)

Run Palladio headless (i.e. without Eclipse) and integrate it into your application. The project currently features:

- Running the Palladio LQNS solver for a `PcmInstance`
- Building `PcmInstances` on the fly with a fluent builder API.
- Programmatically extracting the performance analysis results of the LQNS analysis.

> **Attention:** This project is a work in progress and as such, the API is unstable and may change anytime. For recent changes refer to the change log.


## Installation

#### Prerequisites:

- For the Palladio PCM2LQN to work, you will need the [LQNSolvers](http://www.sce.carleton.ca/rads/lqns/) executables on your PATH.

#### Installation Steps:

- Check out this project from source.
- Hop on a shell and run `mvn clean install`. You may also do this from your favorite IDE.
- Copy the configuration file in `palladio-analysis/src/main/resources/config.properties.dist` to `palladio-analysis/src/main/resources/config.properties` and adjust its values accordingly.

#### Export as Library:

You may export the palladio-headless project as standalone JAR library including all required dependencies by running `mvn clean package`. The JAR files are then created in the respective `target` folders, i.e. `palladio-builder/target/*.jar`.


## Running

The `de.fabiankeller.palladio.RunLQNS` class offers a main method that reads the `config.properties` file and invokes the PCM2LQN solver.

The actual output of the tool will appear upon successful completion in your defined `Output_Path`.


## Result Extraction

The project is capable of programmatically extracting the performance prediction results of the LQNS analysis. As references to the PCM model elements are lost on the transformation to the LQNS solver the solution relies on adding trace information to the names of PCM `NamedElement`s. The trace information simply is a random UUID. Let's take advantage of the tracing capabilities:

```java
// 1. Create a PCM instance to analyze
final PCMInstance instance = new SimpleTacticsProvider().provide();

// 2. Add the UUID trace information
final PcmModelTrace trace = PcmModelTrace.trace(instance);

// 3. Run the actual analysis
final Pcm2LqnRunner runner = new Pcm2LqnRunner(new Pcm2LqnAnalysisConfig(this.runnerConfig));
runner.analyze(instance);

// 4. Parse the results file
final Pcm2LqnResult results = new Pcm2LqnResult(instance);
Pcm2LqnResultsParser.parse(trace, results, this.resultsFile);

// [optional] 4.1 Remove the trace information to restore the exact same PCMInstance
PcmModelTrace.untrace(instance);

// 5. Read the results
RepositoryComponent component = instance.getRepositories().get(0).getComponents__Repository().get(0);
results.getResults(component);
```

The retrieved result objects are mapped to a `NamedElement` of the PCM instance. The result objects are strongly typed with the help of appropriate value objects to retain their semantic meaning. For example, there are `ServiceTime`, `Throughout` and `Utilization` objects to store the analysis results. Have a look at `de.fabiankeller.palladio.analysis.result` to see what else is available.



## Builder API

The project features a fluent Java builder API to build `PcmInstance`s on the fly. Here is a preview of how the builder API looks like. You may see the full example by [viewing the SimpleTacticsProvider](https://github.com/SQuAT-Team/palladio-headless/blob/master/palladio-analysis/src/main/java/de/fabiankeller/palladio/analysis/provider/SimpleTacticsProvider.java) class.

```java
    final PcmBuilder builder = new PcmBuilder();
    
    final InterfaceBuilder i_businessTrip = builder.repository().withInterface("IBusiness Trip");
    final SignatureBuilder s_plan = i_businessTrip.createOperation("plan")
            .withParameter("isBook", ParameterType.BOOL)
            .withParameter("isBank", ParameterType.BOOL);
    
    // ...
    
    final ComponentBuilder c_businessTripMgmt = builder.repository().withComponent("BusinessTripMgmt")
            .provides(i_businessTrip)
            .requires(i_booking)
            .requires(i_employeePayment)
            .withServiceEffectSpecification(s_plan)
                .start()
                .internalAction("action")
                    .withCpuDemand("4")
                .end()
                .branch("aName")
                    .createBranch("aName", "isBook.VALUE")
                        .start()
                        .externalCall(s_book)
                            .withInputVariableUsage("isBank", "isBank.VALUE")
                        .end()
                        .stop()
                    .end()
                    .createBranch("aName", "NOT isBook.VALUE")
                        .start()
                        .externalCall(s_reimburse).end()
                        .stop()
                    .end()
                .end()
                .stop()
            .end();
            
    // ...
```

**Limitations:** The builder does not yet support all PCM features. You may have a look at [issue #7](https://github.com/SQuAT-Team/palladio-lqns-headless/issues/7) to see what is still missing.


## Roadmap

- Support more PCM features for the builder API.
- Support different analyses.


## Wiki

For more information [check out the wiki](https://github.com/SQuAT-Team/palladio-lqns-headless/wiki) of this project. It contains behind the scenes information how the headless approach works.


## Release

To release a new version, run the following commands:

```sh
mvn release:prepare -DautoVersionSubmodules=true
mvn release:perform -Darguments="-Dmaven.javadoc.skip=true"
```


## Contributing

Open a PR :-)


## [Change Log](CHANGELOG.md)

See all changes made to this project in the [change log](CHANGELOG.md). This project follows [semantic versioning](http://semver.org/).


## [License](LICENSE)

This project is licensed under the terms of the [MIT license](LICENSE).


---

Project created and maintained by [Fabian Keller](http://www.fabian-keller.de) in the scope of his master's thesis.
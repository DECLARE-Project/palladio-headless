# Palladio Headless

[![GitHub tag](https://img.shields.io/github/tag/SQuAT-Team/palladio-headless.svg?maxAge=30)](https://github.com/SQuAT-Team/palladio-headless/releases)
[![Travis](https://img.shields.io/travis/SQuAT-Team/palladio-headless.svg?maxAge=30)](https://travis-ci.org/SQuAT-Team/palladio-headless)
[![Coverage](https://img.shields.io/codecov/c/github/SQuAT-Team/palladio-headless.svg?maxAge=30)](https://codecov.io/gh/SQuAT-Team/palladio-headless)
[![license](https://img.shields.io/github/license/SQuAT-Team/palladio-headless.svg?maxAge=30)](LICENSE)

Run the Palladio performance analyses headless (i.e. without Eclipse) and integrate them into your application. This project currently features:

- Running the Palladio LQNS solver for a `PcmInstance`.
- Building `PcmInstances` on the fly with a fluent builder API.
- Programmatically extracting the performance analysis results of the LQNS analysis.
- A nice and strongly-typed API for model-agnostic performance analysis results.

This project implements a performance analysis based on the `PerformanceAnalyzer` abstraction of [FaKeller/performance](https://github.com/FaKeller/performance).

> **Attention:** This project is a work in progress and as such, the API is unstable and may change anytime. For recent changes refer to the change log.


## Installation

#### Prerequisites:

- For the Palladio PCM2LQN to work, you will need the [LQNSolvers](http://www.sce.carleton.ca/rads/lqns/) executables on your PATH.

#### Installation Steps:

- Check out this project from source.
- Hop on a shell and run `mvn clean install`. You may also do this from your favorite IDE.

#### Export as Library:

You may export the palladio-headless project as standalone JAR library including all required dependencies by running `mvn clean package`. The JAR files are then created in the respective `target` folders, i.e. `palladio-builder/target/*.jar`.


## Running

The `de.fakeller.palladio.RunLqnsWithBuilder` class offers a main method that invokes the PCM2LQN solver and prints the performance analysis results to the logger for demonstration purposes.

By default, the actual output of the LQNS CLI tool will be stored in a temporary directory. A custom output path can be set by setting the appropriate value in the `PcmLqnsAnalyzerConfig`.


## Result Extraction

The project automatically extracts the performance prediction results of the LQNS analysis. As references to the PCM model elements are lost on the transformation to the LQNS solver the solution relies on adding trace information to the names of PCM `NamedElement`s. The trace information simply is a random UUID. 

```java
// 1. Create a PCM instance to analyze
final PCMInstance instance = new SimpleTacticsProvider().provide();

// 2. Prepare the actual analysis
final PcmLqnsAnalyzer analyzer = new PcmLqnsAnalyzer();

// 3. Run the performance analysis
final PcmLqnsAnalyzerContext ctx = analyzer.setupAnalysis(instance);
final PerformanceResult<NamedElement> result = ctx.analyze();

// [optional] 3.1 Remove the trace information to restore the exact same PCMInstance
ctx.untrace();

// 4. Read the results
RepositoryComponent component = instance.getRepositories().get(0).getComponents__Repository().get(0);
for (final Result<? extends NamedElement> r : result.getResults(component)) {
    // ...
}
```

The retrieved result objects are mapped to a `NamedElement` of the PCM instance. The result objects are strongly typed with the help of appropriate value objects to retain their semantic meaning. For example, there are `ServiceTime`, `Throughput` and `Utilization` objects to store the analysis results. Have a look at `de.fakeller.palladio.analysis.result` package to see what else is available.



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
# Change Log

This file keeps track of all changes to this project. This project follows [semantic versioning](http://semver.org/) and [keeps a change log](http://keepachangelog.com/).

> Please [view this change log on the master branch](https://github.com/DECLARE-Project/palladio-headless/blob/master/CHANGELOG.md), as otherwise it is probably outdated.


## [UNRELEASED]


## v0.6.1 - 2016-10-20

### Changed
- Updated to fastpan v0.2.1


## v0.6.0 - 2016-10-11

### Changed
- The `performance-analysis` module has been extracted to its own project: https://github.com/DECLARE-Project/fastpan.
- The package namespaces have been changed from `de.fabiankeller.*` to `de.fakeller.*` to comply with Java naming conventions.
- The maven groupId has been changed from `de.fabiankeller.palladio-headless` to `de.fakeller.palladio-headless` to comply with naming conventions. 


## v0.5.1 - 2016-10-04 [Maintenance Release]

### Changed
- Update Travis build process to release `performance-analysis` module.


## v0.5.0 - 2016-10-04

### Changed
- The performance abstraction has been refactored to the separate module `performance-analysis` under the namespace `de.fabiankeller.performance.analysis`.


## v0.4.1 - 2016-10-03

### Changed
- The performance results interface has been simplified. Instead of `<? extends T>` all interfaces now simply use `<T>` instead.

### Added
- `TraceMapper` interface to easily map performance results between different models.


## v0.4.0 - 2016-09-28

### Changed
- The performance analyzers have a new API, starting with the `de.fabiankeller.palladio.analysis.PerformanceAnalyzer` interface. The usage of existing analyzers has slightly changed, however the new interface is simple to use and more generic.
- The palladio headless project does not require a `config.properties` anymore. Former configuration settings now have sound default values and can be programmatically overridden using the respective classes.
- The performance results are now value objects as well, implementing the `PerformanceMetric` interface.

### Fixes
- The `Pcm2LqnResultsParser` previously failed when there was no UUID in any given element of the results file. The parser will now ignore elements without trace and print a log message instead.


## v0.3.0 - 2016-09-23

### Changed
- `Pcm2Lqn*` classes are now located in `de.fabiankeller.palladio.analysis.pcm2lqn`.

### Added
- A `PcmModelTrace` class to trace PCM `NamedElements` by appending a UUID to their name.
- An abstraction for performance results in `de.fabiankeller.palladio.analysis.result`.
- A parser for the PCM2LQN analysis results.


## v0.2.2 - 2016-09-15

### Changed
- Travis is now releasing build artifacts to GitHub releases.


## v0.2.1 - 2016-09-15

### Changed
- The PCM2LQN analysis output format is XML by default now.
- Build process to produce a JAR file for `palladio-environment` containing all dependencies, and all others without dependencies.


## v0.2.0 - 2016-09-13

### Changed
- Palladio default models are now loaded from the classpath, the Environment config is not required anymore.
- The module structure has been refactored

### Fixes
- Excludes all xml-api related dependencies
- The palladio-bridge module has been extracted to a separate project


## v0.1.0 - 2016-07-15

### Fixes
- Fixed issues when running the LQNS analysis with a model built by the `PcmBuilder`.

### Added
- Major refactoring to split prototype to several classes.
- Builder API to construct PCM instances using an API (see [PR #3](https://github.com/DECLARE-Project/palladio-headless/pull/3))
- Improved dependency management with maven


## v0.0.1 - 2016-05-28

### Added
- Working prototypical implementation of running Palladio's PCM2LQN tool headless, i.e. without Eclipse 
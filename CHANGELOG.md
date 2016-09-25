# Change Log

This file keeps track of all changes to this project. This project follows [semantic versioning](http://semver.org/) and [keeps a change log](http://keepachangelog.com/).

> Please [view this change log on the master branch](https://github.com/SQuAT-Team/palladio-headless/blob/master/CHANGELOG.md), as otherwise it is probably outdated.


## [UNRELEASED]

### Changed
- The performance analyzers have a new API, starting with the `de.fabiankeller.palladio.analysis.PerformanceAnalyzer` interface. The usage of existing analyzers has slightly changed, however the new interface is simple to use and more generic.
- The palladio headless project does not require a `config.properties` anymore. Former configuration settings now have sound default values and can be programmatically overridden using the respective classes.


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
- Builder API to construct PCM instances using an API (see [PR #3](https://github.com/SQuAT-Team/palladio-lqns-headless/pull/3))
- Improved dependency management with maven


## v0.0.1 - 2016-05-28

### Added
- Working prototypical implementation of running Palladio's PCM2LQN tool headless, i.e. without Eclipse 
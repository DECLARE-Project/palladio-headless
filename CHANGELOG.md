# Change Log

This file keeps track of all changes to this project. This project follows [semantic versioning](http://semver.org/) and [keeps a change log](http://keepachangelog.com/).


## [UNRELEASED]

### Changed
- The PCM2LQN analysis output format is XML by default now


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
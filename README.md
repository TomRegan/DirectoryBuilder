DirectoryBuilder [![Build Status](https://travis-ci.org/TomRegan/DirectoryBuilder.png?branch=master)](https://travis-ci.org/TomRegan/DirectoryBuilder)
================

Creates a directory tree from XML

## Directory Builder Core

Contains classes for parsing configuration, creating a directory structure and processing file templates.

## Directory Builder Demo

Demonstrates how to use the Directory Builder library to create a directory tree, and shows two-way communication
between the user application and descriptors.

## Usage

The build uses gradle via a wrapper, so does not require any tools to be installed.

### Build

To run the build in a bash (or similar) shell:

    $ ./gradlew build

from the root of the project.

### Include

Add the directory-builder-core jar as a dependency to your project.

### Run Demo App

Find the `directory-builder-demo.jar` in the `directory-builder-demo/build/libs/` directory. Run using the command:

    $ java -jar directory-builder-demo.jar

### Further Documentation

Javadoc can be built:

    $ ./gradlew javadoc

Documentation output will be in `directory-builder-core/build/docs/javadoc/`.
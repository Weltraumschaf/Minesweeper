# Welcome to the Minesweeper Project

Java Swing implementation of famous [Minesweeper][1].

## Installation

You need Java 7 SDK to run the game.

At the moment you can only start the game from command line.

Either clone the repository or download the [zip file][3]. In case of zip file you must unpack it.
Then move into the direcptry and invoke the start script. You can pass two parameters

### Linux/Mac OS

With default size (8 x 8):

    $ cd minesweeper
    $ ./bin/minesweeper

with custom size:

    $ cd minesweeper
    $ ./bin/minesweeper 16 12

### Windows

Open `cmd.exe` and change into the minesweeprt directory.

With default size (8 x 8):

    > cd minesweeper
    > bin\minesweeper.bat

with custom size:

    > cd minesweeper
    > bin\minesweeper.bat 16 12

## Build From Source

You need [Maven][2] and Java JDK 7 to build the source.

    $ cd minesweeper
    $ mvn clean install

[1]: https://de.wikipedia.org/wiki/Minesweeper
[2]: https://www.maven.org/
[3]: https://github.com/Weltraumschaf/Minesweeper/archive/master.zip

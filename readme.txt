Othello
Project for IJA course

Authors:
    Tomáš Vlk
    Tomáš Ščavnický

Implementation of Othello(Reversi) game with graphical user interface.


---------------------
Build and run Othello
---------------------
Before compile or run this application you must have installed Java 8. After
that you must download required libraries. Move to lib directory and run
get-libs.sh from this directory. Finally move back to root directory.

To compile, generate documentation and create jar file run:

    $ ant compile

To only compile(generate class files) run:

    $ ant production

To only generate documentation run:

    $ ant doc

To only generate jar file run:

    $ ant jar

To simply run Othello run:

    $ ant run

If you want clean all generated files run:

    $ ant clean


-------
Othello
-------
Application has no command line arguments. When the application starts you will
see launcher window, where you can set game options. When you are ready click to
play button and enjoy the game!

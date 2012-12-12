Gabriel West
CS 2410
Project 1 - Calculator
11/19/12

For simplicity, please use "Keyculator.jar" to play with the calculator. If you want to compile the program yourself, istructions are included below.

Introduction:
Rather than extending functionality of the calculator in the book, I decided to create my own calculator program from scratch, using an expression parsing library called JEval. This calculator behaves almost identically to most simple professional calculators, and could easily be extended to include scientific operations, radians, and so forth. Expressions are also stored in a history pane, which can be accessed at any time through the "view" menu. 

Using the calculator:
The calculator parses complete expressions, so entering something like "2+2" works, but "6*3/(45+6/(32-8))+72" will also work. In addition (haha), you can do math to the result just by pressing another operator. Pressing a number will start a new expression. For example:

2+2
=4
+2
=6
/2
=3

Using JEval/Compiling the program:
Hopefully, you should be able to compile the program without changing anything. Just in case, the only thing that you need to do to get it to work is add jeval-0.9.4/dist/jeval.jar to your buildpath, and compile the program. In Eclipse, this can be done by right clicking on the project and selecting "Edit build path" then in the libraries tab, click the "add external jars" button. Navigate to /dist/ and select jeval.jar.

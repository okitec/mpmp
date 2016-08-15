mpmp - Multiplayer Monopoly
===========================

*A version of Monopoly, situated in the Weimar republic. This originated as a German school project.*

Many gameplay features are now in the game, but there are graphical glitches making it
annoying to play.

Installation
------------

Get hold of mpmp by cloning this repo or by downloading the .zip via GitHub.

	git clone https://github.com/okitec/mpmp.git

There are two projects: `mpmp-launcher` and `mpmp`. We use NetBeans, but there is a script
for Plan 9's `rc` shell that can generate `mpmp.jar` and `mpmp-launcher.jar` automagically.
Adapting it to any other shell should be trivial. If you choose to use NetBeans, fix the
library resolve problem `mpmp` has by adding all the jars in `lib/` to a library.

How to run it
-------------

All parts of `mpmp` require access to the `graphics/` directory at runtime, so the jars must
be run in the main directory. Execute the launcher to either host a game or join one. On Windows,
click the jar file to execute it. On other systems, type:

	java -jar mpmp-launcher.jar

Essentially, the launcher is passing some arguments to mpmp proper, which acts as client
or server depending on the first argument.

	java -jar mpmp.jar server
	java -jar mpmp.jar client <server addr> <gamemode> <color> <username>

What can it do?
---------------

 - chat
 - rolling dice and moving
 - buying and selling plots
 - buying houses
 - event cards
 - special fields
 - cheats

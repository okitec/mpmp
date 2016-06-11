mpmp - Multiplayer Monopoly
===========================

*A version of Monopoly, situated in the Weimar republic. This is a German school project.*

As of now, `mpmp` doesn't fulfill its purpose, but it is a full chat client and server.
There are lots of SVG graphics and a PNG of the game board in the subdirectories of `graphics/`.

Installation
------------

Get hold of mpmp by cloning this repo or by downloading the .zip via GitHub.

	git clone https://github.com/leletec/mpmp.git

There are two projects: `mpmp-launcher` and `mpmp`. We use NetBeans, but there is a script
for Plan 9's `rc` shell that can generate `mpmp.jar` and `mpmp-launcher.jar`automagically.
Adapting it to anyother shell should be trivial.

How to run it
-------------

Execute the launcher to either host a game or join one. On Windows, click the jar file to
execute it. On other systems, type:

	java -jar mpmp-launcher.jar

Essentially, the launcher is passing some arguments to mpmp proper, which acts as client
or server depending on the first argument.

	java -jar mpmp.jar server
	java -jar mpmp.jar client <server addr> <gamemode> <color> <username>

What can it do?
---------------

`mpmp` implements all the chatting functionality in client and server, but not much more.

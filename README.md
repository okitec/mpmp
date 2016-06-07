mpmp - Multiplayer Monopoly
===========================

*A version of Monopoly, situated in the Weimar republic. This is a German school project.*

As of now, the first prototype is not really interesting, it's essentially the server implementing
a subset of the protocol. The client can't do anything. There are lots of SVG graphics and a PNG of
the game board in the subdirectories of `graphics/`.

Installation
------------

Get hold of mpmp by cloning this repo or by downloading the .zip via GitHub.

    git clone https://github.com/leletec/mpmp.git

There are two projects: `mpmp-launcher` and `mpmp`. We use NetBeans, but there is a script for Plan 9's
`rc` shell that can generate `mpmp.jar` automagically. Adapting it to any other shell should be trivial.

How to run it
-------------

Execute the launcher to either host a game or join one. Essentially, it's passing some
arguments to mpmp proper, which acts as client or server depending on the first argument.

    java -jar mpmp.jar server
    java -jar mpmp.jar client

What can it do?
---------------

The server implements `subscribe` and `chat`. For more on the yet unfinished protocol, see
[its documentation](proto.md). Simply connect to a running server instance on port 1918 via
netcat. Telnet will likely not work because it expects CRLF line endings.

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

Open up the project in Eclipse (we use Eclipse because of internal decisions *cough*) and compile it.

What can it do?
---------------

The server implements `subscribe` and `chat`. For more on the yet unfinished protocol, see [its documentation](proto.md).
Simply connect to a running instance of mpmp-srv on port 1918 via netcat. Telnet will likely not work because it expects CRLF line endings.

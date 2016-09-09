DEVELOPER HOWTO – Adding protocol packets
=========================================

Should the need for a new protocol packet arise, think about it. *Is it really necessary?*
*Is it implementable?* If so, change the protocol spec accordingly and push the changes to
a topic branch. Add the packet to the packet overview and the detailed definition and add
new error codes to the error code table as well.

If the packet is deemed good, make a new class in the `net` package and extend `CmdFunc`.
The class name is the packet's name where the hyphens are replaced by CamelCase. Add a
JavaDoc comment describing the direction of the packet (`S->C` or `C->S`). Broadcast
packets have names ending in `-update`, e.g. `pos-update`, `turn-update` and `chat-update`.
They often update the clients' cached model.

Now, implement the `exec` and `error` functions. Be aware that the `error` method is executed
in the sender of the packet whereas `exec` is executed on the receiver. The `error`
method can be a dummy if reacting to the error is futile anyway.

Add your packet to the `Cmd` table.

Server→Client packets
----------------------

Server-to-Client packets update the client's model or update visuals (e.g. chat).
Update packets need a single-function public interface in the packet handler. This function's
arguments are the strictly typed arguments used in the packet. `exec` just needs to parse
the passed arguments and call the update function.

	public class PosUpdate extends CmdFunc {
		private PosUpdater pu;

		...
		public interface PosUpdater {
			public void posUpdate(int pos, String name);
		}

		public void addPosUpdater(PosUpdater pu) {
			this.pu = pu;
		}
	}

These update functions are implemented in the client's `Controller`; the registration happens
in the `Controller`'s constructor.

Purely visual S->C packets follow the same pattern, except that they use a `view.Displayer`.
Implementations of `Displayer` are inner classes of `Frame` with a public attribute pointing
to an instance of them. To write to the chat box, use `chatDisp`.

If you add a update packet, make an entry in `srv.Update`. Call it from the server model.


Client→Server packets
----------------------

These packets are sent when the user of a client does *something*: sending chat, ending their
turn, building houses, buying plots, etc. Because there is not yet a server controller, gameplay
packets are a tad harder to do; just follow the existing code. In the end, it amounts to finding
out what parts of the server-model are affected and calling game logic routines.


Conclusion
----------

Adding packets is not hard if you get the scheme: it's quite regular. Some parts
might even be automated away in the future.

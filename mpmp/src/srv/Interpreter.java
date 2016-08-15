package srv;

import model.SrvPlayer;

/**
 * This interpreter executes gameplay commands. The event cards uses them;
 * cheats for testing are the other use. For a command list, see cmds.md
 * in the top directory.
 *
 * Separate commands on one line with semicola (tp 5; collect 20).
 */
public class Interpreter {
	/**
	 * For cheat system: pass a full command line.
	 */
	public static void run(SrvPlayer sp, String line) {
		run(sp, line.split("( |\t)*;( |\t)*"));
	}

	/**
	 * For event cards: pass already split command array.
	 */
	public static void run(SrvPlayer sp, String[] cmds) {
		String[] args;

		for (String cmd: cmds) {
			args = cmd.split(" ");

			try {
				switch(args[0]) {
				/* one argument */
				case "add-money":
					sp.addMoney(Integer.parseInt(args[1]));
					break;
				case "collect":
					sp.collect(Integer.parseInt(args[1]));
					break;
				case "set-money":
					int sum = Integer.parseInt(args[1]);
					sp.p.setMoney(sum);
					/* Send update here as we don't pass SrvPlayer.addMoney */
					Update.transact(sp, sum, "set-money command");
					break;
				case "tp":
					sp.teleport(Integer.parseInt(args[1]), true);
					break;
	
				/* no argument */
				case "add-unjail":
					sp.addUnjailCard();
					break;
				case "prison":
					sp.prison(true);
					break;

				default:
					System.err.println("mpmp-script: bad command '" + args[0] + "'");
				}
			} catch(ArrayIndexOutOfBoundsException oobe) {
				System.err.println("mpmp-script: missing argument for command '" + args[0] + "'");
			}
		}
	}
}

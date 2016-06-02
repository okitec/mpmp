package cmds;

import main.Client;

public interface CmdFunc {
	public void exec(String line, Client c);
}

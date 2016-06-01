package cmds;

import main.Client;

public class Subscribe implements CmdFunc {
	@Override
	public void exec(String line, Client c) {
		String[] flds;
		
		flds = line.split(" \t");
		if(flds.length < 3) {
			// XXX continue
		}
	}
}

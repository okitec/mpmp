package net;

import view.Displayer;

public class StartUpdate implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		d.reset();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/*TODO*/}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}

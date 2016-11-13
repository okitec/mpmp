package net;

import view.Displayer;

/** eventcard S->C display-only packet */
public class Eventcard implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String descr;

		descr = line.substring("eventcard".length());
		descr = descr.trim();
		d.show(descr);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}

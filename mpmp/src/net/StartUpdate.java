package net;

import view.Displayer;

public class StartUpdate implements CmdFunc {
	private StartUpdater su;
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		su.startUpdate();
		d.reset();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/*TODO*/}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}

	public interface StartUpdater {
		public void startUpdate();
	}

	public void addStartUpdater(StartUpdater su) {
		this.su = su;
	}
}

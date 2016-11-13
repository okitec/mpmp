/* Generated at Sun Nov 13 14:40:16 CET 2016 */

package net;

/**
 * S->C game start-update
 */
public class StartUpdate implements CmdFunc {
	private StartUpdater su;

	@Override
	public void exec(String line, Conn conn) {
		su.startUpdate();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/* herp derp */}

	public interface StartUpdater {
		public void startUpdate();
	}

	public void addStartUpdater(StartUpdater su) {
		this.su = su;
	}
}

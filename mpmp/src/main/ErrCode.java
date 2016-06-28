package main;

/**
 * Error codes. (inspired by SMTP reply codes)
 *	first digit: How bad is the error? (we don't need positive answers, because we have "+JAWOHL")
 *		1yz  Transient Negative Completion reply
 *		2yz  Permanent Negative Completion reply
 *		3yz  "Impossible" Negative Completion reply
 *	second digit: Where did the error occur?
 *		x0z  EOF, Usage, Command, Internal
 *		x1z  Subscribing, ClientlistUpdating
 *		x2z  Chatting, Whispering
 *		x3z  Gameplay
 *	third digit: What exactly happened?
 *		depends on the second digit
 * 
 * @author Leander
 */
public enum ErrCode {
	MissingMoney(131, "You don't have enough money for this"),
	MissingUnjailCard(132, "You don't have a unjail card"),
	NewEventCard(133, "I want to have a new eventcard"),
	NewEventCardImossible(134, "You can't get a new eventcard"),
	UnbalancedColor(135, "Your houses are to unbalanced for that color"), //XXX Say what color?
	AlreadyOwned(136, "This is owned by"),
	PlotWithHouse(137, "You can't sell a plot with houses on it"),
	InsufficientMoney(138, "You don't have enough money for this. You would need"),
	NameTaken(211, "Name already taken!"),
	NoSuchPlayer(215, "This player does not exist"),
	NotSubscribed(221, "You are not subscribed"),
	NotAPlayer(232, "You are not a player"),
	Usage(301, "Usage:"),
	Internal(302, "Unexpected Error:"),
	EOF(303, "Unexpected EOF"),
	Command(304, "Not existing command:"),
	ClientlistUpdateMissingFields(311, "Expected three fields: 'color: mode: name'"),
	NotAPlot(333, "This is not a valid plot");
	
	private final int code;
	private final String message;
	private ErrCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ErrCode search(int code) {
		for (ErrCode err : ErrCode.values())
			if (err.code == code)
				return err;
		return null;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}

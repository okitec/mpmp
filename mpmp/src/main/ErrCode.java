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
 */
public enum ErrCode {
	MissingMoney(131, "Insufficient money, need"),
	MissingUnjailCard(132, "Missing unjail card"),
	NewEventCard(133, "pls giv me new eventcart"),
	NewEventCardImpossible(134, "Taking new eventcard not allowed"),
	UnbalancedColor(135, "Your houses are to unbalanced for that plot group"), //XXX Say what color?
	AlreadyOwned(136, "Owned by"),
	PlotWithHouse(137, "Can't sell a plot with houses on it"),
	TooManyHouses(138, "Already fully upgraded"),
	DontHave(139, "You don't have"),

	NameTaken(211, "Name already taken"),
	GameRunning(212, "Game already running"),
	NoSuchPlayer(215, "This player does not exist"),
	NotSubscribed(221, "You are not subscribed"),
	NotAPlayer(232, "You are not a player"),
	
	Usage(301, "Usage:"),
	Internal(302, "Unexpected Error:"),
	EOF(303, "Unexpected EOF"),
	Command(304, "Non-existing command:"),
	ClientlistUpdateMissingFields(311, "Expected three fields: 'color: mode: name'"),
	NotAPlot(333, "Not a valid plot");
	
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

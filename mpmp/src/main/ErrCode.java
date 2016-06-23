package main;

/**
 * Error codes. (inspired by SMTP reply codes)
 *	first digit: How bad is the error? (we don't need positive answers, because we have "+JAWOHL")
 *		1yz  Transient Negative Completion reply
 *		2yz  Permanent Negative Completion reply
 *	second digit: Where did the error occur?
 *		x0z  EOF
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
	AlreadyOwned(136, "This is owned by another player"),
	PlotWithHouse(137, "You can't sell a plot with houses on it"),
	EOF(201, "Unexpected EOF"),
	NameTaken(211, "Name already taken!"),
	ClientlistUpdateUsage(212, "Usage: clientlist-update <number of clients>"),
	ClientlistUpdateMissingFields(213, "Expected three fields: 'color: mode: name'"),
	SubscribeUsage(214, "Usage: subscribe <spectator|player> #<rgb hex triplet> <Name>"),
	NotSubscribed(221, "You are not subscribed"),
	NoSuchPlayer(222, "This player does not exist"),
	WhisperUsage(223, "Usage: whisper <playername> <message>"),
	UnjailUsage(231, "Usage: unjail [card|money]");
	
	private final int code;
	private final String message;
	private ErrCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ErrCode search(int code){
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

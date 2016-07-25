package net;

import java.util.ArrayList;

import srv.Client;
import model.Diceroll;
import model.Field;
import model.Player;
import model.SrvModel;
import model.SrvPlayer;

/**
 * start-game C->S
 */
public class StartGame implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		SrvModel sm = SrvModel.self;
		ArrayList<SrvPlayer> players = sm.getSrvPlayers();
		SrvPlayer sp;

		sm.m.startGame();
		
		sp = sm.getSrvPlayer(((Client) conn).getName());
		if (sp == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			sp.prison(true);
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + sp.p.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + sp.p.getName());
		} else {
			sp.move(dr.getSum());
			Client.broadcast("turn-update " + dr.getSum() + " " + dr.getPaschs() + " " + sp.p.getName());
			Client.broadcast("pos-update " + sp.p.getPos() + " " + sp.p.getName());
		}

		sm.m.setCurrentPlayer(players.get((players.indexOf(sp)+1) % players.size()).p);
		
		Client.broadcast("start-update");
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
}

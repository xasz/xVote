package me.xasz.xVote;

import org.bukkit.entity.Player;

public class xVoteConstruct {
	private boolean vote = false;
	private Player voter = null;
	private boolean initiator = false;
	public xVoteConstruct(Player p, boolean v){
		voter = p;
		vote = v;
	}
	public boolean isInitiator() {
		return initiator;
	}
	public void setInitiator(boolean initiator) {
		this.initiator = initiator;
	}
	public Player getVoter() {
		return voter;
	}
	public void setVoter(Player voter) {
		this.voter = voter;
	}
	public boolean isVote() {
		return vote;
	}
	public void setVote(boolean vote) {
		this.vote = vote;
	}
	public boolean getVote() {
		return vote;
	}
}

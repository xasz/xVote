package me.xasz.xVote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;


public abstract class xAbstractVote {
	protected xVote x = null;
	protected boolean isVoteRunning = false;
	protected int voteTimerSec = 40;
	protected int timeLeftForVote = 0;
	protected World myWorld = null;
	protected List<xVoteConstruct> votes = null;
	protected String votename ="basevote";
	protected int playerCountOnVoteStart = 0;
	protected double successPercent = 0.6f;
	protected int repeatingRunnable = 0;
	protected int fulltimerRunnable = 0;
	
	public xAbstractVote(World w,xVote plugin){
		myWorld = w;
		this.x = plugin;
		votes = new ArrayList<xVoteConstruct>();
		this.successPercent = xVote.voteSuccessPercentage;
		this.voteTimerSec = xVote.votingTime;
	}
	public void vote (Player p, boolean v){
		votes.add(new xVoteConstruct(p,v));
		if(votes.size() >= myWorld.getPlayers().size() && isVoteRunning){
			//all players voted
			timerEndExectue();
		}
	}
	public boolean startVote(Player p,int playercount){
		if(isVoteRunning){
			return false;
		}
		playerCountOnVoteStart = playercount;
		xVoteConstruct v = new xVoteConstruct(p,true);
		votes.add(v);
		v.setInitiator(true);
		
		startTimer();
		
		return true;
	}
	public boolean isVoteRunning(){
		return isVoteRunning;
	}
	protected void startTimer(){
		this.isVoteRunning = true;
		this.timeLeftForVote = this.voteTimerSec;
		repeatingRunnable = x.getServer().getScheduler().scheduleSyncRepeatingTask(x,new Runnable(){
			@Override
			public void run() {
				timeLeftForVote -= 10;
				if(timeLeftForVote <= 10){
					x.getServer().getScheduler().cancelTask(repeatingRunnable);
				}
				x.sendBroadCastMessageToWorld(myWorld, ChatColor.GOLD+votename+ChatColor.WHITE+" - Current Result: "+ChatColor.GREEN+"Yes: "+ChatColor.WHITE+getYesVotes()+" / "+ChatColor.RED+"No: "+ChatColor.WHITE+getNoVotes());
				x.sendBroadCastMessageToWorld(myWorld, "Needed Player for Success: "+getNeededPlayers());
				x.sendBroadCastMessageToWorld(myWorld, "Timeleft for Voting: "+ChatColor.YELLOW+timeLeftForVote + ChatColor.WHITE + " Seconds");
				
			}
		}, 200L, 200L);
		
		fulltimerRunnable = x.getServer().getScheduler().scheduleSyncDelayedTask(x, new Runnable(){
			@Override
			public void run() {
				timerEndExectue();
			}
			
		},this.voteTimerSec*20);

		
	}
	protected void timerEndExectue(){
		x.sendBroadCastMessageToWorld(myWorld, ChatColor.GOLD+votename+ChatColor.WHITE+" - Current Result: "+ChatColor.GREEN+"Yes: "+ChatColor.WHITE+getYesVotes()+" / "+ChatColor.RED+"No: "+ChatColor.WHITE+getNoVotes());
		x.sendBroadCastMessageToWorld(myWorld, "Needed Player for Success:"+getNeededPlayers());
		x.getServer().getScheduler().cancelTask(fulltimerRunnable);
		x.getServer().getScheduler().cancelTask(repeatingRunnable);
		if((getYesVotes()-getNoVotes()) >= getNeededPlayers()){
			x.sendBroadCastMessageToWorld(myWorld, ChatColor.GOLD+votename+ChatColor.GREEN+" - Vote success!");
			execute();	
		}else{
			x.sendBroadCastMessageToWorld(myWorld, ChatColor.GOLD+votename+ChatColor.RED+" - Vote failed!");
		}
		votes.clear();
		isVoteRunning = false;
	}
	protected void stopTimer(){
		this.isVoteRunning = false;
		this.x.getServer().getScheduler().cancelTask(repeatingRunnable);
		this.x.getServer().getScheduler().cancelTask(fulltimerRunnable);
	}
	protected void printCurrentVoteResult(){

	}
	public int getYesVotes(){
		int count = 0;
		for(xVoteConstruct vote : votes){
			if(vote.getVote()){
				count++;
			}
		}
		return count;
	}

	public int getNoVotes(){
		int count = 0;
		for(xVoteConstruct vote : votes){
			if(!vote.getVote()){
				count++;
			}
		}
		return count;
	}
	public int getNeededPlayers(){
		return (int)(Math.round((this.playerCountOnVoteStart*this.successPercent)+0.5f));
	}
	public boolean hasPlayerVoted(Player p){
		for (xVoteConstruct vc :votes){
			if(vc.getVoter() == p){
				return true;
			}
		}
		return false;
	}
	abstract protected void execute();
}

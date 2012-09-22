package me.xasz.xVote;

import org.bukkit.World;

public class xWheaterVote extends xAbstractVote{
	private boolean stopStorm = false;
	public xWheaterVote(World w, xVote plugin) {
		super(w, plugin);
		// TODO Auto-generated constructor stub
		this.votename = "xWheaterVote";
	}
	@Override
	protected void execute(){
		if (myWorld.hasStorm() == !stopStorm){
			x.sendBroadCastMessageToWorld(myWorld, "Already the correct Wheater. Nothing to do.");
			return;
		}
		if (isStopStorm()){
			//its day
			myWorld.setStorm(false);
			return;
		}
		myWorld.setStorm(true);		
	}
	public boolean isStopStorm() {
		return stopStorm;
	}
	public void setStopStorm(boolean stopStorm) {
		this.stopStorm = stopStorm;
	}
}

package me.xasz.xVote;

import org.bukkit.World;


public class xTimeVote extends xAbstractVote{

	public xTimeVote(World w,xVote v) {
		super (w,v);
		this.votename = "xTimeVote";
	}
	
	@Override
	protected void execute(){

		if (myWorld.getTime() >= 0 && myWorld.getTime() <= 12000){
			//its day
			myWorld.setTime(13000);
			return;
		}
		myWorld.setTime(1000);		
	}

}

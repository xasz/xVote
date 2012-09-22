package me.xasz.xVote;



import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class xEventListener implements Listener{
	private final xVote x;
	private Map<World,xTimeVote> timevotes = null;
	private Map<World,xWheaterVote> wheatervotes = null;
	public xEventListener (final xVote instance){
		x = instance;
		timevotes = new HashMap<World,xTimeVote>();
		for(World w:x.getServer().getWorlds()){
			timevotes.put(w, new xTimeVote(w,x));
		}
		wheatervotes = new HashMap<World,xWheaterVote>();
		for(World w:x.getServer().getWorlds()){
			wheatervotes.put(w, new xWheaterVote(w,x));
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() != Action.LEFT_CLICK_BLOCK){
			return;
		}
		boolean isDay = false;
		if (event.getPlayer().getWorld().getTime() >= 0 &&event.getPlayer().getWorld().getTime() <= 12000){
			isDay = true;
		}
		boolean isSun = false;
		if (event.getPlayer().getWorld().getTime() >= 0 &&event.getPlayer().getWorld().getTime() <= 12000){
			isSun = true;
		}
		//timevoting
		if(event.getClickedBlock().getType() == Material.BED_BLOCK){
			if(timevotes.get(event.getPlayer().getWorld()).isVoteRunning()){
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH)
				{
					if(timevotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						timevotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), true);
						if(isDay){
							x.sendMessageToPlayer(event.getPlayer(),"You voted for Night.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted for Day.");
						}
					}	
				}else{
					if(timevotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						timevotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), false);
						if(isDay){
							x.sendMessageToPlayer(event.getPlayer(),"You voted against Night.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted against Day.");
						}
					}		
				}

			}else{
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH){
					timevotes.get(event.getPlayer().getWorld()).startVote(event.getPlayer(),event.getPlayer().getWorld().getPlayers().size());
					if(isDay){
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started xNightVote");
					}else{
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started xDayVote");
					}	
				}
			}
		}
		//wheatervoting
		if(event.getClickedBlock().getType() == Material.IRON_BLOCK){
			if(wheatervotes.get(event.getPlayer().getWorld()).isVoteRunning()){
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH)
				{
					if(wheatervotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						wheatervotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), true);
						if(isDay){
							x.sendMessageToPlayer(event.getPlayer(),"You voted for Sun.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted for Storm.");
						}
					}	
				}else{
					if(wheatervotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						wheatervotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), false);
						if(isDay){
							x.sendMessageToPlayer(event.getPlayer(),"You voted against Sun.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted against Storm.");
						}
					}		
				}

			}else{
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH){
					wheatervotes.get(event.getPlayer().getWorld()).startVote(event.getPlayer(),event.getPlayer().getWorld().getPlayers().size());
					if(isSun){
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started xStormVote");
						wheatervotes.get(event.getPlayer().getWorld()).setStopStorm(false);
					}else{
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started xSunVote");
						wheatervotes.get(event.getPlayer().getWorld()).setStopStorm(true);
					}	
				}
	
			}
		}
	}
}
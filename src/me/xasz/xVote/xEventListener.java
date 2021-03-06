package me.xasz.xVote;



import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
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
	private Map<World,xWheaterVote> weathervotes = null;
	public xEventListener (final xVote instance){
		x = instance;
		timevotes = new HashMap<World,xTimeVote>();
		for(World w:x.getServer().getWorlds()){
			timevotes.put(w, new xTimeVote(w,x));
		}
		weathervotes = new HashMap<World,xWheaterVote>();
		for(World w:x.getServer().getWorlds()){
			weathervotes.put(w, new xWheaterVote(w,x));
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
		boolean isStorm = event.getPlayer().getWorld().hasStorm();
		
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
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started"+ChatColor.GOLD+" xNightVote");
					}else{
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started"+ChatColor.GOLD+" xDayVote");
					}	
				}
			}
		}
		//wheatervoting
		if(event.getClickedBlock().getType() == Material.IRON_BLOCK){
			if(weathervotes.get(event.getPlayer().getWorld()).isVoteRunning()){
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH)
				{
					if(weathervotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						weathervotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), true);
						if(isStorm){
							x.sendMessageToPlayer(event.getPlayer(),"You voted for good Weather.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted for bad Weather.");
						}
					}	
				}else{
					if(weathervotes.get(event.getPlayer().getWorld()).hasPlayerVoted(event.getPlayer())){
						x.sendMessageToPlayer(event.getPlayer(),"You already voted.");	
					}else{
						weathervotes.get(event.getPlayer().getWorld()).vote(event.getPlayer(), false);
						if(isStorm){
							x.sendMessageToPlayer(event.getPlayer(),"You voted against good Weather.");
						}else{
							x.sendMessageToPlayer(event.getPlayer(),"You voted against bad Weather.");
						}
					}		
				}

			}else{
				if(event.getPlayer().getItemInHand().getType() == Material.TORCH){
					weathervotes.get(event.getPlayer().getWorld()).startVote(event.getPlayer(),event.getPlayer().getWorld().getPlayers().size());
					if(isStorm){
						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started"+ChatColor.GOLD+" xStopStormVote");
						weathervotes.get(event.getPlayer().getWorld()).setStopStorm(true);
					}else{

						x.sendBroadCastMessageToWorld(event.getPlayer().getWorld(), event.getPlayer().getName()+" started"+ChatColor.GOLD+" xStartStormVote");
						weathervotes.get(event.getPlayer().getWorld()).setStopStorm(false);
					}	
				}
	
			}
		}

		/*if(event.getClickedBlock().getType() == Material.GRASS){
			if(isStorm){
				System.out.println("Es Regnet");
			}else{
				System.out.println("Kein Regen");
			}
		}
		if(event.getClickedBlock().getType() == Material.GOLD_BLOCK){
			if(isStorm){
				System.out.println("Regen stoppen");
				event.getPlayer().getWorld().setStorm(false);
			}else{
				System.out.println("Regen starten");
				event.getPlayer().getWorld().setStorm(true);
			}
		}
		if(event.getClickedBlock().getType() == Material.DIAMOND_BLOCK){
			if(event.getPlayer().getWorld().isThundering()){
				System.out.println("Thunder start");
				event.getPlayer().getWorld().setThundering(true);
			}else{
				System.out.println("Thunder stop");
				event.getPlayer().getWorld().setThundering(false);
			}
		}*/
	}
}

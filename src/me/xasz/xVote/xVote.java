package me.xasz.xVote;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class xVote extends JavaPlugin{
	
	PluginManager pm = null;
    xEventListener listener = null;
    public static double voteSuccessPercentage = 0.6f;
    public static int votingTime = 40;
	@Override
    public void onEnable() {
 
		voteSuccessPercentage = this.getConfig().getDouble("voteSuccessPercentage");
		votingTime = this.getConfig().getInt("votingTime");
	    this.getConfig().options().copyDefaults(true);
	    saveConfig();
	    
    	System.out.println("[xVote] enabled");
		pm = getServer().getPluginManager();
		listener = new xEventListener(this);
		pm.registerEvents(listener,this);
	}
    
    @Override
    public void onDisable() {
    	System.out.println("[xVote] disabled");
    	
    }
    public void sendMessageToPlayer(Player player, String message){
    	if(player != null){
    		player.sendMessage(ChatColor.BLUE+"[xVote] "+ChatColor.WHITE+message);
    	}
    }
    public void sendBroadCastMessageToWorld(World world,String message){
    	for(Player p :world.getPlayers()){
        	p.sendMessage(ChatColor.BLUE+"[xVote] "+ChatColor.WHITE+message);
    	}
    }
}

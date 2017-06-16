package com.buildersrefuge.utilities.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.buildersrefuge.utilities.Main;

public class NoClipManager {
	public static List<String> noClipPlayerNames;
	Main main;
	
	public NoClipManager(Main main){
		noClipPlayerNames = new ArrayList<String>();
		this.main = main;
		everyTick();
	}

	private void everyTick() {
		Bukkit.getScheduler().runTaskTimer(this.main, new Runnable() {
			@Override
			public void run() {
				checkForBlocks();
			}
		}, 1L, 1L);
	}
	
	private void checkForBlocks() {
		for (String name : noClipPlayerNames){
			Player p = Bukkit.getPlayer(name);
			if (p.isOnline()){
				if (p.getGameMode().equals(GameMode.CREATIVE)){
					boolean noClip = false;
					if (p.getLocation().add(0, -0.1, 0).getBlock().getType()!=Material.AIR&&p.isSneaking()){noClip = true;}
					else if (p.getLocation().add(+0.4, 0, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(-0.4, 0, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 0, +0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 0, -0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(+0.4, 1, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(-0.4, 1, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 1, +0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 1, -0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, +1.9, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					if (noClip){
						p.setGameMode(GameMode.SPECTATOR);
					}
				}
				else if (p.getGameMode().equals(GameMode.SPECTATOR)){
					boolean noClip = false;
					if (p.getLocation().add(0, -0.1, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(+0.4, 0, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(-0.4, 0, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 0, +0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 0, -0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(+0.4, 1, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(-0.4, 1, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 1, +0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, 1, -0.4).getBlock().getType()!=Material.AIR){noClip = true;}
					else if (p.getLocation().add(0, +1.9, 0).getBlock().getType()!=Material.AIR){noClip = true;}
					if (!noClip){
						p.setGameMode(GameMode.CREATIVE);
					}
				}
			}
		}
	}
}

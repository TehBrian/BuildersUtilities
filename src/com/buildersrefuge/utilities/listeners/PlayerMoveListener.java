package com.buildersrefuge.utilities.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.buildersrefuge.utilities.Main;

public class PlayerMoveListener  implements Listener{
	HashMap<String, Double> lastVelocity = new HashMap<String, Double>();
	static List<String> enabledPlayers = new ArrayList<String>();
	
	public Main plugin;
	
	public PlayerMoveListener(Main main){
		plugin = main;
	}
	
	public static boolean togglePlayer(Player p){
		if (enabledPlayers.contains(p.getName())){
			enabledPlayers.remove(p.getName());
			return false;
		}
		else {
			enabledPlayers.add(p.getName());
			return true;
		}
	}
	
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e){
		if (e.getPlayer().isFlying()){
			if (!enabledPlayers.contains(e.getPlayer().getName())){
				return;
			}
			Double speed = e.getFrom().clone().add(0, -e.getFrom().getY(), 0).distance(e.getTo().clone().add(0, -e.getTo().getY(), 0));
			if (Math.abs(e.getFrom().getYaw()-e.getTo().getYaw())>5){
				return;
			}
			if (Math.abs(e.getFrom().getPitch()-e.getTo().getPitch())>5){
				return;
			}
			if (lastVelocity.containsKey(e.getPlayer().getName())){
				Double lastSpeed = lastVelocity.get(e.getPlayer().getName());
				if (speed*1.15<lastSpeed){
					Vector v = e.getPlayer().getVelocity().clone();
					v.setX(0);
					v.setZ(0);
					e.getPlayer().setVelocity(v);
					lastVelocity.put(e.getPlayer().getName(), 0.0);
				}
				else if (speed>lastSpeed){
					lastVelocity.put(e.getPlayer().getName(), speed);
				}
			}
			else{
				lastVelocity.put(e.getPlayer().getName(), speed);
			}
		}
	}
}

package com.buildersrefuge.utilities.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.SecretBlockGUI;

public class SecretBlockHandler implements Listener, CommandExecutor
{
	  public static Main plugin;
	  
	  public SecretBlockHandler(Main main)
	  {
	    plugin = main;
	  }

	  @Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (cmd.getName().equalsIgnoreCase("blocks")||cmd.getName().equalsIgnoreCase("secretblocks")){
				if (!(sender instanceof Player)){return false;}
				Player p = (Player)sender;
				if (p.hasPermission("builders.util.secretblocks")){
					SecretBlockGUI g = new SecretBlockGUI();
					p.openInventory(g.generateStartInv());
					return true;
				}
			}
			return false;
	  }

}
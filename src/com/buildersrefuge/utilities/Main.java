package com.buildersrefuge.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.buildersrefuge.utilities.cmd.BannerHandler;
import com.buildersrefuge.utilities.cmd.ColorHandler;
import com.buildersrefuge.utilities.cmd.CommandHandler;
import com.buildersrefuge.utilities.cmd.SecretBlockHandler;
import com.buildersrefuge.utilities.listeners.BannerInventoryListener;
import com.buildersrefuge.utilities.listeners.ColorInventoryListener;
import com.buildersrefuge.utilities.listeners.PlayerMoveListener;
import com.buildersrefuge.utilities.listeners.SecretBlocksInventoryListener;
import com.buildersrefuge.utilities.object.NoClipManager;

public class Main  extends JavaPlugin implements Listener{
	BannerInventoryListener IL;
	BannerHandler CH;
	NoClipManager ncM;
    public static String version;
	
	public void onEnable(){
		this.saveDefaultConfig();
		String a = this.getServer().getClass().getPackage().getName();
		version = a.substring(a.lastIndexOf('.') + 1);
		IL = new BannerInventoryListener(this);
		CH = new BannerHandler(this);
		ncM = new NoClipManager(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(IL, this);
		pm.registerEvents(CH, this);
		getCommand("banner").setExecutor(CH);
		getCommand("bm").setExecutor(CH);
		pm.registerEvents(new ColorInventoryListener(this), this);
		pm.registerEvents(new SecretBlocksInventoryListener(this), this);
		pm.registerEvents(new PlayerMoveListener(this), this);
		pm.registerEvents(new ColorHandler(this), this);
		pm.registerEvents(this, this);
		getCommand("armorcolor").setExecutor(new ColorHandler(this));
		getCommand("color").setExecutor(new ColorHandler(this));
		getCommand("blocks").setExecutor(new SecretBlockHandler(this));
		getCommand("secretblocks").setExecutor(new SecretBlockHandler(this));
		
		getCommand("n").setExecutor(new CommandHandler(this));
		getCommand("nv").setExecutor(new CommandHandler(this));
		getCommand("nc").setExecutor(new CommandHandler(this));
		getCommand("noclip").setExecutor(new CommandHandler(this));
		getCommand("/1").setExecutor(new CommandHandler(this));
		getCommand("/2").setExecutor(new CommandHandler(this));
		getCommand("/cuboid").setExecutor(new CommandHandler(this));
		getCommand("/convex").setExecutor(new CommandHandler(this));
		getCommand("/cub").setExecutor(new CommandHandler(this));
		getCommand("/con").setExecutor(new CommandHandler(this));
		getCommand("/s").setExecutor(new CommandHandler(this));
		getCommand("/r").setExecutor(new CommandHandler(this));
		getCommand("/f").setExecutor(new CommandHandler(this));
		getCommand("/pa").setExecutor(new CommandHandler(this));
		getCommand("/c").setExecutor(new CommandHandler(this));
		getCommand("ws").setExecutor(new CommandHandler(this));
		getCommand("fs").setExecutor(new CommandHandler(this));
		getCommand("af").setExecutor(new CommandHandler(this));
		getCommand("advfly").setExecutor(new CommandHandler(this));
		getCommand("/derot").setExecutor(new CommandHandler(this));
		getCommand("/scale").setExecutor(new CommandHandler(this));
		getCommand("/twist").setExecutor(new CommandHandler(this));
	}

	@EventHandler
	public void onWeather(WeatherChangeEvent e)
	  {
		if (this.getConfig().getBoolean("disable-weather-changes")){
			e.setCancelled(true);
		}
	  }
	
	@EventHandler
	public void onPhysics(BlockPhysicsEvent e)
	  {
		if (!e.getChangedType().hasGravity()){
			if (this.getConfig().getBoolean("disable-physics")){
				e.setCancelled(true);
			}
		}
		else{
			if (this.getConfig().getBoolean("disable-gravity-physics")){
				e.setCancelled(true);
			}
		}
		
	  }
	
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	  {
		if (this.getConfig().getBoolean("disable-explosions")){
			e.setCancelled(true);
		}
	  }
	  
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)  {
		if (this.getConfig().getBoolean("disable-soil-trample")){
			 if (e.getAction() == Action.PHYSICAL) {
		            Block block = e.getClickedBlock();
		            if (block == null) return;
		            if (block.getType() == Material.SOIL) {
		                e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
		                e.setCancelled(true);
		                block.setTypeIdAndData(block.getType().getId(), block.getData(), true);
		            }
		        }
		}
       
    }
	
	@EventHandler
	  public void onLeafDecay(LeavesDecayEvent e)
	  {
		if (this.getConfig().getBoolean("disable-leaves-decay")){
			e.setCancelled(true);
		}
	  }
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	  {
		Player p = e.getPlayer();
		if(NoClipManager.noClipPlayerNames.contains(p.getName())){
			NoClipManager.noClipPlayerNames.remove(p.getName());
    	}
	  }
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e){
		
		if (this.getConfig().getBoolean("fix-attackspeed")){
			AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
		    attribute.setBaseValue(1024.0D);
		    e.getPlayer().saveData();
		}
	
	  }
	
	@EventHandler
    public void onTeleport(PlayerTeleportEvent event)
    {
        if (event.getCause().equals(TeleportCause.SPECTATE))
        {
        	if (!event.getPlayer().hasPermission("builders.util.tpgm3")){
        		event.setCancelled(true);
        	}
        }
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void ironTrapDoorInteract(final PlayerInteractEvent e){
		if (!this.getConfig().getBoolean("iron-trapdoor-interaction")){
			return;
		}
		if (e.isCancelled()){return;}
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){return;}
		if (!e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)){return;}
		if (!version.contains("v1_8")){
			if (!e.getHand().equals(EquipmentSlot.HAND)){
				return;
			}
		}
		if (e.getPlayer().isSneaking()){
			return;
		}
		
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			@Override
			public void run() {
				Block b = e.getClickedBlock();
				byte da = b.getData();
				byte data = 0;
				if (da>=0&&da<4){
					 data = (byte) (da+4);
				}
				else if (da>=4&&da<8){
					data = (byte) (da-4);
				}
				else if (da>=8&&da<12){
					data = (byte) (da+4);
				}
				else if (da>=12&&da<16){
					data = (byte) (da-4);
				}
				b.setData(data, true);
			}
		}, 0L);
		e.setCancelled(true);
	}
}

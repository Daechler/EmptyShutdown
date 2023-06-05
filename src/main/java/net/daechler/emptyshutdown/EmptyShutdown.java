package net.daechler.emptyshutdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

public class EmptyShutdown extends JavaPlugin implements Listener {

    // Timer for the shutdown
    private Timer timer;

    // Delay for the shutdown in milliseconds (24 hours)
    private final long delay = 24 * 60 * 60 * 1000;

    @Override
    public void onEnable() {
        // Sending the green message that the plugin is enabled
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getName() + " has been enabled!");

        // Register events
        Bukkit.getPluginManager().registerEvents(this, this);

        // Setup timer
        resetTimer();
    }

    @Override
    public void onDisable() {
        // Sending the red message that the plugin is disabled
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + getName() + " has been disabled!");

        // Cancel timer
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Reset the timer when a player joins the server
        resetTimer();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Reset the timer when a player leaves the server
        resetTimer();
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
            }
        }, delay);
    }
}

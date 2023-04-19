package de.jojojux.ptlweekly.api;

import org.bukkit.entity.Player;

import de.jojojux.ptlweekly.PlayerTimeLimit;
import de.jojojux.ptlweekly.managers.PlayerManager;
import de.jojojux.ptlweekly.model.TimeLimitPlayer;
import de.jojojux.ptlweekly.utils.UtilsTime;

public class PlayerTimeLimitAPI {

	private static PlayerTimeLimit plugin;
	public PlayerTimeLimitAPI(PlayerTimeLimit plugin) {
		this.plugin = plugin;
	}
	
	public static String getTimeLeft(Player player) {
		PlayerManager playerManager = plugin.getPlayerManager();
		TimeLimitPlayer p = playerManager.getPlayerByUUID(player.getUniqueId().toString());
		int timeLimit = playerManager.getTimeLimitPlayer(player);
		return playerManager.getTimeLeft(p, timeLimit);
	}
	
	public static String getTotalTime(Player player) {
		PlayerManager playerManager = plugin.getPlayerManager();
		TimeLimitPlayer p = playerManager.getPlayerByUUID(player.getUniqueId().toString());
		return UtilsTime.getTime(p.getTotalTime(), plugin.getMensajesManager());
	}
}

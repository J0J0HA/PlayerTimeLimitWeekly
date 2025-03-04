package de.jojojux.ptlweekly.managers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import de.jojojux.ptlweekly.PlayerTimeLimit;
import de.jojojux.ptlweekly.configs.MainConfigManager;
import de.jojojux.ptlweekly.model.TimeLimitPlayer;
import de.jojojux.ptlweekly.utils.UtilsTime;

public class ServerManager {

	private PlayerTimeLimit plugin;

	public ServerManager(PlayerTimeLimit plugin) {
		this.plugin = plugin;
	}

	public void saveDataTime() {
		FileConfiguration config = plugin.getConfig();

		// Se guarda el millis del proximo reinicio de tiempos por si el server
		// esta cerrado cuando llega a la hora de reinicio
		String resetTimeHour = plugin.getConfigsManager().getMainConfigManager().getResetTime();
		long finalMillis = UtilsTime.getNextResetMillis(resetTimeHour);

		config.set("Data.next_millis_reset", finalMillis);
		plugin.saveConfig();
	}

	public void executeDataTime() {
		FileConfiguration config = plugin.getConfig();

		// Se comprueba si el millis obtenido es menor al actual, si es asi
		// se reinician los tiempos de los jugadores
		if (config.contains("Data.next_millis_reset")) {
			long millisReset = config.getLong("Data.next_millis_reset");
			if (System.currentTimeMillis() > millisReset) {
				plugin.getPlayerManager().resetPlayers();
			}
		}
	}

	public String getRemainingTimeForTimeReset() {
		String resetTimeHour = plugin.getConfigsManager().getMainConfigManager().getResetTime();
		long finalMillis = UtilsTime.getNextResetMillis(resetTimeHour);
		long remainingMillis = finalMillis - System.currentTimeMillis();
		long segundos = remainingMillis / 1000;
		return UtilsTime.getTime(segundos, plugin.getMensajesManager());
	}

	public boolean isValidWorld(World world) {
		MainConfigManager mainConfig = plugin.getConfigsManager().getMainConfigManager();
		if (!mainConfig.isWorldWhitelistEnabled()) {
			return true;
		}

		List<String> worlds = mainConfig.getWorldWhitelistWorlds();
		if (worlds.contains(world.getName())) {
			return true;
		}

		return false;
	}
}

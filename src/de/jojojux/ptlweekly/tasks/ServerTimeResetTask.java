package de.jojojux.ptlweekly.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.scheduler.BukkitRunnable;

import de.jojojux.ptlweekly.PlayerTimeLimit;
import de.jojojux.ptlweekly.configs.MainConfigManager;

public class ServerTimeResetTask {

	private PlayerTimeLimit plugin;
	private DateTimeFormatter dtf;

	public ServerTimeResetTask(PlayerTimeLimit plugin) {
		this.plugin = plugin;
		this.dtf = DateTimeFormatter.ofPattern("e-HH:mm");
	}

	public void start() {
		new BukkitRunnable() {
			@Override
			public void run() {
				execute();
			}

		}.runTaskTimer(plugin, 0L, 1200L); // cada 60 segundos
	}

	public void execute() {
		MainConfigManager mainConfig = plugin.getConfigsManager().getMainConfigManager();
		String resetTime = mainConfig.getResetTime();

		LocalDateTime now = LocalDateTime.now();
		String currentTime = dtf.format(now);
		if (resetTime.equals(currentTime)) {
			// REINICIO DE TIEMPO
			new BukkitRunnable() {
				@Override
				public void run() {
					plugin.getPlayerManager().resetPlayers();
				}

			}.runTaskAsynchronously(plugin);
		}
	}
}

package rush.login.listener;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import rush.login.utils.DataManager;
import rush.login.utils.Logger;

public class PlayerData implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void aoEntrar(PlayerLoginEvent e) {
		String NewPlayer = e.getPlayer().getName();
		File file = DataManager.getFile(NewPlayer.toLowerCase(), "playerdata");
		FileConfiguration config = DataManager.getConfig(file);

		if (file.exists()) {
			String OldPlayer = config.getString("Nick");
			if (!NewPlayer.equals(OldPlayer)) {
				e.setKickMessage("�cO nick '" + NewPlayer
						+ "' n�o pode ser usado pois j� existe um cadastro com o nick '" + OldPlayer + "'." + "\n"
						+ "�c\n�cVoc� deve utilizar a mesma configura��o de caracteres maiusculos"
						+ "�c\n�ce min�sculos para poder acessar o servidor com este nick.");
				e.setResult(Result.KICK_OTHER);
			}
		} else {
			DataManager.createFile(file);
			config.set("Nick", NewPlayer);
			config.set("Senha", "");
			config.set("Registrado", false);
			try {
				config.save(file);
			} catch (IOException ex) {
				Logger.error("Nao foi possivel criar o arquivo " + file.getName() + "!");
			}
		}
	}
}

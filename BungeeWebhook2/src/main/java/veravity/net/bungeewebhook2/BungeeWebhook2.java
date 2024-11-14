package veravity.net.bungeewebhook2;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class BungeeWebhook2 extends Plugin implements Listener {

    private String webhookURL;

    @Override
    public void onEnable() {
        loadConfig();
        getProxy().getPluginManager().registerListener(this, this);
        getLogger().info(ChatColor.GREEN + "BungeeWebhook has been enabled!");

        // Call sendToDiscord() to send a test message
        sendToDiscord("BungeeCord is now running!");

        // Redirect console output to Discord
        redirectConsoleOutput();
    }

    private void redirectConsoleOutput() {
        // Redirect console output to Discord using your preferred method
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "BungeeWebhook has been disabled!");
    }

    private void loadConfig() {
        // Load the webhook URL from the config file
        File configFile = new File(getDataFolder(), "config.properties");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
                saveDefaultConfig(configFile);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe(ChatColor.RED + "Failed to create config file!");
                return;
            }
        }

        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe(ChatColor.RED + "Failed to load config file!");
            return;
        }

        webhookURL = properties.getProperty("webhookURL", "");
        saveConfig(configFile);
    }

    private void saveDefaultConfig(File configFile) {
        Properties properties = new Properties();
        properties.setProperty("webhookURL", "YOUR_DISCORD_WEBHOOK_URL");

        try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
            properties.store(outputStream, "BungeeWebhook Configuration");
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe(ChatColor.RED + "Failed to save default config!");
        }
    }

    private void saveConfig(File configFile) {
        Properties properties = new Properties();
        properties.setProperty("webhookURL", webhookURL);

        try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
            properties.store(outputStream, "BungeeWebhook Configuration");
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe(ChatColor.RED + "Failed to save config!");
        }
    }

    public void sendToDiscord(String message) {
        if (webhookURL.isEmpty()) {
            getLogger().warning("Webhook URL is not set in the config!");
            return;
        }

        OkHttpClient httpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"content\": \"" + message + "\"}";
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(webhookURL)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                getLogger().warning("Failed to send message to Discord: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Failed to send message to Discord: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        // Handle PostLoginEvent
        String playerName = event.getPlayer().getName();
        sendToDiscord("Player " + playerName + " has logged in.");
    }

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        // Handle ServerConnectEvent
        String playerName = event.getPlayer().getName();
        String serverName = event.getTarget().getName();
        sendToDiscord("Player " + playerName + " is connecting to server " + serverName + ".");
    }

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent event) {
        // Handle ServerDisconnectEvent
        String playerName = event.getPlayer().getName();
        sendToDiscord("Player " + playerName + " has been disconnected.");
    }


    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        // Handle ServerSwitchEvent
        String playerName = event.getPlayer().getName();
        String fromServer = event.getFrom().getName();
        String toServer = event.getPlayer().getServer().getInfo().getName();
        sendToDiscord("Player " + playerName + " has switched servers from " + fromServer + " to " + toServer + ".");
    }

    @EventHandler
    public void onServerKick(ServerKickEvent event) {
        // Log server kick event
        String playerName = event.getPlayer().getName();
        String serverName = event.getKickedFrom().getName();
        String reason = ComponentSerializer.toString(event.getKickReasonComponent());
        sendToDiscord("[BungeeCord] Player " + playerName + " got kicked from server: " + serverName + " Reason: " + reason);
    }

}

# BungeeWebhook

BungeeWebhook is a BungeeCord plugin that allows you to send server events and messages to a Discord webhook. It provides a seamless integration between your BungeeCord server and your Discord server, enabling real-time notifications and logging.

## Features

- Send server events (such as player joins, disconnects, kicks, chat messages, etc.) to a Discord webhook.
- Customize the format and content of the messages sent to Discord.
- Easy configuration through a `config.properties` file.
- Lightweight and efficient implementation.

## Requirements

- BungeeCord server (version 1.19)
- Discord server with a webhook URL

## Installation

1. Download the latest release of BungeeWebhook from the [Releases](https://github.com/Recho1235/BungeeCordToDiscord/releases) page.
2. Place the `BungeeWebhook.jar` file in the `plugins` directory of your BungeeCord server.
3. Start or restart your BungeeCord server.
4. Configure the plugin by editing the `config.properties` file located in the `plugins/BungeeWebhook` directory.
5. Set the `webhookURL` property in the `config.properties` file to your Discord webhook URL.
6. Customize the other properties in the `config.properties` file as desired.
7. Save the `config.properties` file.
8. Restart your BungeeCord server.

## Configuration

The `config.properties` file allows you to customize the behavior of the BungeeWebhook plugin. Here are the available configuration options:

- `webhookURL`: The URL of your Discord webhook. Make sure to set this to your actual webhook URL.
- Add any other configuration options specific to your plugin.

## Usage

Once the BungeeWebhook plugin is installed and configured, it will automatically start sending server events and messages to the configured Discord webhook.

Customize the message format and content by modifying the appropriate event handlers in the `BungeeWebhook.java` file.

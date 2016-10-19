package com.compwiz1548.slack.reference;

public final class Messages
{
    public static final class General
    {
        public static final String SERVER_CONNECTED = "Server connected with SlackMC v%s";
        public static final String SERVER_DISCONNECTED = "Server disconnected.";
        public static final String SERVER_STOPPED = "Server stopped.";

        public static final String NO_URL = "No URL specified! Go to the config and specify one, then run '/slackmc reload'";
        public static final String CLIENT_SIDE = "SlackMC is a server-side only mod. Installing it in your client will have no effect.";
        public static final String INCORRECT_WEBHOOK = "Incorrect webhook URL.";

        public static final String CURRENTLY_ONLINE = "Currently Online: %s";
        public static final String NO_ONE_ONLINE = "No players currently online.";

        public static final String PERMISSION_DENIED = "You do not have permission to run this command.";
    }

    public static final class Config
    {
        public static final String WEBHOOK_URL_KEY = "webhook-url";
        public static final String WEBHOOK_URL_COMMENT = "URL from Slack Incoming Webhook.";

        public static final String PORT_KEY = "port";
        public static final String PORT_COMMENT = "Port to listen for messages coming from Slack.";

        public static final String TOKEN_KEY = "token";
        public static final String TOKEN_COMMENT = "Token from Slack Outgoing Webhook.";

        public static final String FORMAT_KEY = "server-format";
        public static final String FORMAT_COMMENT = "Format messages from Slack will appear as on your server.";

        public static final String OPS_KEY = "operators";
        public static final String OPS_COMMENT = "List of Slack usernames of people that are allowed to run commands from Slack. You may remove the example entries.";

        public static final String NAME_KEY = "server-name";
        public static final String NAME_COMMENT = "Enter a name for this server here. This is handy for having multiple servers in the same Slack channel.";
    }

    public static final class Commands
    {
        public static final String BASE_COMMAND_USAGE = "/slackmc <subcommand>";

        public static final String RELOAD_COMMAND_USAGE = "/slackmc reload";
        public static final String RELOAD_COMMAND_SUCCESS = "Reload successful.";

        public static final String URL_COMMAND_USAGE = "/slackmc url <webhook url>";
        public static final String URL_COMMAND_SUCCESS = "Webhook URL set to %s";
        public static final String URL_COMMAND_RESET = "Webhook URL reset.";

        public static final String TOKEN_COMMAND_USAGE = "/slackmc token <token>";
        public static final String TOKEN_COMMAND_SUCCESS = "Token set to %s";
        public static final String TOKEN_COMMAND_RESET = "Token reset.";

        public static final String PORT_COMMAND_USAGE = "/slackmc port <port>";
        public static final String PORT_COMMAND_SUCCESS = "Port set to %s";
        public static final String PORT_COMMAND_RESET = "Port reset.";

        public static final String FORMAT_COMMAND_USAGE = "/slackmc format <format>";
        public static final String FORMAT_COMMAND_CURRENT = "Current Format: %s";
        public static final String FORMAT_COMMAND_SUCCESS = "Format set to %s";
        public static final String FORMAT_COMMAND_RESET = "Format reset.";

        public static final String OP_COMMAND_USAGE = "/slackmc op add/remove <username>";
        public static final String OP_COMMAND_ADD_SUCCESS = "%s successfully added to SlackMC ops list.";
        public static final String OP_COMMAND_REMOVE_SUCCESS = "%s successfully removed from SlackMC ops list.";
    }
}

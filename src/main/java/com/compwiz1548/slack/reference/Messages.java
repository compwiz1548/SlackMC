package com.compwiz1548.slack.reference;

public final class Messages {
    public static final class General {
        private static final String GENERAL_PREFIX = "general.";

        public static final String SERVER_CONNECTED = GENERAL_PREFIX + "server_connected";
        public static final String SERVER_DISCONNECTED = GENERAL_PREFIX + "server_disconnected";
        public static final String SERVER_STOPPED = GENERAL_PREFIX + "server_stopped";

        public static final String NO_URL = GENERAL_PREFIX + "no_url";
        public static final String CLIENT_SIDE = GENERAL_PREFIX + "clientside";
        public static final String INCORRECT_WEBHOOK = GENERAL_PREFIX + "incorrect_webhook";

        public static final String CURRENTLY_ONLINE = GENERAL_PREFIX + "online";
        public static final String NO_ONE_ONLINE = GENERAL_PREFIX + "no_one_online";

        public static final String PERMISSION_DENIED = GENERAL_PREFIX + "permission_denied";
    }

    public static final class Config {
        private static final String CONFIG_PREFIX = "config.";

        public static final String WEBHOOK_URL_KEY = CONFIG_PREFIX + Names.Settings.URL + ".key";
        public static final String WEBHOOK_URL_COMMENT = CONFIG_PREFIX + Names.Settings.URL + ".comment";

        public static final String PORT_KEY = CONFIG_PREFIX + Names.Settings.PORT + ".key";
        public static final String PORT_COMMENT = CONFIG_PREFIX + Names.Settings.PORT + ".comment";

        public static final String TOKEN_KEY = CONFIG_PREFIX + Names.Settings.TOKEN + ".key";
        public static final String TOKEN_COMMENT = CONFIG_PREFIX + Names.Settings.TOKEN + ".comment";

        public static final String FORMAT_KEY = CONFIG_PREFIX + Names.Settings.FORMAT + ".key";
        public static final String FORMAT_COMMENT = CONFIG_PREFIX + Names.Settings.FORMAT + ".comment";

        public static final String OPS_KEY = CONFIG_PREFIX + Names.Settings.OP + ".key";
        public static final String OPS_COMMENT = CONFIG_PREFIX + Names.Settings.OP + ".comment";

        public static final String NAME_KEY = CONFIG_PREFIX + Names.Settings.NAME + ".key";
        public static final String NAME_COMMENT = CONFIG_PREFIX + Names.Settings.NAME + ".comment";
    }

    public static final class Commands {
        private static final String COMMAND_PREFIX = "commands.slackmc.";

        public static final String BASE_COMMAND_USAGE = COMMAND_PREFIX + "usage";

        public static final String RELOAD_COMMAND_USAGE = COMMAND_PREFIX + Names.Commands.RELOAD + ".usage";
        public static final String RELOAD_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Commands.RELOAD + ".success";

        public static final String URL_COMMAND_USAGE = COMMAND_PREFIX + Names.Settings.URL + ".usage";
        public static final String URL_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Settings.URL + ".success";
        public static final String URL_COMMAND_RESET = COMMAND_PREFIX + Names.Settings.URL + ".reset";

        public static final String TOKEN_COMMAND_USAGE = COMMAND_PREFIX + Names.Settings.TOKEN + ".usage";
        public static final String TOKEN_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Settings.TOKEN + ".success";
        public static final String TOKEN_COMMAND_RESET = COMMAND_PREFIX + Names.Settings.TOKEN + ".reset";

        public static final String PORT_COMMAND_USAGE = COMMAND_PREFIX + Names.Settings.PORT + ".usage";
        public static final String PORT_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Settings.PORT + ".success";
        public static final String PORT_COMMAND_RESET = COMMAND_PREFIX + Names.Settings.PORT + ".reset";

        public static final String FORMAT_COMMAND_USAGE = COMMAND_PREFIX + Names.Settings.FORMAT + ".usage";
        public static final String FORMAT_COMMAND_CURRENT = COMMAND_PREFIX + Names.Settings.FORMAT + ".current";
        public static final String FORMAT_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Settings.FORMAT + ".success";
        public static final String FORMAT_COMMAND_RESET = COMMAND_PREFIX + Names.Settings.FORMAT + ".reset";

        public static final String OP_COMMAND_USAGE = COMMAND_PREFIX + Names.Settings.OP + ".usage";
        public static final String OP_COMMAND_ADD_SUCCESS = COMMAND_PREFIX + Names.Settings.OP + ".add.success";
        public static final String OP_COMMAND_REMOVE_SUCCESS = COMMAND_PREFIX + Names.Settings.OP + ".remove.success";
    }
}

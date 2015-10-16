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
        public static final String CURRENT_WEBHOOK = GENERAL_PREFIX + "current_webhook";
    }

    public static final class Commands {
        private static final String COMMAND_PREFIX = "commands.slackmc.";

        public static final String BASE_COMMAND_USAGE = COMMAND_PREFIX + "usage";

        public static final String RELOAD_COMMAND_USAGE = COMMAND_PREFIX + Names.Commands.RELOAD + ".usage";
        public static final String RELOAD_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Commands.RELOAD + ".success";

        public static final String URL_COMMAND_USAGE = COMMAND_PREFIX + Names.Commands.URL + ".usage";
        public static final String URL_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Commands.URL + ".success";
        public static final String URL_COMMAND_RESET = COMMAND_PREFIX + Names.Commands.URL + ".reset";

        public static final String TOKEN_COMMAND_USAGE = COMMAND_PREFIX + Names.Commands.TOKEN + ".usage";
        public static final String TOKEN_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Commands.TOKEN + ".success";
        public static final String TOKEN_COMMAND_RESET = COMMAND_PREFIX + Names.Commands.TOKEN + ".reset";

        public static final String PORT_COMMAND_USAGE = COMMAND_PREFIX + Names.Commands.PORT + ".usage";
        public static final String PORT_COMMAND_SUCCESS = COMMAND_PREFIX + Names.Commands.PORT + ".success";
        public static final String PORT_COMMAND_RESET = COMMAND_PREFIX + Names.Commands.PORT + ".reset";
    }
}

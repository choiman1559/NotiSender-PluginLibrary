package com.noti.plugin.data;

public class PluginConst {
    public static final String SENDER_PACKAGE_NAME = "com.noti.main";
    public static final String REQUIRE_HOST_VERSION = "1.19.0";
    public static final String RECEIVER_ACTION_NAME = "com.noti.plugin.DATA_TRANSFER_PLUGIN";
    public static final String SENDER_ACTION_NAME = "com.noti.plugin.DATA_TRANSFER_HOST";

    public static final String PLUGIN_TITLE = "title";
    public static final String PLUGIN_PACKAGE_NAME = "package_name";
    public static final String PLUGIN_DESCRIPTION = "description";
    public static final String PLUGIN_READY = "is_plugin_ready";
    public static final String PLUGIN_SETTING_ACTIVITY = "setting_activity";
    public static final String PLUGIN_REQUIRE_SENSITIVE_API = "require_sensitive_api";
    public static final String PLUGIN_REQUIRE_VERSION = "require_host_version";

    public static final String DATA_KEY_TYPE = "type";
    public static final String DATA_KEY_IS_SERVICE_RUNNING = "is_running";
    public static final String DATA_KEY_DEVICE_LIST = "device_list";
    public static final String DATA_KEY_EXTRA_DATA = "extra_data";
    public static final String DATA_KEY_REMOTE_ACTION_NAME = "remote_action_name";
    public static final String DATA_KEY_REMOTE_TARGET_DEVICE = "remote_target_device";

    public static final String ACTION_REQUEST_INFO = "request_plugin_info";
    public static final String ACTION_REQUEST_DEVICE_LIST = "request_device_list";
    public static final String ACTION_REQUEST_REMOTE_DATA = "request_remote_data";
    public static final String ACTION_REQUEST_REMOTE_ACTION = "request_remote_action";
    public static final String ACTION_REQUEST_PREFS = "request_preferences";
    public static final String ACTION_REQUEST_SERVICE_STATUS = "request_is_running";

    public static final String ACTION_RESPONSE_INFO = "response_plugin_info";
    public static final String ACTION_RESPONSE_DEVICE_LIST = "response_device_list";
    public static final String ACTION_RESPONSE_REMOTE_DATA = "response_remote_data";
    public static final String ACTION_RESPONSE_PREFS = "response_preferences";
    public static final String ACTION_RESPONSE_SERVICE_STATUS = "response_is_running";

    public static final String ACTION_PUSH_MESSAGE_DATA = "push_message_data";
    public static final String ACTION_PUSH_CALL_DATA = "push_call_data";
    public static final String ACTION_PUSH_EXCEPTION = "push_exception";
}

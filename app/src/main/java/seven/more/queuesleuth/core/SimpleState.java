package seven.more.queuesleuth.core;

public class SimpleState {
    private static int groupPosition;
    private static boolean notificationEnabled = false;

    public static boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public static void setNotificationEnabled(boolean notificationEnabled) {
        SimpleState.notificationEnabled = notificationEnabled;
    }

    public static int getGroupPosition() {
        return groupPosition;
    }

    public static void setGroupPosition(int groupPosition) {
        SimpleState.groupPosition = groupPosition;
    }
}

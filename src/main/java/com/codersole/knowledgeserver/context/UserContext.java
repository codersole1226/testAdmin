package com.codersole.knowledgeserver.context;

import java.util.List;

public class UserContext {

    private static final ThreadLocal<Long> USER_ID_HOLDER =
            new ThreadLocal<>();

    private static final ThreadLocal<String> ROLE_HOLDER =
            new ThreadLocal<>();

    private static final ThreadLocal<List<String>> PERMISSION_HOLDER = new ThreadLocal<>();

    public static void setPermissions(List<String> permissions) {

        PERMISSION_HOLDER.set(permissions);

    }

    public static List<String> getPermissions() {

        return PERMISSION_HOLDER.get();

    }
    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void setRole(String role) {
        ROLE_HOLDER.set(role);
    }

    public static String getRole() {
        return ROLE_HOLDER.get();
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
        ROLE_HOLDER.remove();
        PERMISSION_HOLDER.remove();
    }
}

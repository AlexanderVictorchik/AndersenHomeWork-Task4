package green.router.commandservice;

import java.util.ArrayList;
import java.util.List;

public class CommandService {

    public static UserService getService() {
        UserService userService = new UserServiceImplementationService()
                .getPort(UserService.class);
        return userService;
    }


    public static List<String> getAllUsers() {
        List<User> users = getService().findAll();
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            stringUsers.add(
                    u.getId() + "|"
                            + u.getUsername() + "|"
                            + u.getFirstName() + "|"
                            + u.getLastName() + "|"
                            + u.getRole() + "|"
                            + u.getGroup()
            );
        }

        return stringUsers;
    }


    public static List<String> getUsersByGroup(String group) {
        List<User> users = getService().findByGroup(group);
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            stringUsers.add(
                    u.getId() + "|"
                            + u.getUsername() + "|"
                            + u.getFirstName() + "|"
                            + u.getLastName() + "|"
                            + u.getRole() + "|"
                            + u.getGroup()
            );
        }

        return stringUsers;
    }

    public static List<String> getUsersByRole(String role) {
        List<User> users = getService().findByRole(role);
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            stringUsers.add(
                    u.getId() + "|");
        }

        return stringUsers;
    }

    public static List<String> getUsersByRoleAndGroup(String role, String group) {
        List<User> users = getService().findByRoleAndGroup(role, group);
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            stringUsers.add(
                    u.getId() + "|"
                            + u.getUsername() + "|"
                            + u.getFirstName() + "|"
                            + u.getLastName() + "|"
                            + u.getRole() + "|"
                            + u.getGroup()
            );
        }

        return stringUsers;
    }

    public static List<String> getNotAdminUsers() {
        List<User> users = getService().findAll();
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            if (u.getRole().equals("user") || u.getRole().equals("lead")) {
                stringUsers.add(
                        String.valueOf(u.getId())
                );
            }
        }

        return stringUsers;
    }

    public static List<String> getLeadUserByGroupName(String group) {
        List<User> users = getService().findAll();
        List<String> stringUsers = new ArrayList<>();
        for (User u : users) {
            if (u.getGroup().equals(group) & u.getRole().equals("lead")) {
                stringUsers.add(
                        u.getId() + "|"
                                + u.getUsername() + "|"
                                + u.getFirstName() + "|"
                                + u.getLastName() + "|"
                                + u.getRole() + "|"
                                + u.getGroup()
                );
            }
        }

        return stringUsers;
    }

    public static String getUserById(String userId){
        User user = getService().findById(userId);
        String stringUser=user.getId() + "|"
                + user.getUsername() + "|"
                + user.getFirstName() + "|"
                + user.getLastName() + "|"
                + user.getRole() + "|"
                + user.getGroup();

        return stringUser;
    }


    public static String[] getFieldsFromString(String ingoing) {
        String[] result = ingoing.split("\\|");
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getUserById("665"));
    }

    public static void save(User user) {
        getService().save(user);
    }

}

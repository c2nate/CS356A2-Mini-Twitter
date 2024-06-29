package controller;

import model.User;
import model.UserGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Singleton class to manage users and user groups.
 */
public class UserManager {
    private static UserManager instance = null;
    private Map<String, User> userMap;
    private Map<String, UserGroup> groupMap;

    /**
     * Private constructor to prevent instantiation.
     * Initializes the user and group maps.
     */
    private UserManager() {
        userMap = new HashMap<>();
        groupMap = new HashMap<>();
    }

    /**
     * Returns the singleton instance of UserManager.
     *
     * @return the singleton instance of UserManager
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Retrieves a user by their user ID.
     *
     * @param userID the ID of the user to retrieve
     * @return the User object corresponding to the provided ID, or null if not found
     */
    public User getUserById(String userID) {
        return userMap.get(userID);
    }

    /**
     * Adds a new user to the user map.
     *
     * @param user the User object to add
     */
    public void addUser(User user) {
        userMap.put(user.getUserID(), user);
    }

    /**
     * Adds a new group to the group map.
     *
     * @param group the UserGroup object to add
     */
    public void addGroup(UserGroup group) {
        groupMap.put(group.getGroupID(), group);
    }

    /**
     * Gets the total number of users.
     *
     * @return the total number of users
     */
    public int getTotalUsers() {
        return userMap.size();
    }

    /**
     * Gets the total number of user groups.
     *
     * @return the total number of user groups
     */
    public int getTotalGroups() {
        return groupMap.size();
    }

    /**
     * Gets the total number of unique messages across all users.
     *
     * @return the total number of unique messages
     */
    public int getTotalMessages() {
        Set<String> uniqueMessages = new HashSet<>();
        for (User user : userMap.values()) {
            uniqueMessages.addAll(user.getNewsFeed());
        }
        return uniqueMessages.size();
    }

    /**
     * Calculates the percentage of positive messages across all users.
     * A message is considered positive if it contains any of the words: "good", "great", "excellent".
     *
     * @return the percentage of positive messages
     */
    public double getPositiveMessagesPercentage() {
        int positiveCount = 0;
        Set<String> uniqueMessages = new HashSet<>();
        String[] positiveWords = {"good", "great", "excellent", "perfect", "awesome"};

        for (User user : userMap.values()) {
            uniqueMessages.addAll(user.getNewsFeed());
        }

        for (String message : uniqueMessages) {
            for (String word : positiveWords) {
                if (message.toLowerCase().contains(word)) {
                    positiveCount++;
                    break;
                }
            }
        }

        int totalCount = uniqueMessages.size();
        return totalCount > 0 ? (positiveCount / (double) totalCount) * 100 : 0;
    }

    /**
     * Verifies that all user and group IDs are unique and do not contain spaces.
     *
     * @return true if all IDs are valid, false otherwise
     */
    public boolean verifyIDs() {
        Set<String> allIDs = new HashSet<>();
        boolean allValid = true;
    
        // Check User IDs
        for (User user : userMap.values()) {
            if (user.getUserID().contains(" ") || !allIDs.add(user.getUserID())) {
                allValid = false;
                break;
            }
        }
    
        // Check Group IDs
        for (UserGroup group : groupMap.values()) {
            if (group.getGroupID().contains(" ") || !allIDs.add(group.getGroupID())) {
                allValid = false;
                break;
            }
        }
    
        return allValid;
    }

    /**
     * Retrieves the ID of the user who was last updated.
     *
     * @return the ID of the user who was last updated
     */
    public String getLastUpdatedUser() {
        long maxUpdateTime = Long.MIN_VALUE;
        String lastUpdatedUser = null;
    
        for (User user : userMap.values()) {
            if (user.getLastUpdateTime() > maxUpdateTime) {
                maxUpdateTime = user.getLastUpdateTime();
                lastUpdatedUser = user.getUserID();
            }
        }
    
        return lastUpdatedUser;
    }
}

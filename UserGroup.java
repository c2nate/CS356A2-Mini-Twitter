package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of users or other user groups.
 */
public class UserGroup {
    private String groupID;
    private List<Object> members; // Members can be User or UserGroup
    private long creationTime;

    /**
     * Constructs a new UserGroup with the specified groupID.
     * 
     * @param groupID the unique ID of the group
     */
    public UserGroup(String groupID) {
        this.groupID = groupID;
        this.members = new ArrayList<>();
        this.creationTime = System.currentTimeMillis(); // Set creation time
    }

    /**
     * Gets the group's ID.
     * 
     * @return the groupID
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Gets the members of the group.
     * 
     * @return the list of members
     */
    public List<Object> getMembers() {
        return members;
    }

    /**
     * Adds a user or another group to the group.
     * 
     * @param userOrGroup the user or group to add as a member
     */
    public void addUser(Object userOrGroup) {
        if (!members.contains(userOrGroup)) {
            members.add(userOrGroup);
        }
    }

    /**
     * Gets the creation time of the user.
     * 
     * @return the creation time in milliseconds since epoch
     */
    public long getCreationTime() {
        return creationTime;
    }
    
}

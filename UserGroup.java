package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of users or other user groups.
 */
public class UserGroup {
    private String groupID;
    private List<Object> members; // Members can be User or UserGroup

    /**
     * Constructs a new UserGroup with the specified groupID.
     * 
     * @param groupID the unique ID of the group
     */
    public UserGroup(String groupID) {
        this.groupID = groupID;
        this.members = new ArrayList<>();
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
}

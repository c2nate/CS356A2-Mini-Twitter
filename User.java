package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system.
 */
public class User {
    private String userID;
    private List<String> followings;
    private List<String> newsFeed;
    private List<User> followers; // List of observers

    /**
     * Constructs a new User with the specified userID.
     * 
     * @param userID the unique ID of the user
     */
    public User(String userID) {
        this.userID = userID;
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

    /**
     * Accepts a visitor that processes the user's messages.
     * 
     * @param visitor the visitor that processes the messages
     */
    public void acceptVisitor(MessageVisitor visitor) {
        for (String message : newsFeed) {
            visitor.visitMessage(message);
        }
    }

    /**
     * Calculates the percentage of positive messages in the user's news feed.
     * 
     * @return the percentage of positive messages
     */
    public double getPositiveMessagesPercentage() {
        int totalCount = newsFeed.size();
        if (totalCount == 0) return 0;

        PositivityVisitor visitor = new PositivityVisitor();
        acceptVisitor(visitor);

        int positiveCount = visitor.getPositiveCount();
        return (positiveCount / (double) totalCount) * 100;
    }

    /**
     * Gets the user's ID.
     * 
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the list of users this user is following.
     * 
     * @return the list of followings
     */
    public List<String> getFollowings() {
        return followings;
    }

    /**
     * Follows another user.
     * 
     * @param userToFollow the user to follow
     */
    public void followUser(User userToFollow) {
        if (!followings.contains(userToFollow.getUserID())) {
            followings.add(userToFollow.getUserID());
            userToFollow.addFollower(this); // Update the followed user's followers list
        }
    }

    /**
     * Adds a follower to this user.
     * 
     * @param follower the user to add as a follower
     */
    public void addFollower(User follower) {
        if (!followers.contains(follower)) {
            followers.add(follower);
        }
    }

    /**
     * Removes a follower from this user.
     * 
     * @param follower the user to remove from followers
     */
    public void removeFollower(User follower) {
        followers.remove(follower);
    }

    /**
     * Notifies all followers when a new tweet is posted.
     * 
     * @param message the message to notify followers about
     */
    private void notifyFollowers(String message) {
        for (User follower : followers) {
            follower.updateNewsFeed(userID + ": " + message);
        }
    }

    /**
     * Updates the user's news feed with a new message.
     * 
     * @param message the message to add to the news feed
     */
    public void updateNewsFeed(String message) {
        newsFeed.add(message);
    }

    /**
     * Posts a new tweet and notifies all followers.
     * 
     * @param tweet the tweet to post
     */
    public void postTweet(String tweet) {
        newsFeed.add(userID + ": " + tweet);
        notifyFollowers(tweet); // Notify followers when a new tweet is posted
    }

    /**
     * Gets the user's news feed.
     * 
     * @return the news feed
     */
    public List<String> getNewsFeed() {
        return newsFeed;
    }
}

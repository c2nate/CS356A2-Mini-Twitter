package view;

import model.User;
import javax.swing.*;

import controller.UserManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a graphical user interface for a user in a social media platform.
 *
 * @param user the user for whom this view is created
 */

public class UserView extends JFrame {
    private User user;
    private JTextField followUserField;
    private JTextArea tweetMessageArea;
    private DefaultListModel<String> followingModel;
    private DefaultListModel<String> newsFeedModel;
    private JList<String> followingList;
    private JList<String> newsFeedList;
    private Timer newsFeedTimer;

    /**
     * Constructs a new UserView object.
     *
     * @param user the user for whom this view is created
     */

    public UserView(User user) {
        this.user = user;
        setTitle("User View - " + user.getUserID());
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel followPanel = new JPanel(new BorderLayout(5, 5));
        JLabel followLabel = new JLabel("Enter username:");
        followPanel.add(followLabel, BorderLayout.WEST);
        followUserField = new JTextField();
        followPanel.add(followUserField, BorderLayout.CENTER);
        JButton followButton = new JButton("Follow User");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = followUserField.getText();
                if (!userID.isEmpty()) {
                    User userToFollow = UserManager.getInstance().getUserById(userID);
                    if (userToFollow != null) {
                        user.followUser(userToFollow);
                        updateFollowingList();
                        followUserField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(UserView.this, "User not found!");
                    }
                }
            }
        });
        followPanel.add(followButton, BorderLayout.EAST);
        add(followPanel, BorderLayout.NORTH);

        followingModel = new DefaultListModel<>();
        followingList = new JList<>(followingModel);
        JScrollPane followingScrollPane = new JScrollPane(followingList);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new JLabel("Following:"));
        centerPanel.add(followingScrollPane);

        JPanel tweetPanel = new JPanel();
        tweetPanel.setLayout(new BorderLayout(5, 5));
        JLabel tweetLabel = new JLabel("Enter your tweet:");
        tweetPanel.add(tweetLabel, BorderLayout.NORTH);
        tweetMessageArea = new JTextArea(3, 20);
        JScrollPane tweetScrollPane = new JScrollPane(tweetMessageArea);
        tweetPanel.add(tweetScrollPane, BorderLayout.CENTER);
        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tweet = tweetMessageArea.getText();
                if (!tweet.isEmpty()) {
                    user.postTweet(tweet);
                    updateNewsFeed();
                    tweetMessageArea.setText("");
                }
            }
        });
        tweetPanel.add(postTweetButton, BorderLayout.SOUTH);

        centerPanel.add(tweetPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel newsFeedPanel = new JPanel();
        newsFeedPanel.setLayout(new BorderLayout(5, 5));
        JLabel newsFeedLabel = new JLabel("News Feed:");
        newsFeedPanel.add(newsFeedLabel, BorderLayout.NORTH);
        newsFeedModel = new DefaultListModel<>();
        newsFeedList = new JList<>(newsFeedModel);
        JScrollPane newsFeedScrollPane = new JScrollPane(newsFeedList);
        newsFeedPanel.add(newsFeedScrollPane, BorderLayout.CENTER);
        add(newsFeedPanel, BorderLayout.EAST);

        updateFollowingList();

        // Initialize and start the timer to update news feed periodically
        newsFeedTimer = new Timer( 1500, e -> updateNewsFeed()); // Update every 1500 milliseconds
        newsFeedTimer.start();

        updateFollowingList();
        updateNewsFeed();
    }

    /**
     * Updates the list of users that the current user is following in the UI.
     */

    private void updateFollowingList() {
        followingModel.clear();
        for (String followedUser : user.getFollowings()) {
            followingModel.addElement(followedUser);
        }
    }

    /**
     * Updates the news feed in the UI with the latest messages.
     */

    private void updateNewsFeed() {
        newsFeedModel.clear();
        for (String message : user.getNewsFeed()) {
            newsFeedModel.addElement(message);
        }
    }
}

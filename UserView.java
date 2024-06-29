package view;

import model.User;
import javax.swing.*;
import controller.UserManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The UserView class represents the UI for a user in the mini Twitter application.
 * It allows users to follow other users, post tweets, and view their news feed.
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
    private JLabel creationTimeLabel;
    private JLabel lastUpdateTimeLabel;

    /**
     * Constructs a UserView for the specified user.
     *
     * @param user the user for whom the view is created
     */
    public UserView(User user) {
        this.user = user;
        setTitle("User View - " + user.getUserID());
        setSize(600, 400); // test best layouts
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // initialize date formatter
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");

        JPanel followPanel = new JPanel(new BorderLayout(10, 10));
        followPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel followLabel = new JLabel("Enter username:");
        followPanel.add(followLabel, BorderLayout.WEST);
        followUserField = new JTextField(15); // Set preferred size
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

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Following Panel
        JPanel followingPanel = new JPanel(new BorderLayout(5, 5));
        JLabel followingLabel = new JLabel("Following:");
        followingModel = new DefaultListModel<>();
        followingList = new JList<>(followingModel);
        JScrollPane followingScrollPane = new JScrollPane(followingList);
        followingPanel.add(followingLabel, BorderLayout.NORTH);
        followingPanel.add(followingScrollPane, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        centerPanel.add(followingPanel, gbc);

        // Tweet Panel
        JPanel tweetPanel = new JPanel(new BorderLayout(5, 5));
        JLabel tweetLabel = new JLabel("Enter your tweet:");
        tweetMessageArea = new JTextArea(5, 20);
        JScrollPane tweetScrollPane = new JScrollPane(tweetMessageArea);
        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tweet = tweetMessageArea.getText();
                if (!tweet.isEmpty()) {
                    user.postTweet(tweet);
                    updateNewsFeed();
                    updateLastUpdateTime();
                    tweetMessageArea.setText("");
                }
            }
        });
        tweetPanel.add(tweetLabel, BorderLayout.NORTH);
        tweetPanel.add(tweetScrollPane, BorderLayout.CENTER);
        tweetPanel.add(postTweetButton, BorderLayout.SOUTH);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        centerPanel.add(tweetPanel, gbc);

        // News Feed Panel
        JPanel newsFeedPanel = new JPanel(new BorderLayout(5, 5));
        JLabel newsFeedLabel = new JLabel("News Feed:");
        newsFeedModel = new DefaultListModel<>();
        newsFeedList = new JList<>(newsFeedModel);
        JScrollPane newsFeedScrollPane = new JScrollPane(newsFeedList);
        newsFeedPanel.add(newsFeedLabel, BorderLayout.NORTH);
        newsFeedPanel.add(newsFeedScrollPane, BorderLayout.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        centerPanel.add(newsFeedPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Info Panel at the Bottom
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        creationTimeLabel = new JLabel("Creation Time: " + dateFormatter.format(new Date(user.getCreationTime())));
        lastUpdateTimeLabel = new JLabel("Last Update Time: ");
        infoPanel.add(creationTimeLabel);
        infoPanel.add(lastUpdateTimeLabel);

        add(infoPanel, BorderLayout.SOUTH);

        updateFollowingList();

        // Initialize and start the timer to update news feed periodically
        newsFeedTimer = new Timer(1000, e -> updateNewsFeed());
        newsFeedTimer.start();

        updateFollowingList();
        updateNewsFeed();
        updateLastUpdateTime();
    }

    /**
     * Updates the list of users the current user is following.
     */
    private void updateFollowingList() {
        followingModel.clear();
        for (String followedUser : user.getFollowings()) {
            followingModel.addElement(followedUser);
        }
    }

    /**
     * Updates the news feed of the current user.
     */
    private void updateNewsFeed() {
        newsFeedModel.clear();
        for (String message : user.getNewsFeed()) {
            newsFeedModel.addElement(message);
            updateLastUpdateTime();
        }
    }

    /**
     * updates the last update time label to the current time.
     */
    private void updateLastUpdateTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
        lastUpdateTimeLabel.setText("Last Update Time: " + dateFormatter.format(new Date(user.getLastUpdateTime())));
    }
}

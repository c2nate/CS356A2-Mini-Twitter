package view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import controller.UserManager;
import model.User;
import model.UserGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The AdminControlPanel class represents the admin control panel GUI for managing users and groups.
 */
public class AdminControlPanel extends JFrame {
    private static AdminControlPanel instance = null;
    private JTextField userIdField;
    private JTextField groupIdField;
    private JTree userTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    /**
     * Constructs a new AdminControlPanel.
     */
    private AdminControlPanel() {
        setTitle("Admin Control Panel");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        userTree = new JTree(treeModel);
        userTree.setCellRenderer(new CustomTreeCellRenderer()); // Set custom cell renderer
        JScrollPane treeView = new JScrollPane(userTree);

        userTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath selectedPath = userTree.getPathForLocation(e.getX(), e.getY());
                    if (selectedPath != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                        Object selectedObject = selectedNode.getUserObject();
                        if (selectedObject instanceof String) {
                            String userID = (String) selectedObject;
                            User user = UserManager.getInstance().getUserById(userID);
                            if (user != null) {
                                openUserView(user);
                            }
                        }
                    }
                }
            }
        });

        //all the buttons and panels
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("User ID:"), gbc);

        userIdField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(userIdField, gbc);

        JButton addUserButton = new JButton("Add User");
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        controlPanel.add(addUserButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Group ID:"), gbc);

        groupIdField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        controlPanel.add(groupIdField, gbc);

        JButton addGroupButton = new JButton("Add Group");
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        controlPanel.add(addGroupButton, gbc);

        JButton showUserTotalButton = new JButton("Show User Total");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        controlPanel.add(showUserTotalButton, gbc);

        JButton showGroupTotalButton = new JButton("Show Group Total");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        controlPanel.add(showGroupTotalButton, gbc);

        JButton showMessagesTotalButton = new JButton("Show Messages Total");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        controlPanel.add(showMessagesTotalButton, gbc);

        JButton showPositivePercentageButton = new JButton("Show Positive Percentage");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        controlPanel.add(showPositivePercentageButton, gbc);

        JButton verifyIDsButton = new JButton("Verify IDs");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(verifyIDsButton, gbc);

        JButton showLastUpdatedUserButton = new JButton("Show Last Updated User");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(showLastUpdatedUserButton, gbc);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userIdField.getText();
                if (!userID.isEmpty()) {
                    User user = new User(userID);
                    UserManager.getInstance().addUser(user);
                    DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(userID);
                    rootNode.add(userNode);
                    treeModel.reload();
                    userIdField.setText("");
                }
            }
        });

        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupID = groupIdField.getText();
                if (!groupID.isEmpty()) {
                    UserGroup group = new UserGroup(groupID);
                    UserManager.getInstance().addGroup(group);
                    DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode("Group: " + groupID);
                    rootNode.add(groupNode);
                    treeModel.reload();
                    groupIdField.setText("");
                }
            }
        });

        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalUsers = UserManager.getInstance().getTotalUsers();
                JOptionPane.showMessageDialog(AdminControlPanel.this, "Total Users: " + totalUsers);
            }
        });

        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalGroups = UserManager.getInstance().getTotalGroups();
                JOptionPane.showMessageDialog(AdminControlPanel.this, "Total Groups: " + totalGroups);
            }
        });

        showMessagesTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalMessages = UserManager.getInstance().getTotalMessages();
                JOptionPane.showMessageDialog(AdminControlPanel.this, "Total Messages: " + totalMessages);
            }
        });

        showPositivePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double positivePercentage = UserManager.getInstance().getPositiveMessagesPercentage();
                JOptionPane.showMessageDialog(AdminControlPanel.this, "Positive Messages Percentage: " + positivePercentage + "%");
            }
        });

        showLastUpdatedUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lastUpdatedUser = UserManager.getInstance().getLastUpdatedUser();
                JOptionPane.showMessageDialog(AdminControlPanel.this, "Last Updated User ID: " + lastUpdatedUser);
            }
        });

        verifyIDsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = UserManager.getInstance().verifyIDs();
                if (valid) {
                    JOptionPane.showMessageDialog(AdminControlPanel.this, "All IDs are valid.");
                } else {
                    JOptionPane.showMessageDialog(AdminControlPanel.this, "Some IDs are invalid.");
                }
            }
        });

        add(treeView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Opens a user view for the specified user.
     *
     * @param user the user to view
     */
    private void openUserView(User user) {
        UserView userView = new UserView(user);
        userView.setVisible(true);
    }

    /**
     * Returns the singleton instance of AdminControlPanel.
     *
     * @return the singleton instance
     */
    public static AdminControlPanel getInstance() {
        if (instance == null) {
            instance = new AdminControlPanel();
        }
        return instance;
    }

    /**
     * The main method to run the AdminControlPanel application. Entry into the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminControlPanel.getInstance().setVisible(true);
            }
        });
    }

    /**
     * Custom tree cell renderer to set different icons for users and groups.
     */
    private class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        private Icon groupIcon; // Icon for groups

        /**
         * Constructs a new CustomTreeCellRenderer.
         */
        public CustomTreeCellRenderer() {
            // fetch the folder icon from UIManager
            groupIcon = UIManager.getIcon("Tree.closedIcon");
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            // check if the node is a group node and set the icon accordingly
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof String) {
                String nodeType = (String) node.getUserObject();
                if (nodeType.startsWith("Group")) { 
                    setIcon(groupIcon);
                } else {
                    setIcon(null); // reset to default icon for users
                }
            }

            return this;
        }
    }
}

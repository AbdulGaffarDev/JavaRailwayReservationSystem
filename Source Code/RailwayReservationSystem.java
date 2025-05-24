import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class RailwayReservationSystem {
    private JFrame frame;
    private List<Passenger> passengers = new ArrayList<>();
    private int pnum = 1;
    private Map<String, Integer> stationDistances = new HashMap<>();
       private final double AC_RATE = 6.0;
    private final double FIRST_RATE = 5.0;
    private final double SLEEPER_RATE = 4.0;

    // Seat availability tracking
    private int maxAC = 75;
    private int maxFirst = 125;
    private int maxSleeper = 175;

    class Passenger {
        int pno;
        String name;
        String phno;
        int age;
        int cl;
        String departure;
        String destination;
        int distance;
        double fare;

        public Passenger(int pno, String name, String phno, int age, int cl,
                         String departure, String destination, int distance, double fare) {
            this.pno = pno;
            this.name = name;
            this.phno = phno;
            this.age = age;
            this.cl = cl;
            this.departure = departure;
            this.destination = destination;
            this.distance = distance;
            this.fare = fare;
        }
    }

    public RailwayReservationSystem() {
        stationDistances.put("Karachi", 0);
        stationDistances.put("Hyderabad", 210);
        stationDistances.put("Sadiqabad", 670);
        stationDistances.put("Multan", 925);
        stationDistances.put("Lahore", 1270);
        stationDistances.put("Rawalpindi", 1440);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new RailwayReservationSystem().initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        frame = new JFrame("Railway Reservation System");
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        showMainMenu();
        frame.setVisible(true);
    }

    private void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Railway Reservation System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Menu Options
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));
        menuPanel.setBackground(new Color(240, 240, 240));

        JButton bookButton = createMenuButton("Book Ticket");
        bookButton.addActionListener(e -> showBookTicket());

        JButton cancelButton = createMenuButton("Cancel Ticket");
        cancelButton.addActionListener(e -> showCancelTicket());

        JButton searchButton = createMenuButton("Search Passenger");
        searchButton.addActionListener(e -> showSearchPassenger());

        JButton chartButton = createMenuButton("Reservation Chart");
        chartButton.addActionListener(e -> showReservationChart());

        JButton unbookedButton = createMenuButton("Unbooked Tickets");
        unbookedButton.addActionListener(e -> showUnbookedTickets());

        JButton exitButton = createMenuButton("Exit");
        exitButton.addActionListener(e -> showExit());

        menuPanel.add(bookButton);
        menuPanel.add(cancelButton);
        menuPanel.add(searchButton);
        menuPanel.add(chartButton);
        menuPanel.add(unbookedButton);
        menuPanel.add(exitButton);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(0, 153, 76));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        return button;
    }

    private void showBookTicket() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Book Ticket");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Form Panel with improved layout
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        formPanel.setBackground(new Color(240, 240, 240));

        // Class Selection
        JPanel classPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        classPanel.setBackground(new Color(240, 240, 240));
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        classLabel.setPreferredSize(new Dimension(150, 30));
        JComboBox<String> classCombo = new JComboBox<>(new String[]{"AC", "First", "Sleeper"});
        classCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        classCombo.setPreferredSize(new Dimension(200, 30));
        classPanel.add(classLabel);
        classPanel.add(classCombo);
        formPanel.add(classPanel);

        // Departure and Destination
        JPanel departurePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        departurePanel.setBackground(new Color(240, 240, 240));
        JLabel departureLabel = new JLabel("Departure:");
        departureLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        departureLabel.setPreferredSize(new Dimension(150, 30));
        JComboBox<String> departureCombo = new JComboBox<>(stationDistances.keySet().toArray(new String[0]));
        departureCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        departureCombo.setPreferredSize(new Dimension(200, 30));
        departurePanel.add(departureLabel);
        departurePanel.add(departureCombo);
        formPanel.add(departurePanel);

        JPanel destinationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        destinationPanel.setBackground(new Color(240, 240, 240));
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        destinationLabel.setPreferredSize(new Dimension(150, 30));
        JComboBox<String> destinationCombo = new JComboBox<>(stationDistances.keySet().toArray(new String[0]));
        destinationCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        destinationCombo.setPreferredSize(new Dimension(200, 30));
        destinationPanel.add(destinationLabel);
        destinationPanel.add(destinationCombo);
        formPanel.add(destinationPanel);

        // Distance and Fare Display
        JPanel distancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        distancePanel.setBackground(new Color(240, 240, 240));
        JLabel distanceLabel = new JLabel("Distance (km):");
        distanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        distanceLabel.setPreferredSize(new Dimension(150, 30));
        JLabel distanceValue = new JLabel("0");
        distanceValue.setFont(new Font("Arial", Font.BOLD, 18));
        distanceValue.setPreferredSize(new Dimension(200, 30));
        distancePanel.add(distanceLabel);
        distancePanel.add(distanceValue);
        formPanel.add(distancePanel);

        JPanel farePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        farePanel.setBackground(new Color(240, 240, 240));
        JLabel fareLabel = new JLabel("Fare per ticket:");
        fareLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        fareLabel.setPreferredSize(new Dimension(150, 30));
        JLabel fareValue = new JLabel("Rs. 0.00");
        fareValue.setFont(new Font("Arial", Font.BOLD, 18));
        fareValue.setPreferredSize(new Dimension(200, 30));
        farePanel.add(fareLabel);
        farePanel.add(fareValue);
        formPanel.add(farePanel);

        // Passenger Details
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        namePanel.setBackground(new Color(240, 240, 240));
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setPreferredSize(new Dimension(150, 30));
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameField.setPreferredSize(new Dimension(200, 30));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        formPanel.add(namePanel);

        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        agePanel.setBackground(new Color(240, 240, 240));
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        ageLabel.setPreferredSize(new Dimension(150, 30));
        JTextField ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 18));
        ageField.setPreferredSize(new Dimension(200, 30));
        agePanel.add(ageLabel);
        agePanel.add(ageField);
        formPanel.add(agePanel);

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        phonePanel.setBackground(new Color(240, 240, 240));
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        phoneLabel.setPreferredSize(new Dimension(150, 30));
        JTextField phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 18));
        phoneField.setPreferredSize(new Dimension(200, 30));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        formPanel.add(phonePanel);

        JPanel ticketsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        ticketsPanel.setBackground(new Color(240, 240, 240));
        JLabel ticketsLabel = new JLabel("Number of Tickets:");
        ticketsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        ticketsLabel.setPreferredSize(new Dimension(150, 30));
        JSpinner ticketsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ticketsSpinner.setFont(new Font("Arial", Font.PLAIN, 18));
        ((JSpinner.DefaultEditor) ticketsSpinner.getEditor()).getTextField().setColumns(5);
        ticketsSpinner.setPreferredSize(new Dimension(200, 30));
        ticketsPanel.add(ticketsLabel);
        ticketsPanel.add(ticketsSpinner);
        formPanel.add(ticketsPanel);

        // Add action listener to calculate distance when stations change
        ActionListener distanceCalculator = e -> {
            String departure = (String) departureCombo.getSelectedItem();
            String destination = (String) destinationCombo.getSelectedItem();
            if (departure.equals(destination)) {
                distanceValue.setText("0");
                fareValue.setText("Rs. 0.00");
                return;
            }
            int distance = Math.abs(stationDistances.get(destination) - stationDistances.get(departure));
            distanceValue.setText(String.valueOf(distance));

            int cl = classCombo.getSelectedIndex() + 1;
            double rate = 0;
            switch (cl) {
                case 1: rate = AC_RATE; break;
                case 2: rate = FIRST_RATE; break;
                case 3: rate = SLEEPER_RATE; break;
            }
            double fare = distance * rate;
            fareValue.setText(String.format("Rs. %.2f", fare));
        };

        departureCombo.addActionListener(distanceCalculator);
        destinationCombo.addActionListener(distanceCalculator);
        classCombo.addActionListener(distanceCalculator);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton bookButton = new JButton("Book");
        styleButton(bookButton, new Color(0, 153, 76), new Dimension(150, 45));
        bookButton.addActionListener(e -> {
            int cl = classCombo.getSelectedIndex() + 1;
            String name = nameField.getText();
            String phno = phoneField.getText();
            String departure = (String) departureCombo.getSelectedItem();
            String destination = (String) destinationCombo.getSelectedItem();

            if (departure.equals(destination)) {
                JOptionPane.showMessageDialog(frame, "Departure and destination cannot be same", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int distance = Integer.parseInt(distanceValue.getText());
            double farePerTicket = Double.parseDouble(fareValue.getText().substring(3));
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int t = (int) ticketsSpinner.getValue();

            if (name.isEmpty() || phno.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check seat availability
            int ticketAvailable = 0;
            if (cl == 1 && maxAC >= t) {
                ticketAvailable = 1;
            } else if (cl == 2 && maxFirst >= t) {
                ticketAvailable = 1;
            } else if (cl == 3 && maxSleeper >= t) {
                ticketAvailable = 1;
            }

            if (ticketAvailable == 1) {
                // Calculate total fare
                double totalFare = farePerTicket * t;

                // Add passengers
                for (int i = 0; i < t; i++) {
                    passengers.add(new Passenger(pnum++, name, phno, age, cl,
                            departure, destination, distance, farePerTicket));
                }

                // Update seat availability
                if (cl == 1) {
                    maxAC -= t;
                } else if (cl == 2) {
                    maxFirst -= t;
                } else if (cl == 3) {
                    maxSleeper -= t;
                }

                JOptionPane.showMessageDialog(frame,
                        String.format("Ticket successfully booked!\n%s to %s (%d km)\n%d Tickets - %s Class\nTotal Fare: Rs. %.2f",
                                departure, destination, distance, t, getClassName(cl), totalFare));
            } else {
                JOptionPane.showMessageDialog(frame, "Not enough tickets available in this class", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(204, 0, 0), new Dimension(150, 45));
        backButton.addActionListener(e -> showMainMenu());

        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showCancelTicket() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Cancel Ticket");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        formPanel.setBackground(new Color(240, 240, 240));

        JLabel pnoLabel = new JLabel("Passenger Number:");
        pnoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField pnoField = new JTextField(15);
        pnoField.setFont(new Font("Arial", Font.PLAIN, 18));
        pnoField.setPreferredSize(new Dimension(200, 35));

        formPanel.add(pnoLabel);
        formPanel.add(pnoField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton cancelButton = new JButton("Cancel Ticket");
        styleButton(cancelButton, new Color(204, 0, 0), new Dimension(180, 45));
        cancelButton.addActionListener(e -> {
            try {
                int p = Integer.parseInt(pnoField.getText());
                boolean passengerFound = false;
                Passenger passengerToRemove = null;

                for (Passenger passenger : passengers) {
                    if (passenger.pno == p) {
                        passengerFound = true;
                        passengerToRemove = passenger;
                        break;
                    }
                }

                if (passengerFound) {
                    passengers.remove(passengerToRemove);
                    double refund = 0;
                    if (passengerToRemove.cl == 1) {
                        maxAC++;
                        refund = passengerToRemove.fare * 0.8;
                    } else if (passengerToRemove.cl == 2) {
                        maxFirst++;
                        refund = passengerToRemove.fare * 0.75;
                    } else if (passengerToRemove.cl == 3) {
                        maxSleeper++;
                        refund = passengerToRemove.fare * 0.7;
                    }

                    JOptionPane.showMessageDialog(frame,
                            String.format("Ticket successfully cancelled!\n%s to %s (%d km)\nRefund Amount: Rs. %.2f",
                                    passengerToRemove.departure, passengerToRemove.destination,
                                    passengerToRemove.distance, refund));
                } else {
                    JOptionPane.showMessageDialog(frame, "No such passenger found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid passenger number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(0, 102, 204), new Dimension(150, 45));
        backButton.addActionListener(e -> showMainMenu());

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showSearchPassenger() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Search Passenger");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        searchPanel.setBackground(new Color(240, 240, 240));

        JLabel pnoLabel = new JLabel("Passenger Number:");
        pnoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField pnoField = new JTextField(15);
        pnoField.setFont(new Font("Arial", Font.PLAIN, 18));
        pnoField.setPreferredSize(new Dimension(200, 25));

        searchPanel.add(pnoLabel);
        searchPanel.add(pnoField);

        // Results Panel
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        scrollPane.setPreferredSize(new Dimension(700, 300));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton searchButton = new JButton("Search");
        styleButton(searchButton, new Color(0, 153, 76), new Dimension(150, 45));
        searchButton.addActionListener(e -> {
            try {
                int p = Integer.parseInt(pnoField.getText());
                boolean passengerFound = false;
                resultArea.setText("");

                for (Passenger passenger : passengers) {
                    if (passenger.pno == p) {
                        passengerFound = true;
                        resultArea.append("Passenger Details Found:\n");
                        resultArea.append(String.format("%-15s: %d\n", "Passenger No", passenger.pno));
                        resultArea.append(String.format("%-15s: %s\n", "Name", passenger.name));
                        resultArea.append(String.format("%-15s: %s\n", "Class", getClassName(passenger.cl)));
                        resultArea.append(String.format("%-15s: %s\n", "Phone", passenger.phno));
                        resultArea.append(String.format("%-15s: %d\n", "Age", passenger.age));
                        resultArea.append(String.format("%-15s: %s to %s\n", "Journey", passenger.departure, passenger.destination));
                        resultArea.append(String.format("%-15s: %d km\n", "Distance", passenger.distance));
                        resultArea.append(String.format("%-15s: Rs. %.2f\n", "Fare", passenger.fare));
                        break;
                    }
                }

                if (!passengerFound) {
                    resultArea.setText("No such passenger found");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid passenger number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(0, 102, 204), new Dimension(150, 45));
        backButton.addActionListener(e -> showMainMenu());

        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);

        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showReservationChart() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Reservation Chart");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Tabbed Pane for different classes
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        // AC Class Tab
        JPanel acPanel = new JPanel(new BorderLayout());
        acPanel.setBackground(new Color(240, 240, 240));
        JTextArea acTextArea = createChartTextArea();
        filterPassengersByClass(acTextArea, 1);
        JScrollPane acScrollPane = new JScrollPane(acTextArea);
        acScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        acPanel.add(acScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("AC Class", acPanel);

        // First Class Tab
        JPanel firstPanel = new JPanel(new BorderLayout());
        firstPanel.setBackground(new Color(240, 240, 240));
        JTextArea firstTextArea = createChartTextArea();
        filterPassengersByClass(firstTextArea, 2);
        JScrollPane firstScrollPane = new JScrollPane(firstTextArea);
        firstScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        firstPanel.add(firstScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("First Class", firstPanel);

        // Sleeper Class Tab
        JPanel sleeperPanel = new JPanel(new BorderLayout());
        sleeperPanel.setBackground(new Color(240, 240, 240));
        JTextArea sleeperTextArea = createChartTextArea();
        filterPassengersByClass(sleeperTextArea, 3);
        JScrollPane sleeperScrollPane = new JScrollPane(sleeperTextArea);
        sleeperScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sleeperPanel.add(sleeperScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Sleeper Class", sleeperPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(0, 102, 204), new Dimension(150, 45));
        backButton.addActionListener(e -> showMainMenu());
        buttonPanel.add(backButton);

        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JTextArea createChartTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textArea.append(String.format("%-8s %-20s %-10s %-15s %-15s %-10s %-10s\n",
                "P.No", "Name", "Age", "From", "To", "Distance", "Fare"));
        textArea.append("-------------------------------------------------------------------------------\n");
        return textArea;
    }

    private void filterPassengersByClass(JTextArea textArea, int cl) {
        for (Passenger passenger : passengers) {
            if (passenger.cl == cl) {
                textArea.append(String.format("%-8d %-20s %-10d %-15s %-15s %-10d Rs. %-10.2f\n",
                        passenger.pno, passenger.name, passenger.age,
                        passenger.departure, passenger.destination,
                        passenger.distance, passenger.fare));
            }
        }
    }

    private void showUnbookedTickets() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel headerLabel = new JLabel("Unbooked Tickets Status");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Status Panel
        JPanel statusPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        statusPanel.setBackground(new Color(240, 240, 240));

        JLabel acLabel = new JLabel("AC Class: " + maxAC + " tickets available");
        acLabel.setFont(new Font("Arial", Font.BOLD, 20));
        acLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel firstLabel = new JLabel("First Class: " + maxFirst + " tickets available");
        firstLabel.setFont(new Font("Arial", Font.BOLD, 20));
        firstLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel sleeperLabel = new JLabel("Sleeper Class: " + maxSleeper + " tickets available");
        sleeperLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sleeperLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusPanel.add(acLabel);
        statusPanel.add(firstLabel);
        statusPanel.add(sleeperLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(0, 102, 204), new Dimension(150, 45));
        backButton.addActionListener(e -> showMainMenu());
        buttonPanel.add(backButton);

        contentPanel.add(statusPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showExit() {
        JOptionPane.showMessageDialog(frame,
                "Thank you for using Railway Reservation System!\n\nA project by:\n· Abdul Gaffar\n· Hassaan Ahmad\n· Abdullah Nadeem\n· Muhammad Rahat",
                "Exit",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private String getClassName(int cl) {
        switch (cl) {
            case 1: return "AC";
            case 2: return "First";
            case 3: return "Sleeper";
            default: return "Unknown";
        }
    }

    private void styleButton(JButton button, Color color, Dimension size) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
    }
}
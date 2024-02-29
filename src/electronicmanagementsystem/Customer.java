/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electronicmanagementsystem;

/**
 *
 * @author senud
 */
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement; // Only if you plan to use Statement
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    private String customerId;
    private String houseNo;
    private String road;
    private String city;
    private List<String> telephoneNumbers;
    private String date;

    // Constructor
    public Customer(String customerId, String title, String firstName, String lastName,
            List<String> telephoneNumbers, String houseNo, String road, String city, String date) {
        super(title, firstName, lastName); // Call to superclass constructor
        this.customerId = customerId;
        this.telephoneNumbers = telephoneNumbers;
        this.houseNo = houseNo;
        this.road = road;
        this.city = city;
        this.date = date;
    }

    // Getters and setters for Customer-specific fields
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getTelephoneNumbers() {
        if (telephoneNumbers != null && !telephoneNumbers.isEmpty()) {
            List<String> validNumbers = new ArrayList<>();
            for (String number : telephoneNumbers) {
                if (number.matches("\\d{1,10}")) {
                    validNumbers.add(number);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid telephone number format. Please enter up to 10 digits without any characters.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Instead of throwing an exception here, just skip this number
                }
            }
            if (validNumbers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No valid telephone numbers entered. Please enter up to 10 digits without any characters.", "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("No valid telephone numbers entered.");
            }
            return validNumbers.toArray(new String[0]);
        } else {
            JOptionPane.showMessageDialog(null, "Telephone numbers list is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Telephone numbers list is empty.");
        }
    }

    public void setTelephoneNumbers(List<String> telephoneNumbers) {
        List<String> validNumbers = new ArrayList<>();
        for (String number : telephoneNumbers) {
            if (number.matches("\\d{1,10}")) {
                validNumbers.add(number);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid telephone number format. Please enter up to 10 digits without any characters.", "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Phone number must contain up to 10 digits.");
            }
        }
        this.telephoneNumbers = validNumbers;
    }

    public String getDate() {
        if (isValidDate(date)) {
            return date;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid date format or claim date cannot be a future date. Please use YYYY-MM-DD and ensure it is not a future date.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Invalid date format or claim date cannot be a future date.");
        }
    }

    public void setDate(String date) {
        if (isValidDate(date)) {
            this.date = date;
        } else {

        }
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate claimDate = LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();
            if (claimDate.isAfter(currentDate)) {
                JOptionPane.showMessageDialog(null, "Claim date cannot be a future date.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format for claim date. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/repair_system", "root", "Waybig@123");

            String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.getCustomerId());
            pstmt.setString(2, this.getTitle());
            pstmt.setString(3, this.getFirstName());
            pstmt.setString(4, this.getLastName());
            pstmt.setString(5, this.getHouseNo());
            pstmt.setString(6, this.getRoad());
            pstmt.setString(7, this.getCity());
            pstmt.setString(8, this.getDate());

            pstmt.executeUpdate();

            // Insert telephone numbers into the Customer_TelNO table
            String telSql = "INSERT INTO Customer_TelNO VALUES (?, ?)";
            for (String telephoneNumber : telephoneNumbers) {
                pstmt = conn.prepareStatement(telSql);
                pstmt.setString(1, this.getCustomerId());
                pstmt.setString(2, telephoneNumber);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Customer added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    

}

    // Implementing the abstract method from Person
    @Override
    public String getFullDetails() {
        return "Customer ID: " + customerId + "\n"
                + "Title: " + getTitle() + "\n"
                + "Name: " + getFirstName() + " " + getLastName() + "\n"
                + "Phone Number: " + telephoneNumbers + "\n"
                + "House No: " + houseNo + "\n"
                + "Road: " + road + "\n"
                + "City: " + city + "\n"
                + "Date: " + date;
    }

}

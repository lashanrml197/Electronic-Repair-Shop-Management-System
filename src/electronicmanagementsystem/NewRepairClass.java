/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electronicmanagementsystem;

/**
 *
 * @author senud
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

public class NewRepairClass {

    private String jobID;
    private String brand;
    private String domain;
    private String customerID;
    private String employeeID;
    private String intakeDate;
    private String status;

    // Database settings
    private final String dbUrl = "jdbc:mysql://localhost:3306/repair_system";
    private final String dbUsername = "root";
    private final String dbPassword = "ThHiuok#21";

    // Constructor
    public NewRepairClass(String jobID, String brand, String domain, String customerID, String employeeID, String intakeDate,String status) {
        this.setJobID(jobID);
        this.setBrand(brand);
        this.setDomain(domain);
        this.setCustomerID(customerID);
        this.setEmployeeID(employeeID);
        this.setIntakeDate(intakeDate);
        this.setStatus(status);
    }

    // Getters and setters
    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    
    public String getIntakeDate() {
        if (isValidDate(intakeDate)) {
            return intakeDate;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid date format or claim date cannot be a future date. Please use YYYY-MM-DD and ensure it is not a future date.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Invalid date format or claim date cannot be a future date.");
        }
    }

    public void setIntakeDate(String intakeDate) {
        if (isValidDate(intakeDate)) {
            this.intakeDate = intakeDate;
        } else {

        }
    }
    
   
    private boolean isValidDate(String date) {
        try {
            LocalDate intakeDate = LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();
            if (intakeDate.isAfter(currentDate)) {
                JOptionPane.showMessageDialog(null, "Claim date cannot be a future date.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format for claim date. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
    }

        public String getStatus(){
           return status;
       }

       public void setStatus(String status){
           this.status = status;
       }
    
    // Method to add a new repair job to the database
    public void addToDatabase() throws ClassNotFoundException {
        String sql = "INSERT INTO Add_New_Repair VALUES (?, ?, ?, ?, ?, ?,?)";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            pstmt.setString(1, this.getJobID());
            pstmt.setString(2, this.getBrand());
            pstmt.setString(3, this.getDomain());
            pstmt.setString(4, this.getCustomerID());
            pstmt.setString(5, this.getEmployeeID());
            pstmt.setString(6, this.getIntakeDate());
            pstmt.setString(7, this.getStatus());

            // Execute the insert operation
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Repair job added successfully to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding repair job to the database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @return the intakeDate
     */
    

}

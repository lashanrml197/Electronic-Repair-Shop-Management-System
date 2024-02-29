/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electronicmanagementsystem;

/**
 *
 * @author senud
 */
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EmployeeClass extends Person {

    private String employeeId;
    private String employeeAddress;
    private String employeeDateOfBirth;
    private List<String> telephoneNumbers;// Added telephoneNumber variable

    // Updated constructor to include telephoneNumber
    public EmployeeClass(String title, String firstName, String lastName, String employeeId,
            String employeeAddress, String employeeDateOfBirth, String telephoneNumber) {
        super(title, firstName, lastName);
        setEmployeeId(employeeId);
        setEmployeeAddress(employeeAddress);
        setEmployeeDateOfBirth(employeeDateOfBirth);
        setTelephoneNumbers(List.of(telephoneNumber)); // Set the telephone number
    }

    public String getEmployeeId() {
        if (employeeId != null && !employeeId.isEmpty()) {
            return employeeId;
        } else {
            JOptionPane.showMessageDialog(null, "Employee ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId != null && !employeeId.isEmpty()) {
            this.employeeId = employeeId;
        } else {
            JOptionPane.showMessageDialog(null, "Employee ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
    }

    public String getEmployeeAddress() {
        if (employeeAddress != null && !employeeAddress.isEmpty()) {
            return employeeAddress;
        } else {
            JOptionPane.showMessageDialog(null, "Employee address cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Employee address cannot be empty.");
        }
    }

    public void setEmployeeAddress(String employeeAddress) {
        if (employeeAddress != null && !employeeAddress.isEmpty()) {
            this.employeeAddress = employeeAddress;
        } else {
            JOptionPane.showMessageDialog(null, "Employee address cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Employee address cannot be empty.");
        }
    }

    public String getEmployeeDateOfBirth() {
        if (isValidDate(employeeDateOfBirth)) {
            return employeeDateOfBirth;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid date format or date of birth cannot be a future date. Please use YYYY-MM-DD and ensure it is not a future date.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Invalid date format or date of birth cannot be a future date.");
        }
    }

    public void setEmployeeDateOfBirth(String employeeDateOfBirth) {
        if (isValidDate(employeeDateOfBirth)) {
            this.employeeDateOfBirth = employeeDateOfBirth;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid date format or date of birth cannot be a future date. Please use YYYY-MM-DD and ensure it is not a future date.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Invalid date format or date of birth cannot be a future date.");
        }
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate dob = LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();
            return !dob.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
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

    // New getter and setter for telephoneNumber
    public void addToDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/repair_system", "root", "Waybig@123");

            // Insert the employee details into the main table
            String sql = "INSERT INTO Add_New_EMP VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.getEmployeeId());
            pstmt.setString(2, getTitle());
            pstmt.setString(3, getFirstName());
            pstmt.setString(4, getLastName());
            pstmt.setString(5, this.getEmployeeAddress());
            pstmt.setString(6, this.getEmployeeDateOfBirth());

            pstmt.executeUpdate();

            // Insert the telephone numbers into the telephone number table
            for (String telephoneNumber : telephoneNumbers) {
                sql = "INSERT INTO EMP_TelNO VALUES (?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, this.getEmployeeId());
                pstmt.setString(2, telephoneNumber);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Employee added successfully!");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw ex; // Rethrow the exception to the calling method
            }
        }
    }

    // Updated getFullDetails method to include telephoneNumber
    @Override
    public String getFullDetails() {
        return "Employee ID: " + employeeId + "\n"
                + "Title: " + getTitle() + "\n"
                + "Name: " + getFirstName() + " " + getLastName() + "\n"
                + "Address: " + employeeAddress + "\n"
                + "Date of Birth: " + employeeDateOfBirth + "\n"
                + "Telephone Number: " + telephoneNumbers; // Include the telephone number in the full details
    }
}

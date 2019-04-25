import java.util.Scanner;
import java.sql.*;

/**
 * This program lets the user insert a row into the ComputerDB database's Computer table
 */
public class ComputerInserter {
    public static void main(String[] args) {
        String description;
        String prodNum;
        Double price;

        //Create named constant for the URL
        final String DB_URL = "jdbc:derby:ComputerDB";

        //Create a Scanner object for the keyboard input
        Scanner keyboard = new Scanner(System.in);

        try
        {
            //Create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);

            //Get the data for the new Computer
            System.out.println("Enter the Computer description: ");
            description = keyboard.nextLine();
            System.out.println("Enter the product number: ");
            prodNum  = keyboard.nextLine();
            System.out.println("Enter the price: ");
            price = keyboard.nextDouble();

            //create the Statement object
            Statement stmt = conn.createStatement();

            //Create a string with an INSERT statement
            String sqlStatement = "INSERT INTO Computer " +
                    "(ProdNum, Price, Description) " +
                    "VALUES ( '" + prodNum + "', " +
                    price + ", '" + description + "')";

            //Send the statement to the DBMS
            int rows = stmt.executeUpdate(sqlStatement);

            //display results
            System.out.println(rows + " row(s) added to the table.");

            //close the connection
            conn.close();

        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
}

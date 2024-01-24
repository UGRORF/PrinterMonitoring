import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 * @author Javin Paul
 */
public class PrintersDB {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/printers";
    private static final String user = "root";
    private static final String password = "rootroot";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private List<Printer> printers;

    public PrintersDB(List<Printer> printers) throws SQLException, IOException {
            this.printers = printers;
            this.rs = getDataFromDB();
            setDataToDB();
    }

    public static ResultSet getDataFromDB() throws SQLException {
        String query = "select * from printers";

        // opening database connection to MySQL server
        Connection con1 = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        Statement stmt1 = con1.createStatement();

        // executing SELECT query
        ResultSet data1 = stmt1.executeQuery(query);

        return data1;
    }


    public static Date getDate(String ip) throws SQLException {
        String query = "select * from printers where ip = '"+ ip +"';";

        // opening database connection to MySQL server
        Connection con4 = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        Statement stmt4 = con4.createStatement();

        // executing SELECT query
        ResultSet data4 = stmt4.executeQuery(query);
        Date date = null;

        while (data4.next()){
            date = data4.getDate("date_last_order");
        }


        stmt4.close();
        con4.close();

        return date;
    }

    public static void updateDate(String ip) throws SQLException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String query = "UPDATE printers \n" +
                "SET date_last_order = '" + sqlDate + "'\n" +
                "WHERE ip = '" + ip + "';";

        // opening database connection to MySQL server
        Connection con3 = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        Statement stmt3 = con3.createStatement();

        // executing SELECT query
        int data3 = stmt3.executeUpdate(query);

        stmt3.close();
        con3.close();
    }

    private void setDataToDB() throws SQLException, IOException {
        for (Printer printer : printers) {
            // opening database connection to MySQL server
            Connection con2 = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            Statement stmt2 = con2.createStatement();

            ResultSet data = getDataFromDB();
            int countID = getCountID();

            if(printer.getSerialNumber() != null){
                String serialNumber = "";
                String ip = "";

                String query = "INSERT INTO printers ( id, name, serial_number, ip, counter, status, black_toner, black_toner_current_balance, cyan_toner, cyan_toner_current_balance, magenta_toner, magenta_toner_current_balance, yellow_toner, yellow_toner_current_balance, location) \n" +
                        " VALUES ("+ countID + ", '" +
                        printer.getName() + "', '" +
                        printer.getSerialNumber() + "', '" +
                        printer.getIp() + "', " +
                        printer.getCounter() + ", " +
                        printer.isStatus() + ", '" +
                        printer.getBlackToner().substring(0, Math.min(45, printer.getBlackToner().length())) + "', " +
                        printer.getBlackTonerCurrentBalance() + ", '" +
                        printer.getCyanToner() + "', " +
                        printer.getCyanTonerCurrentBalance() + ", '" +
                        printer.getMagentaToner() + "', " +
                        printer.getMagentaTonerCurrentBalance() + ", '" +
                        printer.getYellowToner() + "', " +
                        printer.getYellowTonerCurrentBalance() + ", '" +
                        Location.getLocation(printer.getIp()) + "'" +
                        ");";

                while (data.next()) {
                    String name = printer.getName();
                    serialNumber = data.getString("serial_number");
                    ip = data.getString("ip");
                    int counter = printer.getCounter();
                    boolean status = printer.isStatus();
                    String blackToner = printer.getBlackToner();
                    int blackTonerCurrentBalance = printer.getBlackTonerCurrentBalance();
                    String cyanToner = printer.getCyanToner();
                    int cyanTonerCurrentBalance = printer.getCyanTonerCurrentBalance();
                    String magentaToner = printer.getMagentaToner();
                    int magentaTonerCurrentBalance = printer.getMagentaTonerCurrentBalance();
                    String yellowToner = printer.getYellowToner();
                    int yellowTonerCurrentBalance = printer.getYellowTonerCurrentBalance();

                    if(printer.getSerialNumber().equals(serialNumber)){
                        if(printer.getIp().equals(ip)){
                            query = "UPDATE printers \n" +
                                    " SET " +
                                    "counter = " + counter + ", " +
                                    "status = " + status + ", " +
                                    "black_toner = '" + blackToner.substring(0, Math.min(45, printer.getBlackToner().length()))+ "', " +
                                    "black_toner_current_balance = '" + blackTonerCurrentBalance + "', " +
                                    "cyan_toner = '" + cyanToner + "', " +
                                    "cyan_toner_current_balance = '" + cyanTonerCurrentBalance + "', " +
                                    "magenta_toner = '" + magentaToner + "', " +
                                    "magenta_toner_current_balance = '" + magentaTonerCurrentBalance + "', " +
                                    "yellow_toner = '" + yellowToner + "', " +
                                    "yellow_toner_current_balance = '" + yellowTonerCurrentBalance + "' \n" +
                                    "WHERE ip = '"+ ip +"' AND serial_number = '" + serialNumber +"';";
                            break;
                        } else {
                            query = "UPDATE printers \n" +
                                    " SET name = '"+ name + "', " +
                                    " ip = '" + ip + "', " +
                                    "counter = " +counter + ", " +
                                    "status = " + status + ", " +
                                    "black_toner = '" + blackToner.substring(0, Math.min(45, printer.getBlackToner().length())) + "', " +
                                    "black_toner_current_balance = '" + blackTonerCurrentBalance + "', " +
                                    "cyan_toner = '" + cyanToner + "', " +
                                    "cyan_toner_current_balance = '" + cyanTonerCurrentBalance + "', " +
                                    "magenta_toner = '" + magentaToner + "', " +
                                    "magenta_toner_current_balance = '" + magentaTonerCurrentBalance + "', " +
                                    "yellow_toner = '" + yellowToner + "', " +
                                    "yellow_toner_current_balance = '" + yellowTonerCurrentBalance + "', " +
                                    "location = '" + Location.getLocation(printer.getIp()) + "' \n" +
                                    "WHERE serial_number = '"+ serialNumber +"';";
                            break;
                        }
                    } else {
                        if(printer.getIp().equals(ip)){
                            query = "UPDATE printers \n" +
                                    " SET name = '"+ name + "', " +
                                    "serial_number = '" +serialNumber + "', " +
                                    "counter = " +counter + ", " +
                                    "status = " + status + ", " +
                                    "black_toner = '" + blackToner.substring(0, Math.min(45, printer.getBlackToner().length())) + "', " +
                                    "black_toner_current_balance = '" + blackTonerCurrentBalance + "', " +
                                    "cyan_toner = '" + cyanToner + "', " +
                                    "cyan_toner_current_balance = '" + cyanTonerCurrentBalance + "', " +
                                    "magenta_toner = '" + magentaToner + "', " +
                                    "magenta_toner_current_balance = '" + magentaTonerCurrentBalance + "', " +
                                    "yellow_toner = '" + yellowToner + "', " +
                                    "yellow_toner_current_balance = '" + yellowTonerCurrentBalance + "', " +
                                    "location = '" + Location.getLocation(printer.getIp()) + "' \n" +
                                    "WHERE ip = '"+ ip +"';";
                            break;
                        }
                    }
                }

                stmt2.executeUpdate(query);
                con2.close();
                stmt2.close();
            }

        }
    }

    private int getCountID(){
        int count = 0;
        try {
            String query = "select count(*) from printers";
//         opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);


            while (rs.next()) {
                count = rs.getInt(1);
            }


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        return count;
    }


}
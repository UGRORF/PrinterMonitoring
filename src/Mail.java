import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Mail {

    public Mail() throws SQLException, IOException {
        createMail();
    }

    private void createMail() throws SQLException, IOException {
        ResultSet printers = PrintersDB.getDataFromDB();
        Date date = new Date();

        while (printers.next()){


            String toners = "";
            if(printers.getInt(("black_toner_current_balance")) < 30 && printers.getInt("black_toner_current_balance") >= 0){
                toners += "\t" + printers.getString("black_toner") + ", Остаток: " + printers.getInt("black_toner_current_balance") + "% - 1 шт.\n";
            }
            if(printers.getInt(("cyan_toner_current_balance")) < 30 && printers.getInt("cyan_toner_current_balance") >= 0){
                toners += "\t" + printers.getString("cyan_toner") + ", Остаток: " + printers.getInt("cyan_toner_current_balance") + "% - 1 шт.\n";
            }
            if(printers.getInt(("magenta_toner_current_balance")) < 30 && printers.getInt("magenta_toner_current_balance") >= 0){
                toners += "\t" + printers.getString("magenta_toner") + ", Остаток: " + printers.getInt("magenta_toner_current_balance") + "% - 1 шт.\n";
            }
            if(printers.getInt(("yellow_toner_current_balance")) < 30 && printers.getInt("yellow_toner_current_balance") >= 0){
                toners += "\t" + printers.getString("yellow_toner") + ", Остаток: " + printers.getInt("yellow_toner_current_balance") + "% - 1 шт.\n";
            }

            String mail = "•\t"+ printers.getString("location")+"\n" +
                    "•\t" + printers.getString("name") +"\n" +
                    "•\t" + printers.getString("serial_number") +"\n" +
                    "•\tСчетчик: " + printers.getString("counter") +"\n" +
                    "•"+ toners +
                    "•\tЗакончился тонер картридж\n" +
                    "•\tЗапорожец Артем 89885466866\n" +
                    "•\tСредняя\n";


            if(toners.length() != 0){
                if(printers.getString("date_last_order") == null) {
                    PrintersDB.updateDate(printers.getString("ip"));
                    System.out.println(PrintersDB.getDate(printers.getString("ip")));
                    System.out.println(mail);
                }
                else if((date.getTime() - PrintersDB.getDate(printers.getString("ip")).getTime()) / (24 * 60 * 60 * 1000) > 14){
                    long milliseconds = date.getTime() - PrintersDB.getDate(printers.getString("ip")).getTime();

                    int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
                    System.out.println("Разница между датами в днях: " + days);

                    PrintersDB.updateDate(printers.getString("ip"));
                    System.out.println(PrintersDB.getDate(printers.getString("ip")));
                    System.out.println(mail);
                }
            }
        }
    }
}

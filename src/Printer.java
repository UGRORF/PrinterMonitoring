import org.snmp4j.smi.OID;

import java.io.IOException;
import java.util.Arrays;

public class Printer {
    private String name;
    private String serialNumber;
    private String ip;
    private int counter;
    private boolean status;
    private String blackToner;
    private int blackTonerCurrentBalance;
    private String cyanToner;
    private int cyanTonerCurrentBalance;
    private String magentaToner;
    private int magentaTonerCurrentBalance;
    private String yellowToner;
    private int yellowTonerCurrentBalance;
    private String location;

    Printer(String ip) throws IOException {
        this.ip = ip;
        this.status = Main.isAvailable(ip);
        this.blackToner = null;
        this.cyanToner = null;
        this.magentaToner = null;
        this.yellowToner = null;
        this.blackTonerCurrentBalance = -1;
        this.cyanTonerCurrentBalance = -1;
        this.magentaTonerCurrentBalance = -1;
        this.yellowTonerCurrentBalance = -1;
        this.location = null;

        if(status){
            SNMPManager client = new SNMPManager("udp:" + ip + "/161");
            client.start();

            initPrinter(client);
        }
    }

    private void initPrinter(SNMPManager client) throws IOException {
        this.name = client.getAsString(new OID(".1.3.6.1.2.1.25.3.2.1.3.1"));

        if(name.substring(0,3).equals("TAS") || name.substring(0,3).equals("ECO")){
            this.counter = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.4.1.1347.43.10.1.1.12.1.1")));
        } else {
            this.counter = Integer.parseInt(client.getAsString(new OID(".1.3.6.1.2.1.43.10.2.1.4.1.1")));
        }

        this.serialNumber = client.getAsString(new OID(".1.3.6.1.2.1.43.5.1.1.17.1"));

        initActualToner(client);
    }

    private void initActualToner(SNMPManager client) throws IOException {
        if(Arrays.stream(BlackPrinterEnum.values()).anyMatch(element -> element.getName().equals(this.name))){
            this.blackToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.1"));
            String usage = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"));
            String capacity = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.1"));
            this.blackTonerCurrentBalance = 100 * Integer.parseInt(usage) / Integer.parseInt(capacity);
        }
        else if(Arrays.stream(ColorPrinterEnum.values()).anyMatch(element -> element.getName().equals(name))){
            if(this.name.substring(0,3).equals("TAS") || this.name.substring(0,3).equals("ECO")){
                this.blackToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.4"));
                this.cyanToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.1"));
                this.magentaToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.2"));
                this.yellowToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.3"));

                String usageBlack = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.4"));
                String capacityBlack = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.4"));
                this.blackTonerCurrentBalance = 100 * Integer.parseInt(usageBlack) / Integer.parseInt(capacityBlack);

                String usageCyan = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"));
                String capacityCyan = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.1"));
                this.cyanTonerCurrentBalance = 100 * Integer.parseInt(usageCyan) / Integer.parseInt(capacityCyan);

                String usageMagenta = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.2"));
                String capacityMagenta = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.2"));
                this.magentaTonerCurrentBalance = 100 * Integer.parseInt(usageMagenta) / Integer.parseInt(capacityMagenta);

                String usageYellow = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.3"));
                String capacityYellow = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.3"));
                this.yellowTonerCurrentBalance = 100 * Integer.parseInt(usageYellow) / Integer.parseInt(capacityYellow);
            } else {
                this.blackToner = client.getAsString(new OID(".1.3.6.1.4.1.11.2.3.9.4.2.1.2.2.1.110"));
                this.cyanToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.2"));
                this.magentaToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.3"));
                this.yellowToner = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.6.1.4"));


                String usageBlack = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.1"));
                String capacityBlack = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.1"));
                this.blackTonerCurrentBalance = 100 * Integer.parseInt(usageBlack) / Integer.parseInt(capacityBlack);

                String usageCyan = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.2"));
                String capacityCyan = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.2"));
                this.cyanTonerCurrentBalance = 100 * Integer.parseInt(usageCyan) / Integer.parseInt(capacityCyan);

                String usageMagenta = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.3"));
                String capacityMagenta = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.3"));
                this.magentaTonerCurrentBalance = 100 * Integer.parseInt(usageMagenta) / Integer.parseInt(capacityMagenta);

                String usageYellow = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.9.1.4"));
                String capacityYellow = client.getAsString(new OID(".1.3.6.1.2.1.43.11.1.1.8.1.4"));
                this.yellowTonerCurrentBalance = 100 * Integer.parseInt(usageYellow) / Integer.parseInt(capacityYellow);
            }
        }
    }


    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getIp() {
        return ip;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isStatus() {
        return status;
    }

    public String getBlackToner() {
        return blackToner;
    }

    public int getBlackTonerCurrentBalance() {
        return blackTonerCurrentBalance;
    }

    public String getCyanToner() {
        return cyanToner;
    }

    public int getCyanTonerCurrentBalance() {
        return cyanTonerCurrentBalance;
    }

    public String getMagentaToner() {
        return magentaToner;
    }

    public int getMagentaTonerCurrentBalance() {
        return magentaTonerCurrentBalance;
    }

    public String getYellowToner() {
        return yellowToner;
    }

    public int getYellowTonerCurrentBalance() {
        return yellowTonerCurrentBalance;
    }

    @Override
    public String toString() {
        return "Printer{" +
                "name='" + name + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", ip='" + ip + '\'' +
                ", counter=" + counter +
                ", status=" + status +
                ", blackToner='" + blackToner + '\'' +
                ", blackTonerCurrentBalance=" + blackTonerCurrentBalance +
                ", cyanToner='" + cyanToner + '\'' +
                ", cyanTonerCurrentBalance=" + cyanTonerCurrentBalance +
                ", magentaToner='" + magentaToner + '\'' +
                ", magentaTonerCurrentBalance=" + magentaTonerCurrentBalance +
                ", yellowToner='" + yellowToner + '\'' +
                ", yellowTonerCurrentBalance=" + yellowTonerCurrentBalance +
                '}';
    }
}

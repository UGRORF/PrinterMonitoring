public enum BlackPrinterEnum {
    M426FDN("HP LaserJet MFP M426fdn"),
    M528("HP LaserJet MFP M528"),
    M606("HP LaserJet M606"),
    M203DN("HP LaserJet M203dn"),
    M428FDN("HP LaserJet Pro MFP M428fdn"),
    M1536FDN("HP LaserJet M1536dnf MFP"),
    M527("HP LaserJet MFP M527"),
    M525("HP LaserJet 500 MFP M525"),
    M430("HP LaserJet MFP M430");

    private final String name;
    BlackPrinterEnum(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

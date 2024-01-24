public enum ColorPrinterEnum {
    M452DN("HP Color LaserJet M452dn"),
    CP1515N("HP Color LaserJet CP1515n"),
    CI3253("TASKalfa 3253ci"),
    M477FDN("HP Color LaserJet MFP M477fdn"),
    CI2551("TASKalfa 2551ci"),
    E57540("HP Color LaserJet Managed MFP E57540");

    private final String name;
    ColorPrinterEnum(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

package models;

/**
 * Model: Operator
 * Represents a mobile phone operator (carrier).
 */
public class Operator {

    private final String id;
    private final String name;
    private final String colorPrimary;
    private final String colorSecondary;
    private final String logoIcon;
    private final String slogan;

    public Operator(String id, String name, String colorPrimary, String colorSecondary,
                    String logoIcon, String slogan) {
        this.id = id;
        this.name = name;
        this.colorPrimary = colorPrimary;
        this.colorSecondary = colorSecondary;
        this.logoIcon = logoIcon;
        this.slogan = slogan;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getColorPrimary() { return colorPrimary; }
    public String getColorSecondary() { return colorSecondary; }
    public String getLogoIcon() { return logoIcon; }
    public String getSlogan() { return slogan; }

    @Override
    public String toString() {
        return String.format("Operador: %s (%s)", name, id);
    }
}

package dk.monopoly.entities;

public abstract class InfoService {
    public static final String CRATER = "Crater";
    public static final String TOWER = "Tower";
    protected String message;
    protected int score;
    protected int points;
    protected String location;

    public String varName = "";

    public abstract void setPoints(int points);

    public abstract int getScore();

    public abstract String getMessage();

    public abstract String getLocation();


}

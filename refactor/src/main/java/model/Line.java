package model;

public class Line {
    private int year;
    private int reults;

    public Line() {
    }

    public Line(String statictics) {
        final String[] stat = statictics.split(" ");
        if (stat.length != 4) {
            throw new ArrayIndexOutOfBoundsException("Должно быть ровно 4 параметра");
        }

        this.year = Integer.parseInt(stat[2]);
        this.reults = Integer.parseInt(stat[3]);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getReults() {
        return reults;
    }

    public void setReults(int reults) {
        this.reults = reults;
    }
}

public class Color {
    int red;
    int green;
    int blue;

    public Color(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    public Color(){}

    @Override
    public String toString() {
        return red + ":" + green + ":" + blue;
    }
}

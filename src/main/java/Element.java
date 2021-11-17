import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    private Position position;
    public abstract void draw(TextGraphics graphics);
    public Element(int x,int y) {
        this.position = new Position(x,y);
    }
    public Position getPosition() {
        return position ;
    }
    public void setPosition(Position position){
        this.position=position;
    }


}

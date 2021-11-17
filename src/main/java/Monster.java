import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Monster extends Element{
    public Monster(int x, int y) {
        super(x, y);
    }
    public Position getPosition() {
        return super.getPosition();
    }
    public void setPosition(Position position){
        super.setPosition(position);
    }
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(super.getPosition().getX(), super.getPosition().getY()), "M");

    }
    public Position move(Position position){
        if (super.getPosition().getX()==position.getX()) {
            if (super.getPosition().getY() > position.getY())
                return new Position(super.getPosition().getX(), super.getPosition().getY() - 1);
            else
                return new Position(super.getPosition().getX(), super.getPosition().getY() + 1);
        }
        if (super.getPosition().getY()==position.getY()) {
            if (super.getPosition().getX() > position.getX())
                return new Position(super.getPosition().getX() - 1, super.getPosition().getY() );
            else
                return new Position(super.getPosition().getX() + 1, super.getPosition().getY() );
        }
        else{
            if (super.getPosition().getX() > position.getX()){
                if (super.getPosition().getY() > position.getY())
                    return new Position(super.getPosition().getX() - 1, super.getPosition().getY() - 1);
                else
                    return new Position(super.getPosition().getX() - 1, super.getPosition().getY() + 1);

            }
            else{
                if (super.getPosition().getY() > position.getY())
                    return new Position(super.getPosition().getX() + 1, super.getPosition().getY() - 1);
                else
                    return new Position(super.getPosition().getX() + 1, super.getPosition().getY() + 1);
            }

        }
    }
}

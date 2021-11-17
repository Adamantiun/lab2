import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int height;
    private int width;
    private Hero hero = new Hero(10, 10);
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> enemies;
    private boolean gameOver;
    public Arena (int w, int h){
        height = h;
        width = w;
        this.walls = createWalls();
        coins = createCoins();
        enemies = createMonsters();
        gameOver = false;
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) +
                    1, random.nextInt(height - 2) + 1));
        return coins;
    }
    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters= new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width+1; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height));
        }
        for (int r = 1; r < height+1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width, r));
        }
        return walls;
    }
    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#55ff11"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new
                TerminalSize(width+1, height+1), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : enemies)
            monster.draw(graphics);
    }
    private boolean canHeroMove(Position position){
        if(position.getX()<=0 || position.getX()>=width || position.getY()<=0 || position.getY()>=height)
            return false;
        return true;
    }
    public void moveHero(Position position) {
        verifyMonstersCollisions();
        if (canHeroMove(position))
            hero.setPosition(position);
        retrieveCoins();
        moveMonsters();
        verifyMonstersCollisions();
    }
    public void retrieveCoins(){
        for(Coin coin : coins){
            if(coin.getPosition().getX() == hero.getPosition().getX() && hero.getPosition().getY() == coin.getPosition().getY()){
                coins.remove(coin);
                break;
            }
        }
    }
    private void moveMonsters(){
        for(int i=0; i<enemies.size();i++){
            enemies.get(i).setPosition(enemies.get(i).move(hero.getPosition()));
        }
    }
    public void verifyMonstersCollisions(){
        for(Monster monster : enemies){
            if (monster.getPosition().getY()==hero.getPosition().getY() && monster.getPosition().getX()==hero.getPosition().getX() )
                gameOver=true;
        }
    }
    public void processKey(KeyStroke key, Screen screen) throws IOException {
        if(key.getKeyType() == KeyType.ArrowUp)
            moveHero(hero.moveUp());
        if(key.getKeyType() == KeyType.ArrowDown)
            moveHero(hero.moveDown());
        if(key.getKeyType() == KeyType.ArrowRight)
            moveHero(hero.moveRight());
        if(key.getKeyType() == KeyType.ArrowLeft)
            moveHero(hero.moveLeft());
        if (key.getKeyType() == KeyType.Character &&
                key.getCharacter() == 'q')
            screen.close();
    }
    public boolean getGameOver(){
        return gameOver;
    }
}

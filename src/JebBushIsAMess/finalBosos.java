package JebBushIsAMess;

import java.awt.Graphics; 
import java.awt.Color; 
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class finalBosos {
	int x;
	int y;
	int height;
	int width;
	int facing = -1;
	boolean jumping;
	List<Bullet> bullets = new CopyOnWriteArrayList<>();
	ImageIcon jeb = new ImageIcon("jeb-bush.jpg");
	ImageIcon bush = new ImageIcon("george_w_bush3.jpg");
	ImageIcon bushSr = new ImageIcon("george-bush-sr.jpg");
	private int health = 10;
	
	
	finalBosos(int xPos, int yPos) {
		int size = (int) (Math.random() *5) + 10;
		int type = (int) (Math.random() *5) + 10;
		
		height = size * type;
		width = size * type;
		
		x = xPos;
		y = yPos;
		
	}
	
	void move() {
		if (jumping) {
			y += 100;
			jumping = false;
		} else {
			int dir = (int) (Math.random() * 6); // Up = 0; Left = 1; Right =
													// true

			if (dir == 0) {
				y -= 100;
				jumping = true;
			} else if (dir == 1) {
				x -= 30;
			} else if (dir == 2) {
				x += 30;
			}
		}
		int size = (int) (Math.random() *5) + 5;
		int type = (int) (Math.random() *5) + 5;
		
		height = size * type;
		width = size * type;
		
	}
	
	void setFacing(int player_x){
		if(player_x > x)
			facing = 1;
		else
			facing = -1;
	}
	boolean checkBulletHit() {
		return false;
	}
	
	void shoot() {
		if (bullets.size() < 10) {
			bullets.add(new Bullet(x, y, facing, 2));
			bullets.add(new Bullet(x,y,-facing, 2));
		}
		
	}
	
	void drawBoss(Graphics g, ImageObserver image) {
		g.drawImage(jeb.getImage(), x, y + height, height, width + 20, image);
		g.drawImage(bush.getImage(), x, y, height, width, image);
		g.drawImage(bushSr.getImage(), x + 10, y - height/2, height - 20, width - 20, image);
		g.fillRect(x, y - 80, health * 10, 20);
		for (Bullet b : bullets)
			b.drawShot(g, image, 3);
	}
	
	boolean doSomething(int playerx, int playery) {
		setFacing(playerx);
		int thing = (int) (Math.random() * 5);
		if (thing == 1)
			move();
		if (thing == 0)
			shoot();
		if(checkMolest(playerx, playery, 80,100))
			return true;
		for (Bullet b : bullets) {
			if (b.checkHit(playerx, playery, 80,100)){
				bullets.remove(b);
				System.out.println("hit");
				return true;
				
			}
			else if (b.fly(800))
				bullets.remove(b);
		}
		return false;
	}

	public void decrementHealth(){
		health --;
	}
	
	public boolean isAlive() {
		if(health <= 0) {
			return false;
		}
		return true;
	}

	public boolean checkMolest(int obj_x, int obj_y, int obj_w, int obj_h) {
		// above or below
		if (x <= obj_x + obj_w && // far right
				x + width >= obj_x && // far left
				y <= obj_y + obj_h && // bottom
				y + height >= obj_y) // top
			return true;
		return false;
	}
}

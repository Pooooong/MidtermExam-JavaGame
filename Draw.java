import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import java.util.ArrayList;

public class Draw extends JComponent{

	private BufferedImage image;
	private BufferedImage backgroundImage;
	public URL resource = getClass().getResource("Running/run0.png");

	// circle's position
	public int x = 30;
	public int y = 30;

	// animation states
	public int state = 0;

	public Random randomizer;

	public int enemyCount;
	Monster[] monsters = new Monster[10];
	public ArrayList<Monster> monsterlist = new ArrayList<>();

	public Draw(){

		randomizer = new Random();
		player = new Player(-10, 520, this);

		spawnEnemy();

		try{
			image = ImageIO.read(resource);
			backgroundImage = ImageIO.read(getClass().getResource("background.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void spawnEnemy(){
		if(enemyCount < 10){
			int random = randomizer.nextInt(600);
			if(random < 600 || random < 30){
			monsters[enemyCount] = new Monster(random, 530, this);
			monsterlist.add(monsters[enemyCount]);
			enemyCount++;
			}
		}
	}

	public void reloadImage(){
		state++;

		if(state == 0){
			resource = getClass().getResource("Running/run0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("Running/run1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("Running/run2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("Running/run3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("Running/run4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("Running/run5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 5; ctr++){
					try {
						if(ctr==4){
							resource = getClass().getResource("Running/run0.png");
						}
						else{
							resource = getClass().getResource("Attacking/attack"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread1.start();
	}

	public void attack(){
		attackAnimation();
	}

	public void moveUp(){
		y = y - 5;
		reloadImage();
		repaint();
	}

	public void moveDown(){
		y = y + 5;
		reloadImage();
		repaint();
	}

	public void moveLeft(){
		x = x - 5;
		reloadImage();
		repaint();
	}

	public void moveRight(){
		x = x + 5;
		reloadImage();
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(image, x, y, this);

		for(int c = 0; c < monsterlist.size(); c++){
			if(monsterlist.size() != 0){
				g.drawImage(monsterlist.get(c).image, monsterlist.get(c).xPos, monsterlist.get(c).yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsterlist.get(c).xPos+7, monsterlist.get(c).yPos, monsterlist.get(c).life, 2);
			}
		}
	}
}
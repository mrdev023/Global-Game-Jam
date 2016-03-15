package Entity;

import java.util.ArrayList;
import java.util.Random;

import Tools.CoordD;

public class Boss extends Monster{
	
	private ArrayList<CoordD> spotCoordDs = new ArrayList<CoordD>();
	private boolean patternFini = false;
	private final double maxRandRange = 1000.0;
	private double actualRandRange = 1000.0;
	private int inMotion = 0;
	private Random randG = new Random();
	
	private ArrayList<ShootingPattern> listShootingPattern = new ArrayList<ShootingPattern>();
	private int indiceActualShootingPattern = 0;
	
	public Boss(String name, String patternName, int HP, double moveSpeed,int scoreDead,
			ArrayList<CoordD> spotCoordDs, ArrayList<ShootingPattern> listShootingPattern){
		
		super(name, patternName, HP, moveSpeed, 300, true, 1,scoreDead);
		this.spotCoordDs = spotCoordDs;
		this.listShootingPattern = listShootingPattern;
	}
	
	public void deplacer(){
		// si le boss est static
		if(inMotion <= 0){
			
			// on fait un test al�atoire pour savoir si il va commencer un d�placement
			double testDepla = actualRandRange * Math.random();
			
			// si oui
			if(testDepla < 1.0){
				
				// on r�initialise le test
				actualRandRange = maxRandRange;
				
				// on d�termine o� on va
				int indiceSpot = randG.nextInt(spotCoordDs.size());
				
				//on calcule le vercteur de d�placement pour y allez
				double longDeplaTT = spotCoordDs.get(indiceSpot).x - (x + (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth());	// orient�
				double hautDeplaTT = spotCoordDs.get(indiceSpot).y - (y + (image.getHeight() / 2) / Jeu.Jeu.resolution.getHeight());	// orient�
				double hypo = Math.sqrt((longDeplaTT * longDeplaTT) + (hautDeplaTT * hautDeplaTT));
				setVectorX(longDeplaTT / hypo);
				setVectorY(hautDeplaTT / hypo);
				
				// on d�termine le nombre de d�placement � faire pour rejoindre le spot
				inMotion = (int)(hypo / moveSpeed);
			}
			//sinon
			else{
				// on augmente la probabilit� de r�ussir le test
				actualRandRange *= 0.99;
			}
		}
		// sinon il poursuit juste son d�placement
		else{
			inMotion --;
			move(getVectorX() * moveSpeed, getVectorY() * moveSpeed);
		}
	}
	
	public void gestionTir(){
		if(HP < listShootingPattern.get(0).hpFinish){
			listShootingPattern.remove(0);
		}
	}
	
	public void setAFiniSonParttern(){
		patternFini = true;
		current = System.currentTimeMillis();
		previous = 0;
		elapsed = 0;
	}
	
	public boolean getPatternFini(){
		return patternFini;
	}
	
	public boolean isAlive(){
		return (HP > 0) ? true : false;
	}
	
	public ArrayList<ShootingPattern> getListShootingPattern(){
		return listShootingPattern;
	}
	
	public boolean isTimeOut(){
		return elapsed >= listShootingPattern.get(0).duration;
	}
	
	public void resetTimer(){
		elapsed -= listShootingPattern.get(0).duration;
	}
	
	public void setIndiceActualShootingPattern(int p){
		indiceActualShootingPattern = p;
	}
	
	public int getIndiceActualShootingPattern(){
		return indiceActualShootingPattern;
	}
}

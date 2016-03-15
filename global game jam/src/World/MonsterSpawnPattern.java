package World;

import Entity.Monster;


/*
 * d�finit le momant d'apparition d'un mob et le mob lui-m�me
 */
public class MonsterSpawnPattern {
	private Monster monster;
	private long spawnTimer;
	
	public MonsterSpawnPattern(Monster monster, long spawnTimer){
		this.monster = monster;
		this.spawnTimer = spawnTimer;
		
		this.monster.setTimerTo0();
	}
	
	public Monster getMonster(){
		return monster;
	}
	
	public long getSpawnTimer(){
		return spawnTimer;
	}
}

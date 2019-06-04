package politicalLogic;

import java.util.ArrayList;
import java.util.Random;

import graphics.Plot;

public class Warfield {

	private ArrayList<Locality> localities = new ArrayList<Locality>();
	private StringBuilder battleLog = new StringBuilder("War has not started!\n");

	public Warfield() {
	};

	public void stepWar() {
		// A war movement. A random locality is selected and so is one of
		// its frontiers. Winner is chosen randomly and loser changes government to
		// winner's.
		int counter = 0;
		if (isWarFinished()) {
			battleLog.append("War is finished\n");
			return;
		}
		Random rnd = new Random();
		int i = rnd.nextInt(localities.size());
		Locality offensive = localities.get(i);
		while (offensive.conqueredAllFrontiers()) {
			counter++;
			if(counter>500)
				return;
			i = rnd.nextInt(localities.size());
			offensive = localities.get(i);
		}
		i = rnd.nextInt(offensive.getFrontiers().size());
		Locality defensive = offensive.getFrontiers().get(i);
		while (defensive.getGovernment().equals(offensive.getGovernment())) {
			counter++;
			if(counter>500)
				return;
			i = rnd.nextInt(offensive.getFrontiers().size());
			defensive = offensive.getFrontiers().get(i);
		}
		int result = rnd.nextInt(1001);
		Locality winner = null;
		Locality loser = null;
		if (result < 500) { //Offensive wins
			winner = offensive;
			loser = defensive;
		} else { //Defensive wins
			winner = defensive;
			loser = offensive;
		}
		battleLog.append("-"+winner.getGovernment() + " conquers " + loser.getName() + ",\n previously owned by "
				+ loser.getGovernment()+"\n");
		loser.setGovernment(winner.getGovernment());
	}

	public boolean isWarFinished() {
		// War ends when one locality controls all territory
		for (Locality l1 : localities) {
			for (Locality l2 : localities) {
				if (!l1.getGovernment().equals(l2.getGovernment())) {
						return false;
				}
			}
		}
		return true;
	}
	

	public void fullWar() {
		// Steps war until it is finished
		while(!isWarFinished())
			this.stepWar();		
	}

	public StringBuilder getBattleLog() {
		return this.battleLog;
	}

	public ArrayList<Locality> getLocalities() {
		return this.localities;
	}

	public void addLocality(Locality l) {
		localities.add(l);
	}

	public void addNewLocalityGoverment(String government) {
		Locality l = new Locality();
		l.setGovernment(government);
		localities.add(l);
	}

	public void addNewLocalityName(String name) {
		Locality l = new Locality();
		l.setName(name);
		localities.add(l);
	}

	public void removeLocality(Locality l) {
		for (Locality lo: localities) {
			lo.removeFrontier(l);
		}
		localities.remove(l);
	}

	public Locality getLocalityFromPlot(Plot g) {
		for (Locality l : localities) {
			if (l.getGraphic().equals(g))
				return l;
		}
		return null;
	}

	public Locality getLocalityFromName(String name) {
		for (Locality l : localities) {
			if (l.getName().equals(name))
				return l;
		}
		return null;
	}

}

package politicalLogic;

import java.util.ArrayList;

import graphics.Plot;

public class Warfield {
	
	private ArrayList<Locality> localities = new ArrayList<Locality>();
	
	public Warfield() {};
	
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

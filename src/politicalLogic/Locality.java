package politicalLogic;

import java.util.ArrayList;

import graphics.Plot;

public class Locality {
	
	private String name;
	private String government;
	private Plot graphic;
	ArrayList<Locality> frontiers = new ArrayList<Locality>();
	
	public Locality(Plot graph) {
		this.graphic = graph;
	};
	
	public Locality() {};
	
	public ArrayList<Locality> getFrontiers() {
		return new ArrayList<Locality>(this.frontiers);
	}
	
	public void removeFrontier(Locality f) {
		frontiers.remove(f);
	}
	
	public void addFrontier(Locality f) {
		if (!frontiers.contains(f))
			frontiers.add(f);
	}
	
	public void setGraphic(Plot graphic) {
		this.graphic = graphic;
	}
	
	public Plot getGraphic() {
		return this.graphic;
	}
	
	public void setGovernment(String gover) {
		this.government = gover;
		this.graphic.update(gover);
	}
	
	public String getGovernment() {
		return this.government;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean conqueredAllFrontiers() {
		//Checks if all of this locality's frontiers share its government
		for (Locality l:frontiers) {
			if (!l.getGovernment().equals(this.government)) {
				return false;
			}
		}
		return true;
	}

}

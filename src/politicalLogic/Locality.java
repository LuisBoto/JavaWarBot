package politicalLogic;

import java.util.ArrayList;

import graphics.Plot;

public class Locality {
	
	private String name;
	private String government;
	private Plot graphic;
	ArrayList<Locality> frontiers;
	
	public Locality() {};
	
	public ArrayList<Locality> getFrontiers() {
		return new ArrayList<Locality>(this.frontiers);
	}
	
	public void removeFrontier(Locality f) {
		frontiers.remove(f);
	}
	
	public void addFrontier(Locality f) {
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

}

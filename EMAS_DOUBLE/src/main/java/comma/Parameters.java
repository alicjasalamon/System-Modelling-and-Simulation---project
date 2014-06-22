package comma;

public final class Parameters {

	private final static Parameters instance = new Parameters();
	
	public static Parameters getInstance() {
		return instance;
	}
	
	private boolean fixedDistance;
	private int currentGen;
	private int maxGen;
	private int agents;
	
	private Parameters() {
		fixedDistance = false;
		currentGen = 0;
		maxGen = 1000;
		agents = 200;
	}

	public boolean isFixedDistance() {
		return fixedDistance;
	}

	public int getCurrentGen() {
		return currentGen;
	}

	public void setCurrentGen(int currentGen) {
		this.currentGen = currentGen;
	}

	public int getMaxGen() {
		return maxGen;
	}

	public int getAgents() {
		return agents;
	}

	public void setAgents(int agents) {
		this.agents = agents;
	}
	
}

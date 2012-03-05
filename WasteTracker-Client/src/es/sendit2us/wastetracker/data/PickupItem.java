package es.sendit2us.wastetracker.data;


public class PickupItem {

	private long id;
	private String residual;
	private String containerType;
	private int containers;
	private int amount;
	
	public PickupItem() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResidual() {
		return residual;
	}

	public void setResidual(String residual) {
		this.residual = residual;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public int getContainers() {
		return containers;
	}

	public void setContainers(int containers) {
		this.containers = containers;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}

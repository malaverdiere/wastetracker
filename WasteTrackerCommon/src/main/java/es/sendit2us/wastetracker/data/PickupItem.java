package es.sendit2us.wastetracker.data;


public class PickupItem {

	private long id;
	private long residual;
	private long containerType;
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

	public String getResidualDesc() {
		for (int i = 0; i < MasterDataMock.getResiduals().size(); i++) {
			ResidualType res = (ResidualType) MasterDataMock.getResiduals().elementAt(i);
			if (residual == res.getId()) {
				return res.getDescription();
			}
		}
		return "<no catalogado>";
	}

	public long getResidual() {
		return residual;
	}

	public void setResidual(long residual) {
		this.residual = residual;
	}

	public long getContainerType() {
		return containerType;
	}

	public void setContainerType(long containerType) {
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

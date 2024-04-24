public class Topping
{
	private int TopID;
	private String TopName;
	private double PerAMT;
	private double MedAMT;
	private double LgAMT;
	private double XLAMT;
	private double CustPrice;
	private double BusPrice;
	private int MinINVT;
	private int CurINVT;
	
	public Topping(int topID, String topName, double perAMT, double medAMT, double lgAMT, double xLAMT,
			double custPrice, double busPrice, int minINVT, int curINVT) {
		TopID = topID;
		TopName = topName;
		PerAMT = perAMT;
		MedAMT = medAMT;
		LgAMT = lgAMT;
		XLAMT = xLAMT;
		CustPrice = custPrice;
		BusPrice = busPrice;
		MinINVT = minINVT;
		CurINVT = curINVT;
	}
	// ToppingID -- getTopID()
	//ToppingName -- getTopName()
	//BusinessPrice -- getBusPrice()
	//CustomerCost -- getCustPrice()
	//Current Inventory -- getCurINVT()
	//Minimum Topping -- getMinINVT()
	// Price small -- **getPerAMT()
	// Price medium -- getMedAMT()
	// Price large -- getLgAMT()
	// price XL -- getXLAMT()



	//Topping ID
	public int getTopID() {
		return TopID;
	}

	//Topping Name
	public String getTopName() {
		return TopName;
	}

	public double getPerAMT() {
		return PerAMT;
	}

	//Medium price
	public double getMedAMT() {
		return MedAMT;
	}

	// LG price
	public double getLgAMT() {
		return LgAMT;
	}

	//XL Price
	public double getXLAMT() {
		return XLAMT;
	}

	// Customer Price
	public double getCustPrice() {
		return CustPrice;
	}

	// Business Price
	public double getBusPrice() {
		return BusPrice;
	}

	// Minimum Inventory
	public int getMinINVT() {
		return MinINVT;
	}

	// Current Inventory
	public int getCurINVT() {
		return CurINVT;
	}

	public void setTopID(int topID) {
		TopID = topID;
	}

	public void setTopName(String topName) {
		TopName = topName;
	}

	public void setPerAMT(double perAMT) {
		PerAMT = perAMT;
	}

	public void setMedAMT(double medAMT) {
		MedAMT = medAMT;
	}

	public void setLgAMT(double lgAMT) {
		LgAMT = lgAMT;
	}

	public void setXLAMT(double xLAMT) {
		XLAMT = xLAMT;
	}

	public void setCustPrice(double custPrice) {
		CustPrice = custPrice;
	}

	public void setBusPrice(double busPrice) {
		BusPrice = busPrice;
	}

	public void setMinINVT(int minINVT) {
		MinINVT = minINVT;
	}

	public void setCurINVT(int curINVT) {
		CurINVT = curINVT;
	}

	@Override
	public String toString() {
		return "Topping [TopID=" + TopID + ", TopName=" + TopName + ", PerAMT=" + PerAMT + ", MedAMT=" + MedAMT
				+ ", LgAMT=" + LgAMT + ", XLAMT=" + XLAMT + ", CustPrice=" + CustPrice + ", BusPrice=" + BusPrice
				+ ", MinINVT=" + MinINVT + ", CurINVT=" + CurINVT + "]";
	}
	
	

}

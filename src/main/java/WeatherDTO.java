import java.util.ArrayList;

public class WeatherDTO {

	String location;
	String date;
	int HF;
	int UV;
	int WC;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHF() {
		return HF;
	}

	public int getUV() {
		return UV;
	}

	public void setUV(int uV) {
		UV = uV;
	}

	public int getWC() {
		return WC;
	}

	public void setWC(int wC) {
		WC = wC;
	}

	public void setHF(int hF) {
		HF = hF;
	}

}

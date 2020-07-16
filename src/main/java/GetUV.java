import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class GetUV {

	String filePath = null;
	ArrayList<WeatherDTO> arr = new ArrayList<WeatherDTO>();

	public ArrayList<WeatherDTO> getHeatFeeling() {
		try {

			for (int month = 5; month < 6; month++) {

				for (int day = 1; day < 32; day++) {

					if ((month == 6 || month == 9) && day == 31)
						break;

					if (day < 10) {
						filePath = "C://Users/LJO/Desktop/weather/UV/20190" + month + "/FCT_IDX_A20_2019050" + day
								+ "06_NEW.csv";
					} else {

						filePath = "C://Users/LJO/Desktop/weather/UV/20190" + month + "/FCT_IDX_A20_201905" + day
								+ "06_NEW.csv";
					}

					InputStreamReader is = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
					CSVReader reader = new CSVReader(is);
					List<String[]> list = reader.readAll();

					for (String[] str : list) {

						if (str[1].equals("5000000000")) {
							WeatherDTO entity = new WeatherDTO();
							entity.setLocation(str[1]);
							entity.setDate(str[2]);
							entity.setHF(Integer.valueOf(str[6]));
							arr.add(entity);
						}
					}

				}
			}

			for (WeatherDTO entity : arr) {
				System.out.println(
						"지역코드 : " + entity.getLocation() + " 날짜 : " + entity.getDate() + " 지수 : " + entity.getHF());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return arr;
	}

}

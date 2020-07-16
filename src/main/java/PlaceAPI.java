import java.util.ArrayList;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceAPI {

	public static void main(String[] args) {

		try {

			String urlstr = "https://maps.googleapis.com/maps/api/place/textsearch/json?&";
			String key = "AIzaSyAzIiN1B7yb4X8kGJCu5LQJjEk3mFa4pKc";
			String next_page_token = null;
			ArrayList<String> type = new ArrayList<String>();
			ArrayList<String> query = new ArrayList<String>();
			String url = null;
			PlaceObject placeObject = null;
			String json = null;

			type.add("tourist_attraction");
			// type.add("restaurant");
			// type.add("cafe");

			query.add("제주특별자치도+서귀포시");
			query.add("제주특별자치도+제주시");

			API_DAO dao = new API_DAO();

			for (int i = 0; i < query.size(); i++) {

				for (int j = 0; j < 3; j++) {

					placeObject = null;

					if (j == 0)
						url = urlstr + "query=" + query.get(i) + "&type=" + type.get(0) + "&key=" + key
								+ "&language=ko";
					else {

						url = urlstr + "pagetoken=" + next_page_token + "&key=" + key;
						Thread.sleep(3000);
					}

					System.out.println(url);

					OkHttpClient client = new OkHttpClient();
					Request request = new Request.Builder().url(url).build();
					Response response = client.newCall(request).execute();
					json = response.body().string();
					// System.out.println(json);

					Gson gson = new Gson();
					placeObject = gson.fromJson(json, PlaceObject.class);
					next_page_token = placeObject.next_page_token;
					System.out.println(next_page_token);

					if (next_page_token == null) {

						PlaceObject_lastPage placeObject2 = gson.fromJson(json, PlaceObject_lastPage.class);
						dao.insertPlaceID2(placeObject2);
						System.out.println("size :" + placeObject2.results.size() + ", i :" + i + ", j :" + j);
						break;

					} else {
						dao.insertPlaceID(placeObject);
						System.out.println("size :" + placeObject.results.size() + ", i :" + i + ", j :" + j);
					}

					// System.out.println(placeObject.results.get(i).place_id);

					System.out.println(
							"-----------------------------------------------------------------------------------------------------------------------------\n");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

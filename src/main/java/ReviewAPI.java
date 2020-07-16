
import java.util.ArrayList;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReviewAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			API_DAO dao = new API_DAO();
			ArrayList<String> place_list = dao.getPlaceID();

			String urlstr = "https://wextractor.com/api/v1/reviews?";
			String auth_token = "9c9bd2fc70fb4482a4a0662493947a180786e52b";
			// String id = "ChIJd3UrcbkUDTURBbzMfk3-b2M";

			String offset = null;
			String url = null;
			String json = null;

			for (int i = 0; i < place_list.size(); i++) {

				ArrayList<ReviewObject> reviewObjects = new ArrayList<ReviewObject>();
				System.out.println(place_list.get(i));

				for (int j = 0; j < 3; j++) {

					offset = Integer.toString(j * 10);
					url = urlstr + "auth_token=" + auth_token + "&hl=ko&sort=relevancy&id=" + place_list.get(0)
							+ "&offset=" + offset;
					System.out.println(url);

					OkHttpClient client = new OkHttpClient();
					Request request = new Request.Builder().url(url).build();
					Response response = client.newCall(request).execute();
					json = response.body().string();

					System.out.println(json);
					Gson gson = new Gson();
					ReviewObject reviewObject = gson.fromJson(json, ReviewObject.class);

					System.out.println(reviewObject.reviews.get(j).toString());
					reviewObjects.add(reviewObject);

				}
				dao.insertReview(reviewObjects, place_list.get(i));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

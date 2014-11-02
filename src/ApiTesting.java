import java.io.IOException;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiTesting {
	public static void main(String[] args) {
		HttpResponse<JsonNode> response = null;
		JSONObject jsonObject = null;
		String token = "";
		String url = "";
		String status = "";
		String tags = "";
		try {
			response = Unirest
					.post("https://camfind.p.mashape.com/image_requests")
					.header("X-Mashape-Key",
							"5t3yOLRJbFmshGyThqjVJeQI7ZUpp1Rk9ScjsntdT1azJ1xYm2")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.field("image_request[language]", "en")
					.field("image_request[locale]", "en_US")
					.field("image_request[remote_image_url]",
							"http://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/"
									+ "articles/health_tools/extreme_eats_slideshow/getty_rf_photo_of_penne_pasta.jpg")
					.asJson();

			jsonObject = new JSONObject(response.getBody().toString());
			token = jsonObject.getString("token");
			url = jsonObject.getString("url");

			System.out.println(response.getBody());
			System.out.println("token: " + token + " url: " + url);
			
			System.out.println("https://camfind.p.mashape.com/image_responses/" + token);

			response = Unirest
					.get("https://camfind.p.mashape.com/image_responses/" + token)
					.header("X-Mashape-Key", "5t3yOLRJbFmshGyThqjVJeQI7ZUpp1Rk9ScjsntdT1azJ1xYm2")
					.asJson();

			jsonObject = new JSONObject(response.getBody().toString());
			status = jsonObject.getString("status");
			while (status.contains("not completed")){
				try {
					Thread.sleep(10000);
					response = Unirest
							.get("https://camfind.p.mashape.com/image_responses/" + token)
							.header("X-Mashape-Key", "5t3yOLRJbFmshGyThqjVJeQI7ZUpp1Rk9ScjsntdT1azJ1xYm2")
							.asJson();
					jsonObject = new JSONObject(response.getBody().toString());
					System.out.println(response.getBody());
					status = jsonObject.getString("status");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (jsonObject.has("name")) {
				tags = jsonObject.getString("name");
			}
			System.out.println("status: " + status + "\nTags: " + tags);

			Unirest.shutdown();
		} catch (UnirestException | IOException e) {
			e.printStackTrace();
		}
	}

	public static String getToken(String s) {
		JSONObject jsonObject = new JSONObject(s);
		String attr1 = jsonObject.getString("token");
		return attr1;
	}
}

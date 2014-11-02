import java.io.IOException;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiTestingClarifai {
	public static void main(String[] args) {
		HttpResponse<JsonNode> response = null;
		JSONObject jsonObject = null;
		String url = "https://api.clarifai.com/v1/token/";
		String secret = "vUiBikoM8lQN48kRCSBCh36qA-rYhoIJ1jc6LKkD";
		String clientId = "-QD9nuVdUiex6jTXuIdEMvbACqM7HR27qgeJabmi";
		String token = "", scope = "", token_type = "", tags = "";
		int expires_in = 0;
		try {
			response = Unirest
					.post(url)
					.field("grant_type", "client_credentials")
					.field("client_id", clientId)
					.field("client_secret", secret)
					.asJson();
			/* Unirest instructions
			   .header = -H
			   .field = -d
			*/

			jsonObject = new JSONObject(response.getBody().toString());
			token = jsonObject.getString("access_token");
			expires_in = jsonObject.getInt("expires_in");
			scope = jsonObject.getString("scope");
			token_type = jsonObject.getString("token_type");

			System.out.println(response.getBody());
			System.out.println("access_token: " + token);
			System.out.println("expires_in: " + expires_in);
			System.out.println("scope: " + scope);
			System.out.println("token_type: " + token_type);
			
			// Stub need to finish the request just getting token at this point
			
			/*System.out.println("Authorization: Bearer <access_token>" +
				      "https://api.clarifai.com/v1/tag/?url=http://www.clarifai.com/img/metro-north.jpg");
			
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
			*/
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

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
		String urlToken = "https://api.clarifai.com/v1/token/";
		String urlTags = "https://api.clarifai.com/v1/tag/";
		String secret = "vUiBikoM8lQN48kRCSBCh36qA-rYhoIJ1jc6LKkD";
		String clientId = "-QD9nuVdUiex6jTXuIdEMvbACqM7HR27qgeJabmi";
		String token = "", scope = "", tokenType = "", tags = "";
		int expires_in = 0;
		try {
			/*
			   Unirest instructions: http://unirest.io/java.html
			   .header = -H
			   .field = -d
			   
			   jsonObject Instructions
			   .getString = label for the attribute u want
			   .getInt = label for attribute u want that is an int
			   
			   use .getJSONObject to get a sub object
			   ex: JSONObject subObject = object.getJSONObject("Example");
			   to get an array object
			   JSONArray subArray = subObject.getJSONArray("example");
			   
			   then parse the array
			   String strParsedValue = null;
			   strParsedValue += "\n Array Length => " + subArray.length();
			   for(int i=0; i < subArray.length(); i++){
					strParsedValue+="\n"+subArray.getJSONObject(i).getString("sub1_attr").toString();
				}
				
				Article for JSON Parsing: http://www.technotalkative.com/android-json-parsing/
			*/
			response = Unirest
					.post(urlToken)
					.field("grant_type", "client_credentials")
					.field("client_id", clientId)
					.field("client_secret", secret)
					.asJson();
			
			jsonObject = new JSONObject(response.getBody().toString());
			token = jsonObject.getString("access_token");
			expires_in = jsonObject.getInt("expires_in");
			scope = jsonObject.getString("scope");
			tokenType = jsonObject.getString("token_type");

			System.out.println(response.getBody());
			System.out.println("access_token: " + token);
			System.out.println("expires_in: " + expires_in);
			System.out.println("scope: " + scope);
			System.out.println("token_type: " + tokenType);
			
			//System.out.println("Authorization: " + tokenType + " " + token + " -d url=http://www.clarifai.com/img/metro-north.jpg " + urlToken);
			
			// Stub need to finish the request just getting token at this point
			response = Unirest
					.post(urlTags)
					.header("Authorization", tokenType + " " + token)
					.field("url", "http://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/articles/health_tools/extreme_eats_slideshow/getty_rf_photo_of_penne_pasta.jpg")
					.asJson();
			
			System.out.println(response.getBody());
			/*
			
			
		 	
			System.out.println("Authorization: Bearer <access_token>" +
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

package restapiTestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.API_Payloads;
import resources.API_Resources;

public class SampleWebSiteTest {
	
	Properties prop;
	@Before
	public void loadProperties() throws FileNotFoundException, IOException
	{
		prop=new Properties();
		prop.load(new FileInputStream(new File(".\\src\\test\\resources\\env.properties")));
		
	}
		
	  @Test public void getCountries() {
	  RestAssured.baseURI=prop.getProperty("getCountriesAPI_host");
	  
	  given().header("X-RapidAPI-Key",prop.getProperty("X-RapidAPI-Key")).
	  when().get("/all").
	  then().assertThat().statusCode(200).and().header(
	  "access-control-allow-methods", equalTo("GET"));
	  
	  }
	  
	  
	  @Test public void getPlaceAPI() { // TODO Auto-generated method stub
	  
	  //BaseURL or Host 
	  RestAssured.baseURI=prop.getProperty("getPlaceAPI_host");
	  
	  Map<String, String> expectedHeaders=new HashMap<String, String>();
	  expectedHeaders.put("server", "scaffolding on HTTPServer2");
	  
	  given(). param("location","-33.8670522,151.1957362"). param("radius","500").
	  param("key",prop.getProperty("getPlaceAPI_key")). when().
	  get("/maps/api/place/nearbysearch/json").
	  then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
	  . body("results[0].name",equalTo("Sydney")).and().
	  body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
	  headers(expectedHeaders);
	  
	  
	  }
	 
	@Test
	public void addPlace_post() {
		RestAssured.baseURI = prop.getProperty("addAPI_host");

		// get the post response into variable
		Response postResponse = given().queryParam("key", prop.getProperty("addplace_key"))
				.body(API_Payloads.addMap_Payload())
				.when().post(API_Resources.placePostData()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();

		System.out.println("Raw Data :" + postResponse);
		System.out.println("Response as String :" + postResponse.asString());

		// converts the string into json path and place that entire object into js
		JsonPath js = new JsonPath(postResponse.asString());
		System.out.println(js);
		String placeId = js.get("place_id").toString();
		System.out.println("PlaceID: " + placeId);

		Response deleteResponse = given().queryParam("key", prop.getProperty("addplace_key"))
				.body(API_Payloads.deleteMap_Payload(placeId))
				.when()
				.delete(API_Resources.deletePostData())
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().header("server", containsStringIgnoringCase("apache")).extract().response();

		
		System.out.println("delete response :" + deleteResponse.asString());
		JsonPath djs = new JsonPath(deleteResponse.asString());
		System.out.println(djs.get("status"));
		
		}
}

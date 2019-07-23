package payloads;

public class API_Payloads {
	
	public static String addMap_Payload()
	{
		String b="{\r\n" + "    \"location\":{\r\n" + "        \"lat\" : -38.383494,\r\n"
				+ "        \"lng\" : 33.427362\r\n" + "    },\r\n" + "    \"accuracy\":50,\r\n"
				+ "    \"name\":\"Frontline house\",\r\n" + "    \"phone_number\":\"(+91) 983 893 3937\",\r\n"
				+ "    \"address\" : \"29, side layout, cohen 09\",\r\n"
				+ "    \"types\": [\"shoe park\",\"shop\"],\r\n"
				+ "    \"website\" : \"http://google.com\",\r\n" + "    \"language\" : \"French-IN\"\r\n"
				+ "}\r\n" + "";
		return b;
	}
	
	public static String deleteMap_Payload(String placeId)
	{
		String b="{\r\n" + "    \"place_id\":\"" + placeId + "\"\r\n" + "}\r\n" + "";
		return b;
	}

}

package com.roadArchitectWeb.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonRead {
	public static void main(String[] args) {
		JsonParser jsonParser = new JsonParser();
		JsonReader jsonReader=null;
		try {
			jsonReader = new JsonReader(new FileReader("src/test.json"));//这里为什么不能用相对路径
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonReader);
		
		String start = jsonObject.get("start").getAsString();
		
		JsonArray middleObject = jsonObject.get("middle").getAsJsonArray();
		
		int middle1 = ((JsonObject) middleObject.get(0)).get("1").getAsInt();
		String middle2a = ((JsonObject) middleObject.get(0)).get("2").getAsString();
		int middle3 = ((JsonObject) middleObject.get(1)).get("3").getAsInt();
		String middle4a = ((JsonObject) middleObject.get(1)).get("4").getAsString();
		int middle5 = ((JsonObject) middleObject.get(2)).get("5").getAsInt();
		String middle6a = ((JsonObject) middleObject.get(2)).get("6").getAsString();
		
		Boolean end = jsonObject.get("end").getAsBoolean();
		System.out.println("JsonRead.main():"+start);
		System.out.println("JsonRead.main():"+middle1+" "+middle2a+" "+middle3+" "+middle4a+" "+middle5+" "+middle6a);
		System.out.println("JsonRead.main():"+end);
	}
	
	
}

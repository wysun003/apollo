package com.roadArchitectWeb.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class createjson {
	public static void main(String[] args) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("start", "This is start");
		
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject1 = new JsonObject();
		jsonObject1.addProperty("1", 1);
		jsonObject1.addProperty("2", 2);
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("3", "3");
		jsonObject2.addProperty("4", "4");
		JsonObject jsonObject3 = new JsonObject();
		jsonObject3.addProperty("5", true);
		jsonObject3.addProperty("6", false);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);
		
		jsonObject.add("middle", jsonArray);
		jsonObject.addProperty("end", "This is end");
		
		System.out.println("createjson.main():"+jsonObject.toString());
	}
}

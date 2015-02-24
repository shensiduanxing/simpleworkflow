package org.eason.workflowengine.ui.resteasy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CommonTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		System.out.println(System.currentTimeMillis());
		
		String jsonAppTaskQueue = "{\"provider\":\"rabbitmq\", \"username\":\"guest\", \"password\":\"guest\",\"vhost\":\"/\",\"exchange\":\"admin-taskExchange\"}";
		Map<String, String> retMap = new Gson().fromJson(jsonAppTaskQueue,  
                new TypeToken<Map<String, String>>() {  
                }.getType()); 
		String password = retMap.get("password");
		System.out.println(password);
	}

}

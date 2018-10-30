package com.philips.backend;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.philips.backend.PhilipsApplication;
import com.philips.backend.dao.Location;
import com.philips.backend.dao.Users;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-06
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhilipsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PhilipsApplicationTests {

	@LocalServerPort
	private int port = Configurations.PORT;
	private int userID;
	private int locationID;

	TestRestTemplate restTemplate = new TestRestTemplate();
	private static final Logger LOGGER = Logger.getLogger(PhilipsApplication.class.getName());

	private JsonConverter jsonConverter = new JsonConverter();
	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testAll() {
		testUserRestAPIs();
	}

	public void testUserRestAPIs() {
		testAddUser();
		testRetrieveUser();
		testRetrieveAllUsers();
		testUpdateUser();
		testDeleteUser();
		testAddLocation();
		testRetrieveLocation();
		testRetrieveAllLocations();
		testUpdateLocation();
		testDeleteLocation();	
		testSuccessLogin(); 
	}
	
	
	public void testSuccessLogin()
	{
		testAddUser();
		Users user = new Users(); 
		user.setUserName("userName");
		user.setPassword("password");
		LOGGER.info("\nLogin User Request to : " + createURLWithPort("/login"));
		LOGGER.info("\nLogin User Request : \n" + jsonConverter.objectToJson(user));
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/login"), HttpMethod.POST, entity,
				String.class);
		LOGGER.info("\nLogin User Response : \n" + response.getBody().toString());
		LOGGER.info("\nLogin User status : " + response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
	}

	public void testAddUser() {

		Users user = new Users("userName9", "password", new Date(), "ميسرة محمد", "title", "mail", "01282842176",  "address", "userType"); 
		LOGGER.info("\nAdd User Request to : " + createURLWithPort("/users"));
		LOGGER.info("\nAdd User Request : \n" + jsonConverter.objectToJson(user));

		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.POST, entity,
				String.class);
		LOGGER.info("\nAdd User Response : \n" + response.getBody());
		LOGGER.info("\nAddUser status : " + response.getStatusCodeValue());
		userID = getID(response.getBody().toString(), "/users");
		assertEquals(201, response.getStatusCodeValue());
	}

	public void testUpdateUser() {
		Users user = new Users("userName", "password", new Date(), "ميسرة محمد", "title updated", "mail", "01282842176",  "address", "userType");
		;
		LOGGER.info("\nPut User Request to : HOST:PORT/user/");
		LOGGER.info("\nPut User Request : \n" + jsonConverter.objectToJson(user));

		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.PUT,
				entity, String.class);
		LOGGER.info("\nPut User response status : " + response.getStatusCodeValue());
		LOGGER.info("\nPut User response : " + response.getBody().toString());
		if (response.getStatusCodeValue() > 200) {
			assertEquals(201, response.getStatusCodeValue());
		} else {
			assertEquals(200, response.getStatusCodeValue());
		}
	}

	public void testDeleteUser() {
		Users user = new Users();
		LOGGER.info("\nDelete User Request to : HOST:PORT/users/" + userID);
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users/" + userID),
				HttpMethod.DELETE, entity, String.class);
		LOGGER.info("\nDelete User response status : " + response.getStatusCodeValue());
		assertEquals(204, response.getStatusCodeValue());
	}

	public void testRetrieveUser() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		LOGGER.info("\n Get User Request to : HOST:PORT/users/" + userID);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users/" + userID), HttpMethod.GET,
				entity, String.class);
		LOGGER.info("\nGet User response : " + response.getBody().toString());
		LOGGER.info("\nGet User response status : " + response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
	}

	public void testRetrieveAllUsers() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		LOGGER.info("\n Get All Users Request to : HOST:PORT/users");
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users"), HttpMethod.GET, entity,
				String.class);
		LOGGER.info("\nGet All Users response : " + response.getBody().toString());
		LOGGER.info("\nGet All Users response status : " + response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	
	public void testAddLocation() {

		Location location = new Location(223.348787, 443.834787); 
		LOGGER.info("\nAdd Location Request to : " + createURLWithPort("/locations"));
		LOGGER.info("\nAdd Location Request : \n" + jsonConverter.objectToJson(location));

		HttpEntity<Location> entity = new HttpEntity<Location>(location, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/locations"), HttpMethod.POST, entity,
				String.class);
		LOGGER.info("\nAdd Location Response : \n" + response.getBody().toString());
		LOGGER.info("\nAdd Location status : " + response.getStatusCodeValue());
		locationID = getID(response.getBody().toString(), "/locations");
		assertEquals(201, response.getStatusCodeValue());
	}

	public void testUpdateLocation() {
		Location location = new Location(223.348787, 443.834787); 
		LOGGER.info("\nPut Location Request to : HOST:PORT/locations/" + locationID);
		LOGGER.info("\nPut Location Request : \n" + jsonConverter.objectToJson(location));

		HttpEntity<Location> entity = new HttpEntity<Location>(location, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/locations/" + locationID), HttpMethod.PUT,
				entity, String.class);
		LOGGER.info("\nPut Location response status : " + response.getStatusCodeValue());
		LOGGER.info("\nPut Location response : " + response.getBody().toString());
		if (response.getStatusCodeValue() > 200) {
			assertEquals(201, response.getStatusCodeValue());
		} else {
			assertEquals(200, response.getStatusCodeValue());
		}
	}

	public void testDeleteLocation() {
		Location location = new Location();
		LOGGER.info("\nDelete Location Request to : HOST:PORT/locations/" + locationID);
		HttpEntity<Location> entity = new HttpEntity<Location>(location, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/locations/" + locationID),
				HttpMethod.DELETE, entity, String.class);
		LOGGER.info("\nDelete Location response status : " + response.getStatusCodeValue());
		assertEquals(204, response.getStatusCodeValue());
	}

	public void testRetrieveLocation() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		LOGGER.info("\n Get Location Request to : HOST:PORT/locations/" + locationID);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/locations/" + locationID), HttpMethod.GET,
				entity, String.class);
		LOGGER.info("\nGet Location response : " + response.getBody().toString());
		LOGGER.info("\nGet Location response status : " + response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
	}

	public void testRetrieveAllLocations() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		LOGGER.info("\n Get All Locations Request to : HOST:PORT/locations");
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/locations"), HttpMethod.GET, entity,
				String.class);
		LOGGER.info("\nGet All Locations response : " + response.getBody().toString());
		LOGGER.info("\nGet All Locations response status : " + response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
	}
	
	

	private int getID(String result, String url) {
		String r2 = result.substring(result.indexOf(url) + url.length() + 1);
		int id = Integer.parseInt(r2.substring(0, r2.indexOf("\n") - 1).replace("\"", ""));
		return id;
	}

	private String createURLWithPort(String uri) {
		return "http://" + Configurations.HOST + ":" + port + uri;
	}

}

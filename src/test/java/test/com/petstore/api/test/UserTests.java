package test.com.petstore.api.test;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.com.petstore.api.endpoints.EndPoints;
import test.com.petstore.api.payload.User;

public class UserTests {
    User userPayload;

    @BeforeClass
    public void setUp()
    {
        userPayload = new User();
        userPayload.setId(1);
        userPayload.setUsername("EmreUsl");
        userPayload.setFirstname("Emre");
        userPayload.setLastname("Usul");
        userPayload.setEmail("EmreUsl@gmail.com");
        userPayload.setPassword("enuygun123");
        userPayload.setPhone("05435444444");
    }

    @Test(priority = 1)
    public void test_createUser(){
        Response response = EndPoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}

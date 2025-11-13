package test.com.petstore.api.test;

import com.enuygun.utils.DriverFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.com.petstore.api.endpoints.EndPoints;
import test.com.petstore.api.payload.User;

public class UserTests {
    User userPayload = new User();

    @BeforeClass
    public void setUp()
    {
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

    @Test(priority = 2)
    public void test_readUser(){
        Response response = EndPoints.readUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void test_updateUser(){
        userPayload.setPhone("05435333333");

        Response response = EndPoints.updateUser(userPayload.getUsername(), userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        String updatedPhone = response.jsonPath().getString("phone");
        Assert.assertEquals(updatedPhone, userPayload.getPhone());
    }

    @Test(priority = 4)
    public void test_deleteUser(){
        Response response = EndPoints.deleteUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}

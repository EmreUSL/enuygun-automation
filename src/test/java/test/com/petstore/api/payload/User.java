package test.com.petstore.api.payload;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    int id;
    @Setter
    @Getter
    String username;
    @Getter
    @Setter
    String firstname;
    @Getter
    @Setter
    String lastname;
    @Getter
    @Setter
    String email;
    @Getter
    @Setter
    String password;
    @Getter
    @Setter
    String phone;
    @Getter
    @Setter
    int userStatus = 0;


}

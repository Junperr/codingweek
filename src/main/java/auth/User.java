package auth;

import java.util.UUID;

public class User {
    private String firstName, lastName, userName, email, passWord, address, city;
    private int zipCode;
    private UUID uuid;

    public User(String firstName, String lastName, String userName, String email, String passWord, String address, String city, int zipCode, UUID uuid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.uuid = uuid;
    }

    private void changeUsername(String newName) {
        this.userName = newName;
    }

    private void changeEmail(String newEmail) {
        this.email = newEmail;
    }
}

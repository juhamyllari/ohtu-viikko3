package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {
    
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int PASSWORD_MIN_LENGTH = 8;

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        if (username.length() < USERNAME_MIN_LENGTH) {
            return true;
        }
        if (!username.matches("^[a-z]*$")) {
            return true;
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            return true;
        }
        if (password.chars().allMatch(i -> Character.isAlphabetic(i))) {
            return true;
        }

        return false;
    }
}

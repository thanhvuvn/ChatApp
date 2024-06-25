package dao;

import entities.User;

import java.util.List;

public interface UserDao extends BaseDao<User, Long> {
    public boolean checkLogin(String username, String password);
    boolean checkIfUserExists(String username);
    void registerUser(String username, String password);
}

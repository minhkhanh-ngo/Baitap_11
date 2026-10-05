package vn.iotstar.giuaki.services;

import vn.iotstar.giuaki.model.User_24110248;
import java.util.List;

public interface IUserService_24110248 {
    int countUsers();
    List<User_24110248> findAll(int offset, int limit);
    User_24110248 findById(String username);
    boolean insert(User_24110248 u);
    boolean update(User_24110248 u);
    boolean delete(String username);
}
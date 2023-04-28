package org.battleship.commons.database;

import java.util.List;

public interface UserRepository {
    User getById(long id);
    User getByUsername(String username);
    List<User> getAll();
    User save(User user);
    void delete(User user);
}

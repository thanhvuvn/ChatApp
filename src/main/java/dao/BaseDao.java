package dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<E, ID extends Serializable> {

    ID save(E e);

    List<E> getAll();

    E getOne(ID id);

    void update(E e);

    void delete(ID id);

}

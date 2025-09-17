package app.daos;

import java.util.List;

public interface IDAO<T, I> {
    List<T> create(List<T> list);

    T create(T t);

    T getById(I id);

    List<T> getAll();

    T update(T t);

    boolean delete(I id);
}

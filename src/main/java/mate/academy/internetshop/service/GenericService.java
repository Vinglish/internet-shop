package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, L> {
    T create(T element);

    T get(L id);

    T update(T element);

    List<T> getAll();

    boolean delete(L id);
}

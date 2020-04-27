package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, L> {
    T create(T element);

    Optional<T> get(L id);

    T update(T element);

    List<T> getAll();

    boolean delete(L id);
}

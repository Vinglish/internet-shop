package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, L> {

    Optional<T> get(L id);

    List<T> getAll();

    boolean delete(L id);
}

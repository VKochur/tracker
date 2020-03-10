package team.mediasoft.education.tracker.dto.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper from entity to dto
 * @param <E> entity
 * @param <D> dto
 */
public interface Mapper<E, D> {

    D getDto(E entity);

    E getEntity(D dto);

    default List<D> getListDto(List<E> entities) {
        List<D> dtos = new ArrayList<>(entities.size());
        for (E entity : entities) {
            D dto = getDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }
}

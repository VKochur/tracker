package team.mediasoft.education.tracker.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mapper from entity to dto
 * @param <E> entity
 * @param <D> dto
 */
public interface Mapper<E, D> {

    D getDto(E entity);

    default List<D> getListDto(List<E> entities) {
        List<D> dtos = new ArrayList<>(entities.size());
        for (E entity : entities) {
            D dto = getDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     *
     * @param entity
     * @return dto optional. if entity is Optional.EMPTY, then return Optional.EMPTY
     */
    default Optional<D> getDto(Optional<E> entity) {
        if (entity.isPresent()) {
            return Optional.of(this.getDto(entity.get()));
        } else {
            return Optional.empty();
        }
    }
}

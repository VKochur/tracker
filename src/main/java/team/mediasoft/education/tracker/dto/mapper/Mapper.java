package team.mediasoft.education.tracker.dto.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper from entity to dto
 * @param <E> entity
 * @param <D> dto output
 * @param <I> dto input
 */
public interface Mapper<E, D, I> {

    D getOutput(E entity);

    E getForCreation(I entityInput);

    default List<D> getListOutputs(List<E> entities) {
        List<D> dtos = new ArrayList<>(entities.size());
        for (E entity : entities) {
            D dto = getOutput(entity);
            dtos.add(dto);
        }
        return dtos;
    }
}

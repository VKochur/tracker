package team.mediasoft.education.tracker.repository.custom.impl;

import team.mediasoft.education.tracker.repository.custom.TypeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Optional;

public class TypeRepositoryCustomImpl implements TypeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Long> findIdByNameIgnoreCase(String typeName) {
        String sql =
                "select id from types " +
                        "where UPPER(name) = UPPER(:typeName)";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("typeName", typeName);

        try {
            BigInteger id = (BigInteger) nativeQuery.getSingleResult();
            return Optional.of(id.longValue());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}

package team.mediasoft.education.tracker.repository.custom.impl;

import team.mediasoft.education.tracker.repository.custom.PackRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PackRepositoryCustomImpl implements PackRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    //sql
    @Override
    public List<Long> findPackIdsWhoseRouteHasLoop() {
        String sql = "with subquery as\n" +
                "(select distinct\n" +
                " node_id,\n" +
                " package_id,\n" +
                " min(happened) over(partition by node_id, package_id) as first_time,\n" +
                " max(happened) over(partition by node_id, package_id) as last_time\n" +
                "from another_test.story_points\n" +
                "order by package_id, first_time)\n" +
                "select\n" +
                " sub.package_id\n" +
                "from subquery sub\n" +
                "inner join subquery sup on sub.package_id = sup.package_id and sup.first_time > sub.first_time and sup.first_time < sub.last_time";
        Query nativeQuery = em.createNativeQuery(sql);
        List<BigInteger> queryResult = nativeQuery.getResultList();
        return queryResult.stream().map(bigInteger -> bigInteger.longValue()).collect(Collectors.toList());
    }

    //criteriaBuilder
    @Override
    public List<Long> findPackIdsWhichWereInNodeAtTime(Long nodeId, LocalDateTime from, LocalDateTime to) {
        return null;
    }
}

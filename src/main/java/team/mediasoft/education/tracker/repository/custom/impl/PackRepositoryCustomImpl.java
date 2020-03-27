package team.mediasoft.education.tracker.repository.custom.impl;

import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.repository.custom.PackRepositoryCustom;

import javax.persistence.*;
import javax.persistence.criteria.*;
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
                "from story_points\n" +
                "order by package_id, first_time)\n" +
                "select\n" +
                " sub.package_id\n" +
                "from subquery sub\n" +
                "inner join subquery sup on sub.package_id = sup.package_id and sup.first_time > sub.first_time and sup.first_time < sub.last_time";
        Query nativeQuery = em.createNativeQuery(sql);
        List<BigInteger> queryResult = nativeQuery.getResultList();
        return queryResult.stream().map(bigInteger -> bigInteger.longValue()).collect(Collectors.toList());
    }

    /*
        hibernate:
        select distinct
	         pack1_.id as col_0_0_
        from story_points storypoint0_
                                         inner join packages pack1_ on storypoint0_.package_id=pack1_.id
                                         inner join nodes node2_ on storypoint0_.node_id=node2_.id
        where ?=node2_.id and (storypoint0_.happened between ? and ?)
     */
    @Override
    public List<Long> findPackIdsWhichWereInNodeAtTime(Long nodeId, LocalDateTime fromDate, LocalDateTime toDate) {
        //query creation
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

        Root<StoryPoint> root = query.from(StoryPoint.class);

        query.select(root.join("pack").get("id")).distinct(true);

        ParameterExpression<Long> nodeIdParam = criteriaBuilder.parameter(Long.class);
        ParameterExpression<LocalDateTime> fromDateParam = criteriaBuilder.parameter(LocalDateTime.class);
        ParameterExpression<LocalDateTime> toDateParam = criteriaBuilder.parameter(LocalDateTime.class);

        Predicate equalNode;
        equalNode = criteriaBuilder.equal(nodeIdParam, root.join("place").get("id"));

        Predicate betweenInterval= criteriaBuilder.between(root.get("point"), fromDateParam, toDateParam);
        query.where(criteriaBuilder.and(equalNode, betweenInterval));

        //query execution
        TypedQuery<Long> queryExecutable = em.createQuery(query);
        queryExecutable.setParameter(nodeIdParam, nodeId);
        queryExecutable.setParameter(fromDateParam, fromDate);
        queryExecutable.setParameter(toDateParam, toDate);
        return queryExecutable.getResultList();
    }
}

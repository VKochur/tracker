/**
 * Basic services.
 *
 * All operations, that use only jpaRepository's methods, entity's type
 * are similar for all entities.
 *
 * Uses:
 *      JpaRepository<E, ID>. In order to work with storage
 *      WrapFactory<E, SurfaceException>. In order to get instance container with entity or exception inside
 *
 * @param <ID> entity's id's type
 * @param <E> entity's type
 * @param <I> input dto's type
 */
package team.mediasoft.education.tracker.service.basic;
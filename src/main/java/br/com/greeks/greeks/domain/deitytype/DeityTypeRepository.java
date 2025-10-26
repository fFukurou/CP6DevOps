package br.com.greeks.greeks.domain.deitytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeityTypeRepository extends JpaRepository<DeityType, Long> {

    Optional<DeityType> findByTypeNameIgnoreCase(String typeName);

    @Query("SELECT CASE WHEN COUNT(dt) > 0 THEN true ELSE false END FROM DeityType dt WHERE LOWER(dt.typeName) = LOWER(:typeName) AND dt.id <> :id")
    boolean existsByTypeNameIgnoreCaseAndIdNot(@Param("typeName") String typeName, @Param("id") Long id);

    @Query("""
            SELECT dt FROM DeityType dt
            WHERE (:typeName IS NULL OR LOWER(dt.typeName) LIKE LOWER(CONCAT('%', :typeName, '%')))
              AND (:origin IS NULL OR LOWER(dt.origin) LIKE LOWER(CONCAT('%', :origin, '%')))
            """)
    Page<DeityType> findAllFiltered(
            @Param("typeName") String typeName,
            @Param("origin") String origin,
            Pageable pageable
    );
}
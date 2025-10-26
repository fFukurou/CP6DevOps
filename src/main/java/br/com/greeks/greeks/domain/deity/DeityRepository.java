package br.com.greeks.greeks.domain.deity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeityRepository extends JpaRepository<Deity, Long> {


    Optional<Deity> findByNameIgnoreCase(String name);


    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Deity d WHERE LOWER(d.name) = LOWER(:name) AND d.id <> :id")
    boolean existsByNameIgnoreCaseAndIdNot(@Param("name") String name, @Param("id") Long id);


    @Query("""
            SELECT d FROM Deity d JOIN FETCH d.deityType dt
            WHERE (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:domain IS NULL OR LOWER(d.domain) LIKE LOWER(CONCAT('%', :domain, '%')))
              AND (:deityTypeId IS NULL OR dt.id = :deityTypeId)
            """)
    Page<Deity> findAllFiltered(
            @Param("name") String name,
            @Param("domain") String domain,
            @Param("deityTypeId") Long deityTypeId,
            Pageable pageable
    );


     @Query("SELECT d FROM Deity d JOIN FETCH d.deityType")
     Page<Deity> findAllWithDeityType(Pageable pageable);

     @Query("SELECT d FROM Deity d JOIN FETCH d.deityType dt WHERE d.id = :id")
     Optional<Deity> findByIdWithDeityType(@Param("id") Long id);
}
package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.TokenEntity;
import com.ceyentra.springboot.visitersmanager.entity.UserEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Integer> {

    @Query(value = """
      select t from TokenEntity t inner join UserEntity u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenEntity> findAllValidTokenByUser(Integer id);

    Optional<TokenEntity> findByToken(String token);

    @Query(value = "DELETE FROM token t WHERE t.user_id = :id", nativeQuery = true)
    @Modifying
    void deleteByUserId(@Param("id") int id);
}

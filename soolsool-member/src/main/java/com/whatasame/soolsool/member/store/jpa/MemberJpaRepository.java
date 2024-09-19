package com.whatasame.soolsool.member.store.jpa;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberJpaRepository extends Repository<MemberJpo, Long> {

    MemberJpo save(MemberJpo memberJpo);

    Optional<MemberJpo> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}

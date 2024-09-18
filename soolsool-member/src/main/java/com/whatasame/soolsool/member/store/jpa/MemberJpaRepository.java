package com.whatasame.soolsool.member.store.jpa;

import org.springframework.data.repository.Repository;

public interface MemberJpaRepository extends Repository<MemberJpo, Long> {

    MemberJpo save(MemberJpo memberJpo);
}

package shop.soolsool.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.soolsool.member.domain.model.MemberMileageUsage;

public interface MemberMileageUsageRepository extends JpaRepository<MemberMileageUsage, Long> {}

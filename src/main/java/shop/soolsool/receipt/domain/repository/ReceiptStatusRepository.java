package shop.soolsool.receipt.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.soolsool.receipt.domain.model.ReceiptStatus;
import shop.soolsool.receipt.domain.model.vo.ReceiptStatusType;

@Repository
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus, Long> {

    Optional<ReceiptStatus> findByType(final ReceiptStatusType receiptStatusType);
}

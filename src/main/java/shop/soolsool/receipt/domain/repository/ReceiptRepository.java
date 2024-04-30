package shop.soolsool.receipt.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.soolsool.receipt.domain.model.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Override
    @Query("select r from Receipt r join fetch r.receiptItems where r.id = :receiptId")
    Optional<Receipt> findById(final Long receiptId);
}

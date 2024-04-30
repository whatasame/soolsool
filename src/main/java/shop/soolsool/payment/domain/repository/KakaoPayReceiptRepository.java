package shop.soolsool.payment.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.soolsool.payment.domain.model.KakaoPayReceipt;

public interface KakaoPayReceiptRepository extends JpaRepository<KakaoPayReceipt, Long> {

    Optional<KakaoPayReceipt> findByReceiptId(final Long receiptId);
}

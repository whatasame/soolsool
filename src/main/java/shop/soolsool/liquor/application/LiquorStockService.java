package shop.soolsool.liquor.application;

import static shop.soolsool.liquor.code.LiquorErrorCode.NOT_LIQUOR_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.domain.model.Liquor;
import shop.soolsool.liquor.domain.model.LiquorStocks;
import shop.soolsool.liquor.domain.repository.LiquorRepository;
import shop.soolsool.liquor.domain.repository.LiquorStockRepository;
import shop.soolsool.liquor.ui.dto.LiquorStockSaveRequest;

@Service
@RequiredArgsConstructor
public class LiquorStockService {

    private final LiquorRepository liquorRepository;
    private final LiquorStockRepository liquorStockRepository;

    @Transactional
    public Long saveLiquorStock(final LiquorStockSaveRequest request) {
        final Liquor liquor = liquorRepository
                .findById(request.getLiquorId())
                .orElseThrow(() -> new SoolSoolException(NOT_LIQUOR_FOUND));
        liquor.increaseTotalStock(request.getStock());

        return liquorStockRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void decreaseLiquorStock(final Long liquorId, final int quantity) {
        final LiquorStocks liquorStocks = new LiquorStocks(liquorStockRepository.findAllByLiquorIdNotExpired(liquorId));
        liquorStocks.decreaseStock(quantity);

        liquorStockRepository.deleteAllInBatch(liquorStocks.getOutOfStocks());
    }
}

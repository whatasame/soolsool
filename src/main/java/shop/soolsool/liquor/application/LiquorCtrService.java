package shop.soolsool.liquor.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.soolsool.liquor.domain.model.LiquorCtr;
import shop.soolsool.liquor.domain.repository.LiquorCtrRedisRepository;
import shop.soolsool.liquor.domain.repository.LiquorCtrRepository;
import shop.soolsool.liquor.ui.dto.LiquorClickAddRequest;
import shop.soolsool.liquor.ui.dto.LiquorImpressionAddRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class LiquorCtrService {

    private final LiquorCtrRepository liquorCtrRepository;

    private final LiquorCtrRedisRepository liquorCtrRedisRepository;

    @Transactional(readOnly = true)
    public double getLiquorCtrByLiquorId(final Long liquorId) {
        return liquorCtrRedisRepository.getCtr(liquorId);
    }

    public void increaseImpression(final LiquorImpressionAddRequest request) {
        request.getLiquorIds().forEach(liquorCtrRedisRepository::increaseImpression);
    }

    public void increaseClick(final LiquorClickAddRequest request) {
        liquorCtrRedisRepository.increaseClick(request.getLiquorId());
    }

    @Transactional
    public void writeBackCtr(final LiquorCtr latestLiquorCtr) {
        liquorCtrRepository.updateLiquorCtr(
                latestLiquorCtr.getImpression(), latestLiquorCtr.getClick(), latestLiquorCtr.getLiquorId());
    }
}

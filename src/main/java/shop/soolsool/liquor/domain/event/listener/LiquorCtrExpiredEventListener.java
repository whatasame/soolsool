package shop.soolsool.liquor.domain.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import shop.soolsool.liquor.application.LiquorCtrService;
import shop.soolsool.liquor.domain.event.LiquorCtrExpiredEvent;

@Component
@RequiredArgsConstructor
public class LiquorCtrExpiredEventListener {

    private final LiquorCtrService liquorCtrService;

    @Async
    @EventListener
    public void expiredListener(final LiquorCtrExpiredEvent event) {
        liquorCtrService.writeBackCtr(event.getLiquorCtr());
    }
}

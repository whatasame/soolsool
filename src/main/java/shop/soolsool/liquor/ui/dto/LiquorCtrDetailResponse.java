package shop.soolsool.liquor.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LiquorCtrDetailResponse {

    private final double ctr;

    @JsonCreator
    public LiquorCtrDetailResponse(final double ctr) {
        this.ctr = ctr;
    }
}

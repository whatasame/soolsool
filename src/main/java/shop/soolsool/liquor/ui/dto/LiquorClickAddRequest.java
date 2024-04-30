package shop.soolsool.liquor.ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LiquorClickAddRequest {

    private final Long liquorId;

    @JsonCreator
    public LiquorClickAddRequest(final Long liquorId) {
        this.liquorId = liquorId;
    }
}

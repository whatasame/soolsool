package shop.soolsool.liquor.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.liquor.code.LiquorErrorCode;

@Getter
@EqualsAndHashCode
public class LiquorImageUrl {

    private static final int MAX_LENGTH = 255;

    private final String imageUrl;

    public LiquorImageUrl(final String imageUrl) {
        validateIsNotNullOrEmpty(imageUrl);
        validateIsValidLength(imageUrl);

        this.imageUrl = imageUrl;
    }

    private void validateIsValidLength(final String imageUrl) {
        if (imageUrl.length() > MAX_LENGTH) {
            throw new SoolSoolException(LiquorErrorCode.INVALID_LENGTH_IMAGE_URL);
        }
    }

    private void validateIsNotNullOrEmpty(final String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            throw new SoolSoolException(LiquorErrorCode.NO_CONTENT_IMAGE_URL);
        }
    }
}

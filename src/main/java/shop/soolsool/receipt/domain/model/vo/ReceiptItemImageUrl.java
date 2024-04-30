package shop.soolsool.receipt.domain.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;
import shop.soolsool.core.exception.SoolSoolException;
import shop.soolsool.receipt.code.ReceiptErrorCode;

@Getter
@EqualsAndHashCode
public class ReceiptItemImageUrl {

    private static final int MAX_LENGTH = 255;

    private final String imageUrl;

    public ReceiptItemImageUrl(final String imageUrl) {
        validateIsNotNullOrEmpty(imageUrl);
        validateIsValidLength(imageUrl);

        this.imageUrl = imageUrl;
    }

    private void validateIsValidLength(final String imageUrl) {
        if (imageUrl.length() > MAX_LENGTH) {
            throw new SoolSoolException(ReceiptErrorCode.INVALID_LENGTH_IMAGE_URL);
        }
    }

    private void validateIsNotNullOrEmpty(final String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) {
            throw new SoolSoolException(ReceiptErrorCode.NO_CONTENT_IMAGE_URL);
        }
    }
}

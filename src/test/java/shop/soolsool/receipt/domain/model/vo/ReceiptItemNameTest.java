package shop.soolsool.receipt.domain.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shop.soolsool.core.exception.SoolSoolException;

@DisplayName("단위 테스트: ReceiptItemName")
class ReceiptItemNameTest {

    @Test
    @DisplayName("주문서 술 이름을 정상적으로 생성한다.")
    void create() {
        /* given */
        final String name = "마싯는 소주";

        /* when & then */
        assertThatCode(() -> new ReceiptItemName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문서 술 이름이 null 혹은 공백일 경우 SoolSoolException을 던진다.")
    void createFailWithNullOrEmpty(final String name) {
        /* given */

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemName(name))
                .isExactlyInstanceOf(SoolSoolException.class)
                .hasMessage("주문서 술 이름은 null이거나 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 이름이 100자를 초과할 경우 SoolSoolException을 던진다.")
    void createFailInvalidLength() {
        /* given */
        final String name = "소".repeat(101);

        /* when & then */
        assertThatThrownBy(() -> new ReceiptItemName(name))
                .isExactlyInstanceOf(SoolSoolException.class)
                .hasMessage("주문서 술 이름은 100자보다 길 수 없습니다.");
    }

    @Test
    @DisplayName("주문서 술 이름이 동일하면 동일한 객체이다.")
    void equalsAndHashCode() {
        /* given */
        final ReceiptItemName origin = new ReceiptItemName("마싯는 소주");
        final ReceiptItemName same = new ReceiptItemName("마싯는 소주");
        final ReceiptItemName different = new ReceiptItemName("맛없는 소주");

        /* when & then */
        assertAll(
                () -> assertThat(origin).isEqualTo(same),
                () -> assertThat(origin).hasSameHashCodeAs(same),
                () -> assertThat(origin).isNotEqualTo(different),
                () -> assertThat(origin).doesNotHaveSameHashCodeAs(different));
    }
}

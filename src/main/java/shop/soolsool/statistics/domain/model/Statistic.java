package shop.soolsool.statistics.domain.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.soolsool.statistics.domain.model.converter.BrewTypeConverter;
import shop.soolsool.statistics.domain.model.converter.ClickConverter;
import shop.soolsool.statistics.domain.model.converter.ImpressionConverter;
import shop.soolsool.statistics.domain.model.converter.RegionConverter;
import shop.soolsool.statistics.domain.model.converter.SalePriceConveter;
import shop.soolsool.statistics.domain.model.converter.SaleQuantityConverter;
import shop.soolsool.statistics.domain.model.vo.BrewType;
import shop.soolsool.statistics.domain.model.vo.Click;
import shop.soolsool.statistics.domain.model.vo.Impression;
import shop.soolsool.statistics.domain.model.vo.Region;
import shop.soolsool.statistics.domain.model.vo.SalePrice;
import shop.soolsool.statistics.domain.model.vo.SaleQuantity;

@Entity
@Table(name = "statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Statistic {

    @EmbeddedId
    private StatisticId statisticId;

    @Column(name = "region")
    @Convert(converter = RegionConverter.class)
    private Region region;

    @Column(name = "brew_type")
    @Convert(converter = BrewTypeConverter.class)
    private BrewType brewType;

    @Column(name = "impression", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = ImpressionConverter.class)
    private Impression impression;

    @Column(name = "click", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = ClickConverter.class)
    private Click click;

    @Column(name = "sale_quantity", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SaleQuantityConverter.class)
    private SaleQuantity saleQuantity;

    @Column(name = "sale_price", columnDefinition = "decimal(19,2) default 0.0")
    @Convert(converter = SalePriceConveter.class)
    private SalePrice salePrice;

    @Builder
    public Statistic(
            final Region region,
            final BrewType brewType,
            final Impression impression,
            final Click click,
            final SaleQuantity saleQuantity,
            final SalePrice salePrice) {
        this.region = region;
        this.brewType = brewType;
        this.impression = impression;
        this.click = click;
        this.saleQuantity = saleQuantity;
        this.salePrice = salePrice;
    }
}

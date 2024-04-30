package shop.soolsool.member.domain.model.converter;

import java.math.BigInteger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberMileage;

@Converter
public class MemberMileageConverter implements AttributeConverter<MemberMileage, BigInteger> {

    @Override
    public BigInteger convertToDatabaseColumn(final MemberMileage mileage) {
        return mileage.getMileage();
    }

    @Override
    public MemberMileage convertToEntityAttribute(final BigInteger dbData) {
        return new MemberMileage(dbData);
    }
}

package shop.soolsool.member.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberAddress;

@Converter
public class MemberAddressConverter implements AttributeConverter<MemberAddress, String> {

    @Override
    public String convertToDatabaseColumn(final MemberAddress address) {
        return address.getAddress();
    }

    @Override
    public MemberAddress convertToEntityAttribute(final String dbData) {
        return new MemberAddress(dbData);
    }
}

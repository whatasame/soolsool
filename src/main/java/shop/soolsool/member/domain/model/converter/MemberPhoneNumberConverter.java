package shop.soolsool.member.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberPhoneNumber;

@Converter
public class MemberPhoneNumberConverter implements AttributeConverter<MemberPhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(final MemberPhoneNumber phoneNumber) {
        return phoneNumber.getPhoneNumber();
    }

    @Override
    public MemberPhoneNumber convertToEntityAttribute(final String dbData) {
        return new MemberPhoneNumber(dbData);
    }
}

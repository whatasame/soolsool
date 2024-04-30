package shop.soolsool.member.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberPassword;

@Converter
public class MemberPasswordConverter implements AttributeConverter<MemberPassword, String> {

    @Override
    public String convertToDatabaseColumn(final MemberPassword password) {
        return password.getPassword();
    }

    @Override
    public MemberPassword convertToEntityAttribute(final String dbData) {
        return new MemberPassword(dbData);
    }
}

package shop.soolsool.member.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberEmail;

@Converter
public class MemberEmailConverter implements AttributeConverter<MemberEmail, String> {

    @Override
    public String convertToDatabaseColumn(final MemberEmail email) {
        return email.getEmail();
    }

    @Override
    public MemberEmail convertToEntityAttribute(final String dbData) {
        return new MemberEmail(dbData);
    }
}

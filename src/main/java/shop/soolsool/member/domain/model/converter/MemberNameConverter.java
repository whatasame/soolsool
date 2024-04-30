package shop.soolsool.member.domain.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.soolsool.member.domain.model.vo.MemberName;

@Converter
public class MemberNameConverter implements AttributeConverter<MemberName, String> {

    @Override
    public String convertToDatabaseColumn(final MemberName name) {
        return name.getName();
    }

    @Override
    public MemberName convertToEntityAttribute(final String dbData) {
        return new MemberName(dbData);
    }
}

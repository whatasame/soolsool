package shop.soolsool.member.ui.dto;

import lombok.Builder;
import lombok.Getter;
import shop.soolsool.member.domain.model.Member;

@Getter
public class MemberDetailResponse {

    private final String roleName;
    private final String email;
    private final String name;
    private final String mileage;
    private final String address;

    @Builder
    public MemberDetailResponse(
            final String roleName, final String email, final String name, final String mileage, final String address) {
        this.roleName = roleName;
        this.email = email;
        this.name = name;
        this.mileage = mileage;
        this.address = address;
    }

    public static MemberDetailResponse from(final Member member) {
        return MemberDetailResponse.builder()
                .roleName(member.getRole().getName().getType())
                .email(member.getEmail().getEmail())
                .name(member.getName().getName())
                .mileage(member.getMileage().toString())
                .address(member.getAddress().getAddress())
                .build();
    }
}

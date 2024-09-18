package com.whatasame.soolsool.member.store.jpa;

import com.whatasame.soolsool.member.aggregate.Member;
import com.whatasame.soolsool.member.aggregate.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MemberJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    public MemberJpo(final Member member) {
        this.id = member.id();
        this.role = member.role();
        this.email = member.email();
        this.password = member.password();
        this.name = member.name();
        this.phone = member.phone();
        this.address = member.address();
    }

    public Member toMember() {
        return new Member(id, role, email, password, name, phone, address);
    }
}

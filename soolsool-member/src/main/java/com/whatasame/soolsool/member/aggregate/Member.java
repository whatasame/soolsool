package com.whatasame.soolsool.member.aggregate;

public record Member(
        Long id, MemberRole role, String email, String password, String name, String phone, String address) {}

package com.jmjung.table_reservation.controller;

import com.jmjung.table_reservation.config.TokenProvider;
import com.jmjung.table_reservation.model.member.SignInRequest;
import com.jmjung.table_reservation.model.member.SignUpRequest;
import com.jmjung.table_reservation.repository.role.Role;
import com.jmjung.table_reservation.repository.user.Member;
import com.jmjung.table_reservation.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<?> singup(
            @RequestBody SignUpRequest request
    ) {
        Member member = this.memberService.register(request);
        log.debug("회원가입: ", member.toString());
        return ResponseEntity.ok(member);
    }

    /**
     * 로그인
     */
    @PostMapping("/auth/signin")
    public ResponseEntity<?> sinein(
            @RequestBody SignInRequest request
    ) {
        Member member = memberService.authenticate(request);
        Role role = memberService.getRole(member);
        String token = tokenProvider.generateToken(member.getId(), role.getName());
        log.info("로그인: " + member.getId());
        return ResponseEntity.ok(token);
    }

}

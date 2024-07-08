package com.jmjung.table_reservation.service;

import com.jmjung.table_reservation.config.TokenProvider;
import com.jmjung.table_reservation.exception.auth.AlreadyExistUserException;
import com.jmjung.table_reservation.exception.auth.InvalidRoleException;
import com.jmjung.table_reservation.exception.auth.InvalidUserException;
import com.jmjung.table_reservation.model.member.SignInRequest;
import com.jmjung.table_reservation.model.member.SignUpRequest;
import com.jmjung.table_reservation.repository.role.Role;
import com.jmjung.table_reservation.repository.role.RoleRepository;
import com.jmjung.table_reservation.repository.user.Member;
import com.jmjung.table_reservation.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 fetch
     * @see TokenProvider 에서 사용
     * - 유저 id, role을 가져와서 UserDetails에 세팅
     * - 잘못된 user token인 경우 error throw
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository
                .findById(username)
                .orElseThrow(() ->new UsernameNotFoundException("not found username: " + username));
        Role role = getRole(member);
        return User.withUsername(username)
                .password(member.getPassword())
                .roles(role.getName())
                .build();
    }

    public Role getRole(Member member) {
        Long roleIdx = member.getRoleIdx();
        Role role = roleRepository
                .findByIdx(roleIdx)
                .orElseThrow(() -> new UsernameNotFoundException("not found role: " + roleIdx));
        return role;
    }

    /**
     * 유저 등록
     * - 회원 가입시 사용
     * - 이미 존재하는 유저, 잘못된 role 요청 에러 처리
     */
    public Member register(
            SignUpRequest request
    ) {
        if (memberRepository.existsById(request.getId())) {
            throw new AlreadyExistUserException();
        }

        if (!roleRepository.existsByIdx(request.getRoleIdx())) {
            throw new InvalidRoleException();
        }

        String encodedPassword = this.passwordEncoder.encode(request.getPassword());

        return memberRepository.save(request.toEntity(encodedPassword));
    }

    /**
     * 인증 처리
     * - 로그인 시도시 사용
     */
    public Member authenticate(
            SignInRequest request
    ) {
        String id = request.getId();
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException());

        boolean valid = this.passwordEncoder.matches(request.getPassword(), member.getPassword());
        if (!valid) {
            throw new InvalidUserException();
        }

        return member;
    }

}

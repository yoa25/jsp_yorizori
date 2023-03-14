package ezen.yorizori.domain.member.service;

import java.util.List;

import ezen.yorizori.domain.member.dto.Member;

/**
 * 회원 관련 비즈니스 메소드 정의 및 복잡한 트랜잭션 처리
 * @author 김민영
 * @Date   2023. 3. 13.
 */
public interface MemberService {
	//회원 등록
	public void registerMember(Member member) throws RuntimeException;
	//회원 전체 조회
	public List<Member> getMembers() throws RuntimeException;
	//회원 인증
	public Member isMember(String id, String password) throws RuntimeException;
}

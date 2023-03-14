package ezen.yorizori.domain.member.service;

import java.sql.SQLException;
import java.util.List;

import ezen.yorizori.domain.common.factory.DaoFactory;
import ezen.yorizori.domain.member.dao.MemberDao;
import ezen.yorizori.domain.member.dto.Member;

public class MemberServiceImpl implements MemberService{
	
	// RDB 정보 조회
	private MemberDao memberDao = DaoFactory.getInstance().getMemberDao();
	// Open API 정보 조회
	//private MemberDao memberDao = new APIMemberDao();


	@Override
	public void registerMember(Member member) throws RuntimeException{
		try {
			memberDao.create(member);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Member> getMembers() throws RuntimeException{
		try {
			return memberDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Member isMember(String id, String password) throws RuntimeException{
		Member member;
		try {
			member = memberDao.isMember(id,password);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return member;
	}

}

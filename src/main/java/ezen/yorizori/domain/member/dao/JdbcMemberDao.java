package ezen.yorizori.domain.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ezen.yorizori.domain.common.factory.DaoFactory;
import ezen.yorizori.domain.member.dto.Member;

public class JdbcMemberDao implements MemberDao {

	private DataSource dataSource;

	public JdbcMemberDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void create(Member member) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO member").append(" VALUES(?, ?, ?, ?, ?, SYSDATE)");
		try {
			// 커넥션을 풀링하고 있는 커넥션 팩토리부터 사용하지 않고 있는 커넥션 획득
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setInt(5, member.getAge());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				// 사용후 커넥션 반납 (닫는 것 아님)
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public Member isMember(String id, String password) throws SQLException {
		Member loginMember = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT member_id, name, email, age, regdate").append(" FROM member")
				.append(" WHERE member_id= ? AND password = ?");

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginMember = makeMember(rs);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return loginMember;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		List<Member> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT member_id, name, email, age, regdate").append(" FROM member")
				.append(" ORDER BY regdate DESC");
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = makeMember(rs);
				list.add(member);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	private Member makeMember(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setId(rs.getString("member_id"));
		member.setName(rs.getString("name"));
		member.setEmail(rs.getString("email"));
		member.setAge(rs.getInt("age"));
		member.setRegdate(rs.getDate("regdate"));
		return member;
	}

	// 테스트를 위한 임시 main
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		MemberDao dao = DaoFactory.getInstance().getMemberDao();
		String id = "koko";
		String password = "1111";
		Member loginMember = dao.isMember(id,password);
		// 비회원인 경우
		if (loginMember == null) {
			System.out.println("회원이 아닌가벼...");
		} else {
			System.out.println(loginMember.toString());
		}

		// 전체목록 조회
		List<Member> list = dao.findAll();
		System.out.println(list);

	}

}

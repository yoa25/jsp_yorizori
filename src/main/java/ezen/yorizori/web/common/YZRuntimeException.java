package ezen.yorizori.web.common;

/**
 * 사용자 정의 예외클래스
 * @author 김민영
 * @Date   2023. 3. 14.
 */
public class YZRuntimeException extends RuntimeException{
	private String redirectURL;
	
	public YZRuntimeException() {
		super("예기치 못한 장애가 발생하였습니다.");
	}
	
	public YZRuntimeException(String message) {
		super(message);
	}
	
	public YZRuntimeException(String message, String redirectURL) {
		super(message);
		this.redirectURL = redirectURL;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
}

/**
 * 
 */
package userrepositoryapioutputvo;

import java.util.List;

/**
 * @author Desktopadmin
 *
 */
public class UserRepositoryAPIOutputVO {

	public String resMsg;
	public String userId;
	public List<UserRepositoryAPIOutputErrorVO> userRepositoryAPIOutputErrorVO;
	
	/**
	 * @param resMsg
	 * @param userId
	 * @param userRepositoryAPIOutputErrorVO
	 */
	public UserRepositoryAPIOutputVO(String resMsg, String userId,
			List<UserRepositoryAPIOutputErrorVO> userRepositoryAPIOutputErrorVO) {
		super();
		this.resMsg = resMsg;
		this.userId = userId;
		this.userRepositoryAPIOutputErrorVO = userRepositoryAPIOutputErrorVO;
	}
	
	public UserRepositoryAPIOutputVO(){
		
	}

	/**
	 * @return the resMsg
	 */
	public String getResMsg() {
		return resMsg;
	}

	/**
	 * @param resMsg the resMsg to set
	 */
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userRepositoryAPIOutputErrorVO
	 */
	public List<UserRepositoryAPIOutputErrorVO> getUserRepositoryAPIOutputErrorVO() {
		return userRepositoryAPIOutputErrorVO;
	}

	/**
	 * @param userRepositoryAPIOutputErrorVO the userRepositoryAPIOutputErrorVO to set
	 */
	public void setUserRepositoryAPIOutputErrorVO(List<UserRepositoryAPIOutputErrorVO> userRepositoryAPIOutputErrorVO) {
		this.userRepositoryAPIOutputErrorVO = userRepositoryAPIOutputErrorVO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRepositoryAPIOutputVO [resMsg=");
		builder.append(resMsg);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userRepositoryAPIOutputErrorVO=");
		builder.append(userRepositoryAPIOutputErrorVO);
		builder.append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resMsg == null) ? 0 : resMsg.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((userRepositoryAPIOutputErrorVO == null) ? 0 : userRepositoryAPIOutputErrorVO.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRepositoryAPIOutputVO other = (UserRepositoryAPIOutputVO) obj;
		if (resMsg == null) {
			if (other.resMsg != null)
				return false;
		} else if (!resMsg.equals(other.resMsg))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userRepositoryAPIOutputErrorVO == null) {
			if (other.userRepositoryAPIOutputErrorVO != null)
				return false;
		} else if (!userRepositoryAPIOutputErrorVO.equals(other.userRepositoryAPIOutputErrorVO))
			return false;
		return true;
	}	
}

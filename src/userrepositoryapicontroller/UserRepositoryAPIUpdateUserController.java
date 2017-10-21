/**
 * 
 */
package userrepositoryapicontroller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import userRepositoryapiinputvo.UserRepositoryAPIInputVo;
import userrepositoryapihelper.UserRepositoryAPIValidationHelper;
import userrepositoryapioutputvo.UserRepositoryAPIOutputVO;
import userrepositoryapiutils.UserRepositoryAPIConstants;

/**
 * Controller class to process the Update User call
 * 
 * @author Desktopadmin
 *
 */
@Path("/updateUser")
public class UserRepositoryAPIUpdateUserController implements UserRepositoryAPIConstants {
	
	@POST
	@Produces("application/json")
	public UserRepositoryAPIOutputVO updateUser(UserRepositoryAPIInputVo userRepositoryAPIInputVo){
		UserRepositoryAPIOutputVO userRepositoryAPIOutputVO = null;
		
		userRepositoryAPIOutputVO = UserRepositoryAPIValidationHelper.processResponse(userRepositoryAPIInputVo, CHANNEL_UPDATE);
		
		
		return userRepositoryAPIOutputVO;
	}
}

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
 * Controller class to process the Create User call
 * 
 * @author Desktopadmin
 *
 */
@Path("/createUser")
public class UserRepositoryAPICreateUserController implements UserRepositoryAPIConstants {
	
	@POST
	@Produces("application/json")
	public UserRepositoryAPIOutputVO createUser(UserRepositoryAPIInputVo userRepositoryAPIInputVo){
		UserRepositoryAPIOutputVO userRepositoryAPIOutputVO = null;
		
		userRepositoryAPIOutputVO = UserRepositoryAPIValidationHelper.processResponse(userRepositoryAPIInputVo, CHANNEL_CREATE);
		
		
		return userRepositoryAPIOutputVO;
	}

}

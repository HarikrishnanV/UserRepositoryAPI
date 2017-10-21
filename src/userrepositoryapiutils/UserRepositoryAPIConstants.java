/**
 * 
 */
package userrepositoryapiutils;

/**
 * @author Desktopadmin
 *
 */
public interface UserRepositoryAPIConstants {

	//Channel values for API calls
	public static final String CHANNEL_CREATE = "Create";
	public static final String CHANNEL_UPDATE = "Update";
	public static final String CHANNEL_DELETE = "Delete";
	
	//Input Validations Error responses
	public static final String INPUT_NULL = "Input Data is null";
	public static final String USER_ID_EMPTY = "User ID is null or empty";
	public static final String DOB_EMPTY = "Date of birth is null or empty";
	public static final String DOB_INVALID_FUTURE = "Invalid Date of birth, cannot be future date";
	public static final String DOB_INVALID_FORMAT = "Inalid Date of birth format";
	public static final String PIN_INVALID = "Inalid Pin Code";
	public static final String ACTIVE_INDICATOR_INVALID = "is Active indicator should be Y or N";
	
	public static final String FILE_NAME = "C:\\test\\dataFile.txt";
	public static final String ERROR_WRITING_TO_FILE = "Unable to write contents to file";
	public static final String ERROR_READING_FROM_FILE = "Unable to read contents from file";
	
	public static final String USER_ID_UNAVAILABLE = "User Id not available";
}

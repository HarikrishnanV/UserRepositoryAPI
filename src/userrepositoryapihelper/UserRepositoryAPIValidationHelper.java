/**
 * 
 */
package userrepositoryapihelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import userRepositoryapiinputvo.UserRepositoryAPIInputVo;
import userrepositoryapioutputvo.UserRepositoryAPIOutputErrorVO;
import userrepositoryapioutputvo.UserRepositoryAPIOutputVO;
import userrepositoryapiutils.UserRepositoryAPIConstants;

/**
 * Class to perform Input Validations
 * 
 * @author Desktopadmin
 *
 */
public class UserRepositoryAPIValidationHelper implements UserRepositoryAPIConstants {

	/**
	 * @param userRepositoryAPIInputVo
	 * @return
	 */
	public static UserRepositoryAPIOutputVO processResponse(UserRepositoryAPIInputVo userRepositoryAPIInputVo,
			String channel) {
		UserRepositoryAPIOutputVO userRepositoryAPIOutputVO = null;

		// Log the input
		System.out.println("Performing input validations for : " + channel + " API call");
		System.out.println("Input Received : " + userRepositoryAPIInputVo.toString());

		// Validate the input
		userRepositoryAPIOutputVO = validateRequestData(userRepositoryAPIInputVo, channel);

		// If No errors in the output, good to go for adding user
		if (userRepositoryAPIOutputVO.getUserRepositoryAPIOutputErrorVO() == null
				|| userRepositoryAPIOutputVO.getUserRepositoryAPIOutputErrorVO().size() == 0) {
			String id = userRepositoryAPIInputVo.getId();
			String email = userRepositoryAPIInputVo.getEmail();

			// Check user id present in file already
			boolean userIdAvailable = verifyUserIdAvailable(id, email, userRepositoryAPIOutputVO);

			System.out.println("User Id : " + id + "userIdAvailable = " + userIdAvailable);

			// If user id doesn't exist in the file, then add it
			if (userIdAvailable) {
				System.out.println("User Id added successfully" + id);
				userRepositoryAPIOutputVO.setUserId(id);
			} else {
				userRepositoryAPIOutputVO.setResMsg("USER_ID_UNAVAILABLE");
				userRepositoryAPIOutputVO.setUserId(null);
			}
		}

		// Save the response to the file for create user api call
		if (CHANNEL_CREATE.equalsIgnoreCase(channel)) {
			saveSuccessResponseToFile(userRepositoryAPIInputVo, userRepositoryAPIOutputVO);
		}
		if (CHANNEL_DELETE.equalsIgnoreCase(channel) || CHANNEL_UPDATE.equalsIgnoreCase(channel)) {
			// For update and delete operations we already have the data in
			// input so update it
			updateExistingUser(userRepositoryAPIInputVo, userRepositoryAPIOutputVO, channel);

		}
		return userRepositoryAPIOutputVO;
	}

	private static UserRepositoryAPIOutputVO validateRequestData(UserRepositoryAPIInputVo userRepositoryAPIInputVo,
			String channel) {
		UserRepositoryAPIOutputVO userRepositoryAPIOutputVO = new UserRepositoryAPIOutputVO();
		List<UserRepositoryAPIOutputErrorVO> errorVOList = new ArrayList<UserRepositoryAPIOutputErrorVO>();

		if (userRepositoryAPIInputVo == null) {
			userRepositoryAPIOutputVO.setResMsg(INPUT_NULL);
			UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
			userRepositoryAPIOutputErrorVO.setCode(INPUT_NULL);
			errorVOList.add(userRepositoryAPIOutputErrorVO);
		}

		if (CHANNEL_CREATE.equalsIgnoreCase(channel) || CHANNEL_DELETE.equalsIgnoreCase(channel)) {
			if (userRepositoryAPIInputVo.getId() == null || userRepositoryAPIInputVo.getId().isEmpty()) {
				userRepositoryAPIOutputVO.setResMsg(USER_ID_EMPTY);
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(USER_ID_EMPTY);
				userRepositoryAPIOutputErrorVO.setField("ID");
				userRepositoryAPIOutputErrorVO.setMessage(USER_ID_EMPTY);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
			}
		}

		if (CHANNEL_UPDATE.equalsIgnoreCase(channel)) {
			Long pinCode = userRepositoryAPIInputVo.getPinCode();
			if (pinCode.longValue() == 0L) {
				userRepositoryAPIOutputVO.setResMsg(PIN_INVALID);
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(PIN_INVALID);
				userRepositoryAPIOutputErrorVO.setField("Pin Code");
				userRepositoryAPIOutputErrorVO.setMessage(PIN_INVALID);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
			}

			String dob = userRepositoryAPIInputVo.getBirthDate();
			if (dob == null || dob.isEmpty()) {
				userRepositoryAPIOutputVO.setResMsg(DOB_EMPTY);
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(DOB_EMPTY);
				userRepositoryAPIOutputErrorVO.setField("Birth Date");
				userRepositoryAPIOutputErrorVO.setMessage(DOB_EMPTY);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("DD-MON-YYYY");
			try {
				Date givendobDate = sdf.parse(dob);
				Long l = givendobDate.getTime();
				Date next = new Date(l);
				Date current = new Date();

				if (next.after(current) || next.equals(current)) {
					// Future date provided
					userRepositoryAPIOutputVO.setResMsg(DOB_INVALID_FUTURE);
					UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
					userRepositoryAPIOutputErrorVO.setCode(DOB_INVALID_FUTURE);
					userRepositoryAPIOutputErrorVO.setField("Birth Date");
					userRepositoryAPIOutputErrorVO.setMessage(DOB_INVALID_FUTURE);
					errorVOList.add(userRepositoryAPIOutputErrorVO);
				}
			} catch (ParseException e) {
				// Invalid format
				userRepositoryAPIOutputVO.setResMsg(DOB_INVALID_FORMAT);
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(DOB_INVALID_FORMAT);
				userRepositoryAPIOutputErrorVO.setField("Birth Date");
				userRepositoryAPIOutputErrorVO.setMessage(DOB_INVALID_FORMAT);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
			}
		}

		if (CHANNEL_DELETE.equalsIgnoreCase(channel)) {
			if (userRepositoryAPIInputVo.isActive() == null
					|| (!userRepositoryAPIInputVo.isActive().equalsIgnoreCase("Y")
							|| !userRepositoryAPIInputVo.isActive().equalsIgnoreCase("N"))) {
				userRepositoryAPIOutputVO.setResMsg(ACTIVE_INDICATOR_INVALID);
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(ACTIVE_INDICATOR_INVALID);
				userRepositoryAPIOutputErrorVO.setField("isActive");
				userRepositoryAPIOutputErrorVO.setMessage(ACTIVE_INDICATOR_INVALID);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
			}
		}
		userRepositoryAPIOutputVO.setUserRepositoryAPIOutputErrorVO(errorVOList);
		return userRepositoryAPIOutputVO;
	}

	private static void saveSuccessResponseToFile(UserRepositoryAPIInputVo userRepositoryAPIInputVo,
			UserRepositoryAPIOutputVO userRepositoryAPIOutputVO) {
		List<UserRepositoryAPIOutputErrorVO> errorVOList = new ArrayList<UserRepositoryAPIOutputErrorVO>();
		String contentToWrite = buildContentForFile(userRepositoryAPIInputVo);
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(FILE_NAME);
			bw = new BufferedWriter(fw);
			bw.write(contentToWrite);
		} catch (IOException e) {
			// If error writing to the file, then over write the success
			// response to fatal error
			userRepositoryAPIOutputVO.setResMsg("ERROR_WRITING_TO_FILE");
			UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
			userRepositoryAPIOutputErrorVO.setCode(ERROR_WRITING_TO_FILE);
			userRepositoryAPIOutputErrorVO.setField("BACKEND ISSUE");
			userRepositoryAPIOutputErrorVO.setMessage(ERROR_WRITING_TO_FILE);
			errorVOList.add(userRepositoryAPIOutputErrorVO);
			System.out.println("IOException observed, Message " + e.getMessage());
		} finally {

			try {
				if (fw != null) {
					fw.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				userRepositoryAPIOutputVO.setResMsg("ERROR_WRITING_TO_FILE");
				UserRepositoryAPIOutputErrorVO userRepositoryAPIOutputErrorVO = new UserRepositoryAPIOutputErrorVO();
				userRepositoryAPIOutputErrorVO.setCode(ERROR_WRITING_TO_FILE);
				userRepositoryAPIOutputErrorVO.setField("BACKEND ISSUE");
				userRepositoryAPIOutputErrorVO.setMessage(ERROR_WRITING_TO_FILE);
				errorVOList.add(userRepositoryAPIOutputErrorVO);
				System.out.println("IOException observed, Message " + e.getMessage());
			}
		}
	}

	private static String buildContentForFile(UserRepositoryAPIInputVo userRepositoryAPIInputVo) {
		StringBuilder contentToWrite = new StringBuilder();
		if (userRepositoryAPIInputVo != null) {
			contentToWrite.append("ID = ");
			contentToWrite.append(userRepositoryAPIInputVo.getId());
			contentToWrite.append(", FirstName = ");
			contentToWrite.append(userRepositoryAPIInputVo.getfName());
			contentToWrite.append(", LastName = ");
			contentToWrite.append(userRepositoryAPIInputVo.getlName());
			contentToWrite.append(", Email = ");
			contentToWrite.append(userRepositoryAPIInputVo.getEmail());
			contentToWrite.append(", PinCode = ");
			contentToWrite.append(userRepositoryAPIInputVo.getPinCode());
			contentToWrite.append(", DOB = ");
			contentToWrite.append(userRepositoryAPIInputVo.getBirthDate());
			contentToWrite.append(", isActive = ");
			contentToWrite.append(userRepositoryAPIInputVo.isActive());
			contentToWrite.append("\n");
		}
		return contentToWrite.toString();
	}

	private static boolean verifyUserIdAvailable(String id, String email,
			UserRepositoryAPIOutputVO userRepositoryAPIOutputVO) {
		boolean isUserIdAvailable = true;
		String idPattern = "ID = " + id;
		String emailPattern = "Email = " + email;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FILE_NAME));
			String fileContent;
			while ((fileContent = br.readLine()) != null) {
				if (fileContent.contains(idPattern) || fileContent.contains(emailPattern)) {
					System.out.println("User Id already available");
					isUserIdAvailable = false;
				}
			}
		} catch (FileNotFoundException e) {
			userRepositoryAPIOutputVO.setResMsg("ERROR_READING_FROM_FILE");
			System.out.println("FileNotFoundException observed, Message " + e.getMessage());
		} catch (IOException e) {
			userRepositoryAPIOutputVO.setResMsg("ERROR_READING_FROM_FILE");
			System.out.println("IOException observed, Message " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					userRepositoryAPIOutputVO.setResMsg("ERROR_READING_FROM_FILE");
					System.out.println("IOException observed, Message " + e.getMessage());
				}
			}
		}
		return isUserIdAvailable;
	}

	private static void updateExistingUser(UserRepositoryAPIInputVo userRepositoryAPIInputVo,
			UserRepositoryAPIOutputVO userRepositoryAPIOutputVO, String channel) {
		saveSuccessResponseToFile(userRepositoryAPIInputVo, userRepositoryAPIOutputVO);
	}
}

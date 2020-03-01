/**
 * 
 */
package com.user.pub.local_en.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.user.pub.local_en.dto.WebUserDTO;
import com.user.pub.local_en.service.WebUserAccountService;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
/**
 * @author Sanjeev
 *
 */


@SessionAttributes({ "currentUser" })
@Controller
public class WebUserLoginController {

	static final Logger log = LoggerFactory.getLogger(WebUserLoginController.class);

	@Autowired
	WebUserAccountService webUserAccountService;

	@RequestMapping(value = "/user/pub/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("errorMessge", "Username or Password is incorrect !!");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully !!");
		return "user/pub/login";
	}

	// After a successful login the control can be transfered anywhere as per
	// business need.
	@RequestMapping(value = "/user/pvt/postLogin", method = RequestMethod.GET)
	public String postLogin(Model model, HttpSession session) {

		log.info("postLogin()");

		// read principal out of security context and set it to session
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();

		validatePrinciple(authentication.getPrincipal());

		// User loggedInUser = ((PdfUserDetails)
		// authentication.getPrincipal()).getUserDetails();

		// model.addAttribute("currentUser", loggedInUser.getUsername());

		// session.setAttribute("userId", loggedInUser.getId());

		// return "redirect:/wallPage";
		return "user/pvt/dbPage";
	}

	@RequestMapping(value = "/user/pub/register", method = RequestMethod.GET)
	public ModelAndView register(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("register()");
		ModelAndView mv = new ModelAndView("user/pub/register");
		mv.addObject("webUserDTO", new WebUserDTO());
		return mv;
	}

	@RequestMapping(value = "/user/pub/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("user/pub/register");
		//
		try {
			if (result.hasErrors()) {
				// Get error message
			} else {
				// Implement business logic to save record into database
				webUserAccountService.saveAndUpdate(webUserDTO);
				model.addAttribute("message", "You have been registered successfully !!");
			}
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Ended");
		return mv;

	}

	@RequestMapping(value = "/user/pub/registerf/", method = RequestMethod.GET)
	public ModelAndView registerf(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("user/pub/register");
		try {
			System.out.println("Facebook Signup");
			webUserAccountService.saveAndUpdate(webUserDTO);
			model.addAttribute("message", "You have been registered successfully !!");

		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;

		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Ended");
		return mv;

	}

	@RequestMapping(value = "/user/pub/registergg/", method = RequestMethod.GET)
	public ModelAndView registergg(@Valid WebUserDTO webUserDTO, BindingResult result, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Started");
		ModelAndView mv = new ModelAndView("user/pub/register");
		try {
			System.out.println("Facebook Signup");
			webUserAccountService.saveAndUpdate(webUserDTO);
			model.addAttribute("message", "You have been registered successfully !!");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;

		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		mv.addObject("webUserDTO", webUserDTO);
		log.info("WebUserLoginController :==> saveAndUpdateRecord :==> Ended");
		return mv;
	}

	@RequestMapping(value = "/user/pub/fgotpwd", method = RequestMethod.GET)
	public String fgotpwd(Model model) {
		log.info("fgotpwd()");
		return "user/pub/fgotpwd";
	}

	@RequestMapping(value = "/user/pub/fgotpwd", method = RequestMethod.POST)
	public String sendFgotpwd(Model model) {
		log.info("fgotpwd()");

		return "user/pub/fgotpwd";
	}

	@RequestMapping(value = "/user/pvt/profile", method = RequestMethod.GET)
	public String profile(Model model) {
		log.info("profile()");

		return "user/pvt/profile";
	}

	@RequestMapping(value = "/user/pvt/db", method = RequestMethod.GET)
	public String db(Model model) {
		log.info("profile()");

		return "user/pvt/db";
	}

	@RequestMapping(value = "/user/pvt/rbacEnv", method = RequestMethod.GET)
	public String rbacEnv(Model model) {
		log.info("rbacEnv()");

		return "user/pvt/rbacEnv";
	}

	@RequestMapping(value = "/user/pvt/rbacBase", method = RequestMethod.GET)
	public String rbacBase(Model model) {
		log.info("rbacBase()");

		return "user/pvt/rbacBase";
	}

	@RequestMapping(value = "/user/pvt/rbacRts", method = RequestMethod.GET)
	public String rbacRts(Model model) {
		log.info("rbacRts()");

		return "user/pvt/rbacRts";
	}

	private void validatePrinciple(Object principal) {

		// if (!(principal instanceof PdfUserDetails)) {
		// throw new IllegalArgumentException("Principal can not be null!");
		// }
	}

}// End of WebUserLoginController
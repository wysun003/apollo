package test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import test.logic.LoginCheck;

@Controller
public class LoginController {
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String sayHello(){
		return "login.jsp";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(Model model, HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginCheck c = new LoginCheck();
        //c.test();
        String user_name = LoginCheck.check(username, password);
        if(user_name != null && user_name != ""){
            model.addAttribute("msg", user_name);
            return "success";
        }else{
            return "error";
        }
    }

}

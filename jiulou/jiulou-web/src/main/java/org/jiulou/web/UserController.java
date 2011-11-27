package org.jiulou.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiulou.service.BaseService;
import org.jiulou.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping(value="/users")
public class UserController extends BaseController {
	
	private static final String SQL_MAPPER_NAMESPASE = "com.xiamen.dao.user";
	@Resource
	private BaseService baseService;
	
	@RequestMapping(value="/add.do")
	public ModelAndView addPerson(User domin,Model model){
		
		Long id = 0L;
		try {
			id = (Long) this.baseService.add(SQL_MAPPER_NAMESPASE,domin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("newUserId", id);
		return new ModelAndView("addSucc.jsp");
	}
	
	@RequestMapping(value="add.jn",method=RequestMethod.POST)
	public View addPersonJson(User domin,Model model){
		
		Long id = 0L;
		try {
			id = (Long) this.baseService.add(SQL_MAPPER_NAMESPASE,domin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("newUserId", id);
		return new MappingJacksonJsonView();
	}
}
package org.jiulou.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jiulou.service.BaseService;
import org.jiulou.util.DatetimeUtil;
import org.jiulou.util.controller.ControllerUtil;
import org.jiulou.vo.Corporation;
import org.jiulou.vo.Pager;
import org.jiulou.vo.Rtn;
import org.jiulou.vo.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping(value = "/corporations")
public class CorporationController extends BaseController {

	private static final String SQL_MAPPER_NAMESPASE = "org.jiulou.dao.corporation";
	@Resource
	private BaseService baseService;

	@Resource(name = "corporationValidator")
	private Validator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		// 自定义日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DatetimeUtil.DATE_PATTERN);
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/add.do")
	//@ResponseBody
	public ModelAndView add(Corporation vo, BindingResult result,Model model,Locale locale) {

		Rtn rtn = new Rtn();
		rtn.setCode("0");
		rtn.setMsg("sucess");
		
		this.validator.validate(vo, result);
		if (result.hasErrors()) {
			rtn.setCode("10");
			rtn.setMsg("failed");
			model.addAttribute("vo",vo);
			model.addAttribute("fieldErrors",ControllerUtil.translateValidatorMsgs(result, locale));
		} else {
			Integer count = 0;
			try {
				count = (Integer) this.baseService
						.add(SQL_MAPPER_NAMESPASE, vo);
			} catch (Exception e) {
				rtn.setCode("-10");
				rtn.setMsg("error:" + e.getMessage());
			}
		}

		model.addAttribute("rtn", rtn);

		return new ModelAndView("/admin/corp/add.jsp", model.asMap());
	}
	
	@RequestMapping(value = "/findByPage.json.do")
	@ResponseBody
	public Map<String,Object> findByPage(Pager page,Map map,Model model,ModelMap modelMap
			,HttpServletRequest resuest,HttpSession session,Locale locale){
		Map<String,Object> rstMap = new HashMap<String,Object>();
		Rtn rtn = new Rtn();
		rtn.setCode("0");
		rtn.setMsg("sucess");
		rstMap.put("rtn", rtn);
		return rstMap;
	}

	@RequestMapping(value = "add.jn", method = RequestMethod.POST)
	public View addPersonJson(User domin, Model model) {

		Long id = 0L;
		try {
			id = (Long) this.baseService.add(SQL_MAPPER_NAMESPASE, domin);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("newUserId", id);
		return new MappingJacksonJsonView();
	}
}
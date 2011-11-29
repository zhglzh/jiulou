package org.jiulou.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.jiulou.service.BaseService;
import org.jiulou.util.DatetimeUtil;
import org.jiulou.vo.Corporation;
import org.jiulou.vo.Rtn;
import org.jiulou.vo.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/add.do")
	public ModelAndView add(@ModelAttribute("vo") Corporation vo, BindingResult result,Model model) {

		Rtn rtn = new Rtn();
		rtn.setCode("0");
		rtn.setMsg("sucess");

		this.validator.validate(vo, result);
		if (result.hasErrors()) {
			rtn.setCode("10");
			// StringBuilder sb = new StringBuilder();
			// for(ObjectError oe: result.getAllErrors()){
			// String str = oe.getCode();
			// }
			rtn.setMsg("failed");
			model.addAttribute("vo",vo);
			model.addAttribute("validatorMsgs",result.getModel());
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
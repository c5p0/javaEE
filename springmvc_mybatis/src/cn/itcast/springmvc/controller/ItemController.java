package cn.itcast.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.springmvc.pojo.Items;

@Controller
public class ItemController {
	
	@RequestMapping(value="/item/itemlist.action")
	public ModelAndView itemList()
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("itemList");
		return mav;
	}
}

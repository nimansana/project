package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.todoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")//서버의 url에 /sample/어떤글자 라고 있으면 아래의 클래스를 실행 이 클래스의 함수들은 이 url을 포함해야한다
//예를들어 http://localhost:8080/controller/sample/basicOnlyGet 식으로 sample이 없으면 안됨
@Log4j
public class SampleController {

	@RequestMapping("")//서버의 url에 ""이 있으면 아래의 void를 실행
	public void basic() {
		log.info("basic.........................");
	}
	
	@RequestMapping(value="/basic",method= {RequestMethod.GET,RequestMethod.POST})
	public void basic2() {
		log.info("basic2----------");
	}
	//@RequestMapping이 get이나 post로 값을 받는거에따라 @GetMapping 이나 @PostMapping으로 나누어진다(시간이 지나면서 발전함)
	@RequestMapping("/basicOnlyGet")
	public void basic3() {
		log.info("basicOnlyGet----------");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info(""+dto);
		
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name,@RequestParam("age")String age) {
		log.info("name : "+name);
		log.info("age : "+age);
		return "ex02";
	}
	//아래의것은 리스트로 값을 받을경우를 말함 ex02List?ids=aaa&ids=bbb&ids=ccc 같은경우
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids")ArrayList<String> ids) {
		log.info("ids:"+ids);
				
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids")String[] ids) {
		log.info("array ids:"+Arrays.toString(ids));
				
		return "ex02Array";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));	
	}
	
	@GetMapping("/ex03")
	public String ex03(todoDTO todo) {
		log.info("todo : "+todo);
		
		return "ex03";
	}
//	@GetMapping("/ex04")
//	public String ex04(SampleDTO dto,int page) {
//		log.info("DTO : "+dto);
//		log.info("page : "+page);
//		return "/sample/ex04";
//	} 주석된 방식으로는 page 값은 가는데 값을jsp에 나타내지 못했다 이는 타입에따라서 전달할수있나없나가있기때문
	//이를 Model을 사용해 int형을 넘긴다
	@GetMapping("/ex04")
	//public String ex04(SampleDTO dto, @ModelAttirbute("page") int page) 이렇게도 가능
	public String ex04(SampleDTO dto,int page,Model model) {
		log.info("DTO : "+dto);
		log.info("page : "+page);
		model.addAttribute("page",page);
		return "/sample/ex04";
	}
	
	
	@GetMapping("/ex05")
	public void aaa() {
		log.info("/ex05.......");
	}
	//이건 실행하면 json파일이 만들어진다
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06......");
		SampleDTO dto=new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity <String> ex07(){
		log.info("/ex07.....");
		//{name:홍길동}
		String msg="{\"name\":\"홍길동\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type","application/json;charset=UTF-8");
		return new ResponseEntity<>(msg,header,HttpStatus.OK);
	}

	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload..........");
	}

	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
//		files.forEach(file->{
//			log.info("------------------");
//			log.info("name : "+file.getOriginalFilename());
//			log.info("size : "+file.getSize());
//		});
//		위의 것과 같다
		log.info("play_exUploadPost");
		for(MultipartFile file:files) {
			log.info("------------------");
			log.info("name:"+file.getOriginalFilename());
			log.info("size:"+file.getSize());
		}
		
	}


}

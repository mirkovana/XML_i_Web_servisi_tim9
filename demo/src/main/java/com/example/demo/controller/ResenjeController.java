package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.resenje.DOMParser;

@RestController
@RequestMapping("/resenje")
public class ResenjeController {
	@RequestMapping("/")
    public String home(){
        return "Hello World!";
    }
	@GetMapping(path = "/{path}")
	public String printResenje(@PathVariable String path) {
		String filePath = "./data/" + path;

		DOMParser handler = new DOMParser();
		handler.buildDocument(filePath);
		handler.printAll();
		return "";
	}
	
	@GetMapping(path = "/{path}/{element}")
	public String printResenje(@PathVariable String path,
							@PathVariable String element) {
		String filePath = "./data/" + path;

		DOMParser handler = new DOMParser();
		handler.buildDocument(filePath);
		handler.printElement(element);
		return "";
	}
	@GetMapping(path = "/{path}/{element}/{attr}")
	public String printResenje(@PathVariable String path,
							@PathVariable String element,
							@PathVariable String attr) {
		String filePath = "./data/" + path;

		DOMParser handler = new DOMParser();
		handler.buildDocument(filePath);
		handler.printAttr(element, attr);
		return "";
	}
}

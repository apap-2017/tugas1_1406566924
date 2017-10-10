package com.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tugas1.model.PendudukModel;
import com.tugas1.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String detailPenduduk(Model model, @RequestParam(value="nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		log.info("berhasil get penduduk");
		
		if(penduduk != null) {
			
			model.addAttribute("penduduk", penduduk);
			return "penduduk-detail";
		}
		else {
			model.addAttribute("nik", nik);
			return "penduduk-tidak-ditemukan";
		}
	}
}

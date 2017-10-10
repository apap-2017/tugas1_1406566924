package com.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tugas1.model.KecamatanModel;
import com.tugas1.model.KeluargaModel;
import com.tugas1.model.KelurahanModel;
import com.tugas1.model.KotaModel;
import com.tugas1.model.PendudukModel;
import com.tugas1.service.KecamatanService;
import com.tugas1.service.KeluargaService;
import com.tugas1.service.KelurahanService;
import com.tugas1.service.KotaService;
import com.tugas1.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	KelurahanService kelurahanDAO;
	@Autowired
	KecamatanService kecamatanDAO;
	@Autowired
	KotaService kotaDAO;

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String detailPenduduk(Model model, @RequestParam(value="nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		log.info("berhasil get penduduk");
		
		if(penduduk != null) {
			KeluargaModel km = keluargaDAO.getKeluarga(penduduk.getIdKeluarga());
			penduduk.setAlamat(km.getAlamat());
			penduduk.setRt(km.getRt());
			penduduk.setRw(km.getRw());
			
			KelurahanModel kl = kelurahanDAO.getKelurahan(km.getIdKelurahan());
			penduduk.setNamaKelurahan(kl.getNamaKelurahan());
			System.out.println(penduduk.getNamaKelurahan());
			
			KecamatanModel kc = kecamatanDAO.getKecamatan(kl.getIdKecamatan());
			penduduk.setNamaKecamatan(kc.getNamaKecamatan());
			
			KotaModel kt = kotaDAO.getKota(kc.getIdKota());
			penduduk.setNamaKota(kt.getNamaKota());
			System.out.println(penduduk.getNamaKota());
			
			model.addAttribute("penduduk", penduduk);
			return "penduduk-detail";
		}
		else {
			model.addAttribute("nik", nik);
			return "penduduk-tidak-ditemukan";
		}
	}
}

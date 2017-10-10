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
import com.tugas1.service.KecamatanService;
import com.tugas1.service.KeluargaService;
import com.tugas1.service.KelurahanService;
import com.tugas1.service.KotaService;
import com.tugas1.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KeluargaController {
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

	@RequestMapping("/keluarga")
	public String detailPenduduk(Model model, @RequestParam(value = "nkk", required = false) String nomorKK) {
		KeluargaModel keluarga = keluargaDAO.getKeluargaByNKK(nomorKK);
		log.info("berhasil get keluarga");

		if (keluarga != null) {
			KelurahanModel kl = kelurahanDAO.getKelurahan(keluarga.getIdKelurahan());
			keluarga.setNamaKelurahan(kl.getNamaKelurahan());;
			
			KecamatanModel kc = kecamatanDAO.getKecamatan(kl.getIdKecamatan());
			keluarga.setNamaKecamatan(kc.getNamaKecamatan());
			
			KotaModel kt = kotaDAO.getKota(kc.getIdKota());
			keluarga.setNamaKota(kt.getNamaKota());
			
			model.addAttribute("keluarga", keluarga);
			return "keluarga-detail";
		} else {
			model.addAttribute("nomorKK", nomorKK);
			return "keluarga-tidak-ditemukan";
		}
	}
}

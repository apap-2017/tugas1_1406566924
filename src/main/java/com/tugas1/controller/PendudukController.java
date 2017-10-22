package com.tugas1.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public String index(Model model) {
		model.addAttribute("title", "Home");
		return "index";
	}
	
	@RequestMapping("/penduduk")
	public String detailPenduduk(Model model, @RequestParam(value="nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		log.info("berhasil get penduduk");
		
		if(penduduk != null) {
			KeluargaModel km = keluargaDAO.getKeluargaById(penduduk.getIdKeluarga());
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
			
			model.addAttribute("title", "Lihat Data Penduduk dengan NIK " + nik);
			return "penduduk-detail";
		}
		else {
			model.addAttribute("nik", nik);
			model.addAttribute("title", "Data Penduduk Tidak Ditemukan");
			return "penduduk-tidak-ditemukan";
		}
	}
	
	@RequestMapping("/penduduk/tambah")
	public String addPenduduk(Model model) {
//		List<KeluargaModel> listKeluarga = keluargaDAO.getAllKeluarga();
//		model.addAttribute("listKeluarga", listKeluarga);
		
		model.addAttribute("title", "Tambah Penduduk");
		return "penduduk-tambah";
	}
	
	@RequestMapping("/penduduk/tambah/submit")
	public String addPendudukSubmit(Model model, @RequestParam(value="nama", required=true) String nama, 
			@RequestParam(value="tempatLahir", required=true) String tempatLahir,
			@RequestParam(value="tanggalLahir", required=true) String tanggalLahir,
			@RequestParam(value="jenisKelamin", required=true) int jenisKelamin,
			@RequestParam(value="golonganDarah", required=true) String golonganDarah,
			@RequestParam(value="agama", required=true) String agama,
			@RequestParam(value="pekerjaan", required=true) String pekerjaan,
			@RequestParam(value="statusDalamKeluarga", required=true) String statusDalamKeluarga,
			@RequestParam(value="statusPerkawinan", required=true) String statusPerkawinan,
			@RequestParam(value="isWni", required=true) int isWni,
			@RequestParam(value="isWafat", required=true) int isWafat,
			@RequestParam(value="nomorKK", required=true) String nomorKK) throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tglLahir = format.parse(tanggalLahir);
		
		KeluargaModel keluarga = keluargaDAO.getKeluargaByNKK(nomorKK);
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluarga.getIdKelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getIdKecamatan());
		
		String nik = kecamatan.getKodeKecamatan().substring(0, 6); 
		System.out.println(nik);
		
		 
		// split tanggal lahir untuk nik 
		String[] tanggalKelahiran = tanggalLahir.split("-");
		int tanggal = Integer.parseInt(tanggalKelahiran[2]);
		// jika jenis kelamin wanita, tanggal ditambah 40
		if(jenisKelamin == 1) {
			tanggal +=40;
		}
		String bulan = tanggalKelahiran[1];
		String tahun = tanggalKelahiran[0].substring(2, 3);
		
		nik = nik + tanggal + bulan + tahun;
		System.out.println(nik);
		
		//cek apa ada yang lahir di kelurahan & tanggal lahir yang sama untuk tentuin 4 digit terakhir (urutan)
		String nikSama = pendudukDAO.getNikSamaPenduduk(nik);
		if(nikSama == null) {
			nik = nik + "0001";
			System.out.println(nik);
		}
		else {
			int urutan = Integer.parseInt(nikSama.substring(12)) + 1;
			String f = new DecimalFormat("0000").format(urutan);
			nik = nik + f;
			System.out.println(nik);
		}
		
		int id = pendudukDAO.getIdPendudukTerakhir() + 1;
		
		PendudukModel penduduk = new PendudukModel(id, nik, nama, tempatLahir, tglLahir, jenisKelamin, isWni, keluarga.getId(), agama, pekerjaan, statusPerkawinan, statusDalamKeluarga, golonganDarah, isWafat, null, null, null, null, null, null, null);
		penduduk.setNik(nik);
		penduduk.setNama(nama);
		penduduk.setTempatLahir(tempatLahir);
		penduduk.setTanggalLahir(tglLahir);
		penduduk.setJenisKelamin(jenisKelamin);
		penduduk.setIsWni(isWni);
		penduduk.setIdKeluarga(keluarga.getId());
		penduduk.setAgama(agama);
		penduduk.setPekerjaan(pekerjaan);
		penduduk.setStatusPerkawinan(statusPerkawinan);
		penduduk.setStatusDalamKeluarga(statusDalamKeluarga);
		penduduk.setGolonganDarah(golonganDarah);
		penduduk.setIsWafat(isWafat);
		
		pendudukDAO.addPenduduk(penduduk);
		
		model.addAttribute("nik", nik);
		model.addAttribute("title", "Tambah Penduduk Berhasil");
		return "penduduk-tambah-berhasil";
	}
}

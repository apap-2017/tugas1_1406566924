package com.tugas1.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
			model.addAttribute("title", "Lihat Data Keluarga dengan NKK " + nomorKK);
			return "keluarga-detail";
		} else {
			model.addAttribute("nomorKK", nomorKK);
			model.addAttribute("title", "Data Keluarga Tidak Ditemukan");
			return "keluarga-tidak-ditemukan";
		}
	}
	
	@RequestMapping("/keluarga/tambah")
	public String addKeluarga(Model model) {
		List<KotaModel> listKota = kotaDAO.getAllKota();
		
		model.addAttribute("listKota", listKota);
		model.addAttribute("title", "Tambah Keluarga");
		return "keluarga-tambah";
	}
	
	@RequestMapping("/kecamatan")
	public @ResponseBody List<KecamatanModel> selectKecamatan(Model model, @RequestParam(value="idKota", required=true) int idKota) {
		return kecamatanDAO.getAllKecamatanByIdKota(idKota);
	}
	
	@RequestMapping("/kelurahan")
	public @ResponseBody List<KelurahanModel> selectKelurahan(Model model, @RequestParam(value="idKecamatan", required=true) int idKecamatan) {
		return kelurahanDAO.getAllKelurahanByIdKecamatan(idKecamatan);
	}
	
	@RequestMapping("/keluarga/tambah/submit")
	public String addKeluargaSubmit(Model model, @RequestParam(value="alamat", required=true) String alamat, 
			@RequestParam(value="rt", required=true) String rt,
			@RequestParam(value="rw", required=true) String rw,
			@RequestParam(value="kelurahan", required=true) String namaKelurahan) {
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tanggalPembuatan = new Date();
		
		String hariIni = format.format(tanggalPembuatan);
		
		KelurahanModel kelurahan = kelurahanDAO.getKelurahanByNama(namaKelurahan.toUpperCase());
		System.out.println(kelurahan.getKodeKelurahan());
		
		String nomorKK = kelurahan.getKodeKelurahan().substring(0, 6);
		
		// split tanggal pembuatan/hari ini
		String[] tglPembuatan = hariIni.split("-");
		String tanggal = tglPembuatan[2];
		String bulan = tglPembuatan[1];
		String tahun = tglPembuatan[0].substring(2);
		
		nomorKK = nomorKK + tanggal + bulan + tahun;
		System.out.println(nomorKK);
		
		//cek apa ada yang dibuat di kelurahan & tanggal pembuatan yang sama untuk tentuin 4 digit terakhir (urutan)
		String nkkSama = keluargaDAO.getNkkSamaKeluarga(nomorKK);
		if(nkkSama == null) {
			nomorKK = nomorKK + "0001";
			System.out.println(nomorKK);
		}
		else {
			int urutan = Integer.parseInt(nkkSama.substring(12)) + 1;
			String f = new DecimalFormat("0000").format(urutan);
			nomorKK = nomorKK + f;
			System.out.println(nomorKK);
		}
		
		int id = keluargaDAO.getIdKeluargaTerakhir() + 1;
		
		KeluargaModel keluarga = new KeluargaModel();
		keluarga.setId(id);
		keluarga.setNomorKK(nomorKK);
		keluarga.setAlamat(alamat);
		keluarga.setRt(rt);
		keluarga.setRw(rw);
		keluarga.setIdKelurahan(kelurahan.getId());
		
		keluargaDAO.addKeluarga(keluarga);
		
		model.addAttribute("nkk", nomorKK);
		model.addAttribute("title", "Tambah Keluarga Berhasil");
		return "keluarga-tambah-berhasil";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String editKeluarga(Model model, @PathVariable(value = "nkk") String nomorKK) {
		KeluargaModel keluarga = keluargaDAO.getKeluargaByNKK(nomorKK);
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluarga.getIdKelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getIdKecamatan());
		KotaModel kota = kotaDAO.getKota(kecamatan.getIdKota());
		
		keluarga.setNamaKelurahan(kelurahan.getNamaKelurahan());
		keluarga.setNamaKecamatan(kecamatan.getNamaKecamatan());
		keluarga.setNamaKota(kota.getNamaKota());

		model.addAttribute("nkkLama", nomorKK);
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("title", "Ubah Keluarga");
		return "keluarga-ubah";
	}
	
	@RequestMapping("/keluarga/ubah/submit")
	public String editPendudukSubmit(Model model, @RequestParam(value = "nkkLama", required = true) String nkkLama,
			@RequestParam(value = "alamat", required = true) String alamat,
			@RequestParam(value = "rt", required = true) String rt,
			@RequestParam(value = "rw", required = true) String rw,
			@RequestParam(value = "kelurahan", required = true) String namaKelurahan) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tanggalPembuatan = new Date();
		
		String hariIni = format.format(tanggalPembuatan);
		
		KelurahanModel kelurahan = kelurahanDAO.getKelurahanByNama(namaKelurahan.toUpperCase());
		System.out.println(kelurahan.getKodeKelurahan());
		
		String nomorKK = kelurahan.getKodeKelurahan().substring(0, 6);
		
		// split tanggal pembuatan/hari ini
		String[] tglPembuatan = hariIni.split("-");
		String tanggal = tglPembuatan[2];
		String bulan = tglPembuatan[1];
		String tahun = tglPembuatan[0].substring(2);
		
		nomorKK = nomorKK + tanggal + bulan + tahun;
		System.out.println(nomorKK);
		
		//cek apa ada yang dibuat di kelurahan & tanggal pembuatan yang sama untuk tentuin 4 digit terakhir (urutan)
		String nkkSama = keluargaDAO.getNkkSamaKeluarga(nomorKK);
		if(nkkSama == null) {
			nomorKK = nomorKK + "0001";
			System.out.println(nomorKK);
		} else if (nomorKK.equals(nkkLama)) {
			nomorKK = nkkLama;
		} else {
			int urutan = Integer.parseInt(nkkSama.substring(12)) + 1;
			String f = new DecimalFormat("0000").format(urutan);
			nomorKK = nomorKK + f;
			System.out.println(nomorKK);
		}
		
		
		KeluargaModel keluarga = keluargaDAO.getKeluargaByNKK(nomorKK);
		keluarga.setNomorKK(nomorKK);
		keluarga.setAlamat(alamat);
		keluarga.setRt(rt);
		keluarga.setRw(rw);
		keluarga.setIdKelurahan(kelurahan.getId());

		keluargaDAO.editKeluarga(keluarga);

		model.addAttribute("nkkLama", nkkLama);
		model.addAttribute("title", "Ubah Penduduk Berhasil");
		return "keluarga-ubah-berhasil";
	}
}

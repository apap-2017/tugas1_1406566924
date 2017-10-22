package com.tugas1.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

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
		String tgl = new DecimalFormat("00").format(tanggal);
		String bulan = tanggalKelahiran[1];
		String tahun = tanggalKelahiran[0].substring(2);
		
		nik = nik + tgl + bulan + tahun;
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
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String editPenduduk(Model model, @PathVariable(value="nik") String nik) {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		String nomorKK = keluargaDAO.getKeluargaById(penduduk.getIdKeluarga()).getNomorKK();
		
		model.addAttribute("nomorKK", nomorKK);
		model.addAttribute("nikLama", nik);
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("title", "Ubah Penduduk");
		return "penduduk-ubah";
	}
	
	@RequestMapping("/penduduk/ubah/submit")
	public String editPendudukSubmit(Model model, @RequestParam(value="nama", required=true) String nama, 
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
			@RequestParam(value="nomorKK", required=true) String nomorKK,
			@RequestParam(value="nikLama", required=true) String nikLama) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tglLahir = format.parse(tanggalLahir);
		
		KeluargaModel keluarga = keluargaDAO.getKeluargaByNKK(nomorKK);
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(keluarga.getIdKelurahan());
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(kelurahan.getIdKecamatan());
		
		String nikBaru = kecamatan.getKodeKecamatan().substring(0, 6); 
		System.out.println(nikBaru);
		
		 
		// split tanggal lahir untuk nik 
		String[] tanggalKelahiran = tanggalLahir.split("-");
		int tanggal = Integer.parseInt(tanggalKelahiran[2]);
		// jika jenis kelamin wanita, tanggal ditambah 40
		if(jenisKelamin == 1) {
			tanggal +=40;
		}
		String tgl = new DecimalFormat("00").format(tanggal);
		String bulan = tanggalKelahiran[1];
		String tahun = tanggalKelahiran[0].substring(2);
		
		nikBaru = nikBaru + tgl + bulan + tahun;
		System.out.println(nikBaru);
		
		//cek apa ada yang lahir di kelurahan & tanggal lahir yang sama untuk tentuin 4 digit terakhir (urutan)
		String nikSama = pendudukDAO.getNikSamaPenduduk(nikBaru);
		log.info("ada nik sama nih {}", nikSama);
		
		if(nikSama == null) {
			nikBaru = nikBaru + "0001";
			System.out.println(nikBaru);
		}
		else if(nikSama.equals(nikLama)) {
			nikBaru = nikLama;
		}
		else {
			int urutan = Integer.parseInt(nikSama.substring(12)) + 1;
			String f = new DecimalFormat("0000").format(urutan);
			nikBaru = nikBaru + f;
			System.out.println(nikBaru);
		}
		
		PendudukModel penduduk = pendudukDAO.getPenduduk(nikLama);
		penduduk.setNik(nikBaru);
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
		
		pendudukDAO.editPenduduk(penduduk);
		
		model.addAttribute("nikLama", nikLama);
		model.addAttribute("title", "Ubah Penduduk Berhasil");
		return "penduduk-ubah-berhasil";
	}
	
	@RequestMapping("/penduduk/mati/{nik}")
	public String nonaktifkan(@PathVariable(value="nik") String nik, Model model) {
		PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
		penduduk.setIsWafat(1);
		pendudukDAO.editPenduduk(penduduk);
		
		KeluargaModel keluarga = keluargaDAO.getKeluargaById(penduduk.getIdKeluarga());
		List<PendudukModel> anggotaKeluarga = keluarga.getAnggotaKeluarga();
		log.info("jumlah anggota keluarga {}", anggotaKeluarga);
		int jumlahKeluargaHidup = 0;
		for(PendudukModel p : anggotaKeluarga) {
			if(p.getIsWafat()==0) {
				jumlahKeluargaHidup++;
			}
		}
		if(jumlahKeluargaHidup > 0) {
			model.addAttribute("title", "Lihat Data Penduduk dengan NIK " + nik);
			return "redirect:/penduduk?nik=" + nik;
		}
		else {
			keluarga.setIsTidakBerlaku(1);
			log.info("keluarga {}", keluarga);
			keluargaDAO.editKeluarga(keluarga);
			
			model.addAttribute("nomorKK", keluarga.getNomorKK());
			model.addAttribute("title", "Data Keluarga Berhasil Dinonaktifkan");
			return "keluarga-dinonaktifkan";
		}
	}
	
	@RequestMapping("/penduduk/cari")
	public String searchPenduduk(Model model, @RequestParam(value="kt", required=false) Integer idKota,
			@RequestParam(value="kc", required=false) Integer idKecamatan,
			@RequestParam(value="kl", required=false) Integer idKelurahan) {
		
		KotaModel kota = kotaDAO.getKota(idKota);
		KecamatanModel kecamatan = kecamatanDAO.getKecamatan(idKecamatan);
		KelurahanModel kelurahan = kelurahanDAO.getKelurahan(idKelurahan);
		model.addAttribute("title", "");
		
		List<KelurahanModel> listKelurahan = kelurahanDAO.getAllKelurahanByIdKecamatan(idKecamatan);
		model.addAttribute("listKelurahan", listKelurahan);
		
		List<KecamatanModel> listKecamatan = kecamatanDAO.getAllKecamatanByIdKota(idKota);
		model.addAttribute("listKecamatan", listKecamatan);
		
		List<KotaModel> listKota = kotaDAO.getAllKota();
		model.addAttribute("listKota", listKota);
				
		if(idKota != null && idKecamatan != null && idKelurahan != null) {
			List<PendudukModel> listPenduduk = pendudukDAO.getAllPendudukByIdKelurahan(idKelurahan);
			model.addAttribute("listPenduduk", listPenduduk);
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kelurahan", kelurahan);
			return "penduduk-daftar";
		}
		else {
			return "cari-penduduk";
		}
	}
}

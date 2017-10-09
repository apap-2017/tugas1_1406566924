package com.tugas1.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendudukModel {
	private int id;
	private String nik;
	private String nama;
	private String tempatLahir;
	private Date tanggalLahir;
	private boolean jenisKelamin;
	private boolean isWni;
	private int idKeluarga;
	private String agama;
	private String pekerjaan;
	private String statusPerkawinan;
	private String statusDalamKeluarga;
	private String golonganDarah;
	private boolean isWafat;
	
	@Builder.Default private String alamat = "Alamat Null";
	@Builder.Default private String rt = "00";
	@Builder.Default private String rw = "00";
	@Builder.Default private String namaKelurahan = "Kelurahan Null";
	@Builder.Default private String namaKecamatan = "Kecamatan Null";
	@Builder.Default private String namaKota = "Kota Null";
	@Builder.Default private String kodePos = "00000";
	
	public String getTglLahir() throws ParseException{
		SimpleDateFormat tl = new SimpleDateFormat("dd MMMM yyyy");
		
		return tl.format(tanggalLahir);
//		return tanggalLahir.getYear()+1900 + "-" + (tanggalLahir.getMonth()+1) + "-" + birthdate.getDate();
	}
}

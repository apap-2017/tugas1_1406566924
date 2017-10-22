package com.tugas1.service;

import java.util.Date;

import com.tugas1.model.PendudukModel;

public interface PendudukService {
	PendudukModel getPenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	String getNikSamaPenduduk(int idKelurahan, Date tanggalLahir);
}

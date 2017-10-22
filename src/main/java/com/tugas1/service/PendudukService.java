package com.tugas1.service;

import java.util.List;

import com.tugas1.model.PendudukModel;

public interface PendudukService {
	List<PendudukModel> getAllPendudukByIdKelurahan(int idKelurahan);
	
	PendudukModel getPenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	void editPenduduk(PendudukModel penduduk);
	
	String getNikSamaPenduduk(String nik);
	
	int getIdPendudukTerakhir();
}

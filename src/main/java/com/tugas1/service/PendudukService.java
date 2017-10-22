package com.tugas1.service;

import java.util.List;

import com.tugas1.model.PendudukModel;

public interface PendudukService {
	List<PendudukModel> getAllPendudukByIdKelurahan(int idKelurahan);
	
	PendudukModel getPenduduk(String nik);
	PendudukModel getPendudukTermudaByIdKelurahan(int idKelurahan);
	PendudukModel getPendudukTerturaByIdKelurahan(int idKelurahan);
	
	void addPenduduk(PendudukModel penduduk);
	void editPenduduk(PendudukModel penduduk);
	
	String getNikSamaPenduduk(String nik);
	
	int getIdPendudukTerakhir();
}

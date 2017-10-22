package com.tugas1.service;

import com.tugas1.model.PendudukModel;

public interface PendudukService {
	PendudukModel getPenduduk(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	void editPenduduk(PendudukModel penduduk);
	
	String getNikSamaPenduduk(String nik);
	
	int getIdPendudukTerakhir();
}

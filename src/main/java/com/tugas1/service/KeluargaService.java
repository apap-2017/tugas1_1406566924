package com.tugas1.service;

import java.util.List;

import com.tugas1.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel getKeluargaById(int idKeluarga);
	KeluargaModel getKeluargaByNKK(String nomorKK);
	
	List<KeluargaModel> getAllKeluarga();
	
	String getNkkSamaKeluarga(String nomorKK);
	
	int getIdKeluargaTerakhir();
}

package com.tugas1.service;

import java.util.List;

import com.tugas1.model.KotaModel;

public interface KotaService {
	KotaModel getKota(int idKota);
	
	List<KotaModel> getAllKota();
}

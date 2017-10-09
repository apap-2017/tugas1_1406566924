package com.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomorKK;
	private String alamat;
	private String rt;
	private String rw;
	private int idKelurahan;
	private boolean isTidakBerlaku;
}

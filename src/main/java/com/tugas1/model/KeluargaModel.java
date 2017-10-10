package com.tugas1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeluargaModel {
	private int id;
	private String nomorKK;
	private String alamat;
	private String rt;
	private String rw;
	private int idKelurahan;
	private boolean isTidakBerlaku;
	private List<PendudukModel> anggotaKeluarga;
	
	@Builder.Default private String namaKelurahan = "Kelurahan Null";
	@Builder.Default private String namaKecamatan = "Kecamatan Null";
	@Builder.Default private String namaKota = "Kota Null";
	@Builder.Default private String kodePos = "00000";
	
}

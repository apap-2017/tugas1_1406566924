package com.tugas1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("SELECT * FROM penduduk WHERE nik = #{nik}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="tempatLahir", column="tempat_lahir"),
			@Result(property="tanggalLahir", column="tanggal_lahir"),
			@Result(property="jenisKelamin", column="jenis_kelamin"),
			@Result(property="isWni", column="is_wni"),
			@Result(property="idKeluarga", column="id_keluarga"),
			@Result(property="agama", column="agama"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="statusPerkawinan", column="status_perkawinan"),
			@Result(property="statusDalamKeluarga", column="status_dalam_keluarga"),
			@Result(property="golonganDarah", column="golongan_darah"),
			@Result(property="isWafat", column="is_wafat")
	})
	PendudukModel selectPenduduk(@Param(value="nik") String nik);
}

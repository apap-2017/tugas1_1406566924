package com.tugas1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tugas1.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("SELECT * FROM keluarga WHERE id = #{idKeluarga}")
	@Results(value = {
			@Result(property="nomorKK", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
			@Result(property="id", column="id")
	})
	KeluargaModel selectKeluarga(@Param(value="idKeluarga") int idKeluarga);
}

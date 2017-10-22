package com.tugas1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tugas1.model.KeluargaModel;
import com.tugas1.model.PendudukModel;

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
			@Result(property="id", column="id"),
			@Result(property="anggotaKeluarga", column="id", javaType = List.class, many = @Many(select = "selectAnggotaKeluarga"))
	})
	KeluargaModel selectKeluargaById(@Param(value="idKeluarga") int idKeluarga);
	
	@Select("SELECT * FROM keluarga WHERE nomor_kk = #{nomorKK}")
	@Results(value = {
			@Result(property="nomorKK", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
			@Result(property="id", column="id"),
			@Result(property="anggotaKeluarga", column="id", javaType = List.class, many = @Many(select = "selectAnggotaKeluarga"))
	})
	KeluargaModel selectKeluargaByNKK(@Param(value="nomorKK") String nomorKK);
	
	@Select("SELECT * FROM penduduk WHERE id_keluarga = #{idKeluarga}")
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
	List<PendudukModel> selectAnggotaKeluarga(@Param("idKeluarga") int idKeluarga);
	
	@Select("SELECT id, nomor_kk FROM keluarga")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomorKK", column="nomor_kk")
	})
	List<KeluargaModel> selectAllKeluarga();
	
	@Select("SELECT nomor_kk FROM keluarga WHERE nomor_kk LIKE '#{nomorKK}%' ORDER BY id DESC LIMIT 1")
	String selectNkkSamaKeluarga(@Param(value="nomorKK") String nomorKK);
	
	@Select("SELECT id FROM keluarga ORDER BY id DESC LIMIT 1")
	int selectIdKeluargaTerakhir();
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomorKK}, "
			+ "#{alamat}, #{rt}, #{rw}, #{idKelurahan})")
	void insertKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET nomor_kk=#{nomorKK}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{idKelurahan}, "
			+ "is_tidak_berlaku=#{isTidakBerlaku} WHERE id=#{id}")
	void updateKeluarga(KeluargaModel keluarga);
}
	
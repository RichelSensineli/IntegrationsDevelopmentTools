package br.com.fiap.consumer02.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Summarization implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String MES_REFERENCIA;

	@JsonProperty
	private String MES_COMPETENCIA;

	@JsonProperty
	private String UF;

	@JsonProperty
	private String CODIGO_MUNICIPIO_SIAFI;

	@JsonProperty
	private String NOME_MUNICIPIO;

	@JsonProperty
	private String NIS_FAVORECIDO;

	@JsonProperty
	private String NOME_FAVORECIDO;

	@JsonProperty
	private String VALOR_PARCELA;

	public Summarization(String MES_REFERENCIA, String MES_COMPETENCIA, String UF, String CODIGO_MUNICIPIO_SIAFI, String NOME_MUNICIPIO, String NIS_FAVORECIDO, String NOME_FAVORECIDO, String VALOR_PARCELA) {
		this.MES_REFERENCIA = MES_REFERENCIA;
		this.MES_COMPETENCIA = MES_COMPETENCIA;
		this.UF = UF;
		this.CODIGO_MUNICIPIO_SIAFI = CODIGO_MUNICIPIO_SIAFI;
		this.NOME_MUNICIPIO = NOME_MUNICIPIO;
		this.NIS_FAVORECIDO = NIS_FAVORECIDO;
		this.NOME_FAVORECIDO = NOME_FAVORECIDO;
		this.VALOR_PARCELA = VALOR_PARCELA;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMES_REFERENCIA() {
		return MES_REFERENCIA;
	}

	public void setMES_REFERENCIA(String MES_REFERENCIA) {
		this.MES_REFERENCIA = MES_REFERENCIA;
	}

	public String getMES_COMPETENCIA() {
		return MES_COMPETENCIA;
	}

	public void setMES_COMPETENCIA(String MES_COMPETENCIA) {
		this.MES_COMPETENCIA = MES_COMPETENCIA;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String UF) {
		this.UF = UF;
	}

	public String getCODIGO_MUNICIPIO_SIAFI() {
		return CODIGO_MUNICIPIO_SIAFI;
	}

	public void setCODIGO_MUNICIPIO_SIAFI(String CODIGO_MUNICIPIO_SIAFI) {
		this.CODIGO_MUNICIPIO_SIAFI = CODIGO_MUNICIPIO_SIAFI;
	}

	public String getNOME_MUNICIPIO() {
		return NOME_MUNICIPIO;
	}

	public void setNOME_MUNICIPIO(String NOME_MUNICIPIO) {
		this.NOME_MUNICIPIO = NOME_MUNICIPIO;
	}

	public String getNIS_FAVORECIDO() {
		return NIS_FAVORECIDO;
	}

	public void setNIS_FAVORECIDO(String NIS_FAVORECIDO) {
		this.NIS_FAVORECIDO = NIS_FAVORECIDO;
	}

	public String getNOME_FAVORECIDO() {
		return NOME_FAVORECIDO;
	}

	public void setNOME_FAVORECIDO(String NOME_FAVORECIDO) {
		this.NOME_FAVORECIDO = NOME_FAVORECIDO;
	}

	public String getVALOR_PARCELA() {
		return VALOR_PARCELA;
	}

	public void setVALOR_PARCELA(String VALOR_PARCELA) {
		this.VALOR_PARCELA = VALOR_PARCELA;
	}

	@Override
	public String toString() {
		return "Summarization{" +
				"MES_REFERENCIA='" + MES_REFERENCIA + '\'' +
				", MES_COMPETENCIA='" + MES_COMPETENCIA + '\'' +
				", UF='" + UF + '\'' +
				", CODIGO_MUNICIPIO_SIAFI='" + CODIGO_MUNICIPIO_SIAFI + '\'' +
				", NOME_MUNICIPIO='" + NOME_MUNICIPIO + '\'' +
				", NIS_FAVORECIDO='" + NIS_FAVORECIDO + '\'' +
				", NOME_FAVORECIDO='" + NOME_FAVORECIDO + '\'' +
				", VALOR_PARCELA='" + VALOR_PARCELA + '\'' +
				'}';
	}
}

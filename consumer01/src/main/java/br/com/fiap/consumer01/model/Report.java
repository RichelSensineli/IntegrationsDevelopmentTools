package br.com.fiap.consumer01.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Report {

    private String UF;
    private BigDecimal totalParcelas;
    private int quantidadeBeneficiarios;

    public Report(String UF) {
        this.UF = UF;
        this.totalParcelas = BigDecimal.ZERO;
        this.quantidadeBeneficiarios = 0;
    }

    public Report(String UF, BigDecimal totalParcelas, int quantidadeBeneficiarios) {
        this.UF = UF;
        this.totalParcelas = totalParcelas;
        this.quantidadeBeneficiarios = quantidadeBeneficiarios;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public BigDecimal getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(BigDecimal totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    public int getQuantidadeBeneficiarios() {
        return quantidadeBeneficiarios;
    }

    public void setQuantidadeBeneficiarios(int quantidadeBeneficiarios) {
        this.quantidadeBeneficiarios = quantidadeBeneficiarios;
    }

    @Override
    public String toString() {
        return "UF:'" + UF + '\'' + ", Total Parcelas: " + totalParcelas + ", Quantidade Beneficiarios: " + quantidadeBeneficiarios;
    }
}

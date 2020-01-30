package br.com.fiap.consumer02.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.consumer02.model.Summarization;
import br.com.fiap.consumer02.service.SummarizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("summarizations")
public class SummarizationController {

	private List<Summarization> summarizations = new ArrayList<Summarization>();

	@Autowired
	private SummarizationsService summarizationsService;

	@GetMapping("/comparator")
	public void getComparations() {

		BigDecimal higher = BigDecimal.ZERO;

		summarizations = summarizationsService.getSummarizationList();

		System.out.println("******************************************************");
		System.out.println("*       RELATORIO COM MAIOR BENEFICIARIO             *");
		System.out.println("******************************************************");

		for (Summarization summarization: summarizations) {
			BigDecimal value = new BigDecimal(summarization.getVALOR_PARCELA().replace(",", "."));
			if (value.compareTo(higher) == 1) {
				System.out.println("NIS: " + summarization.getNIS_FAVORECIDO()
								+ " | NOME: " + summarization.getNOME_FAVORECIDO()
								+ " | VALOR: " + summarization.getVALOR_PARCELA().replace(",", ".")
								+ " | MUNICIPIO: " + summarization.getCODIGO_MUNICIPIO_SIAFI()
								+ " | UF: " + summarization.getUF());
				higher = value;
			}
		}

		System.out.println("******************************************************");
	}
}

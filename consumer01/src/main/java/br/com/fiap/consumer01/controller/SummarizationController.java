package br.com.fiap.consumer01.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.fiap.consumer01.model.Report;
import br.com.fiap.consumer01.model.Summarization;
import br.com.fiap.consumer01.service.SummarizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("summarizations")
public class SummarizationController {

	private List<Summarization> summarizations = new ArrayList<Summarization>();
	private Map< String , Report> report;

	@Autowired
	private SummarizationsService summarizationsService;
	
	@GetMapping
    public List<Summarization> getAll(){

		summarizations = summarizationsService.getSummarizationList();

		for (Summarization summarization: summarizations) {
			if(!summarization.getVALOR_PARCELA().contains("VALOR")) {
				System.out.println(summarization.toString());
				contabilizaRegistroPorEstado(summarization);
			}
		}

		System.out.println(report.toString());

        return summarizations;
    }

	private void contabilizaRegistroPorEstado(Summarization summarization) {
		System.out.println(summarization.toString());

//		summarizations.forEach(summa -> {
//			summarizeIt(summa);
//		});
	}

	private void summarizeIt(Summarization summarization) {
		if(report.containsKey(summarization.getUF())){
			BigDecimal totalParcela = report.get(summarization.getUF()).getTotalParcelas();
			int totalBeneficiarios = report.get(summarization.getUF()).getQuantidadeBeneficiarios();

			report.put(summarization.getUF(), new Report(
												summarization.getUF(),
												totalParcela.add(new BigDecimal(summarization.getVALOR_PARCELA())),
												totalBeneficiarios + 1));
		} else {
			report.put(summarization.getUF(), new Report(
					                          	summarization.getUF(),
												new BigDecimal(summarization.getVALOR_PARCELA()),
							1));
		}
	}
}

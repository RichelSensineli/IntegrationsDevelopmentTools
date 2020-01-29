package br.com.fiap.consumer01.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
	private Map< String , Report> report = new HashMap< String, Report>();

	@Autowired
	private SummarizationsService summarizationsService;
	
	@GetMapping("/report")
    public void getReport(){

		initializeReport();

		summarizations = summarizationsService.getSummarizationList();

		for (Summarization summarization: summarizations) {
			if(!summarization.getVALOR_PARCELA().contains("VALOR")) {
				summarize(summarization);
			}
		}

		printReport();
    }

	private void printReport() {
		report.entrySet().forEach(stringReportEntry -> {
			System.out.println(stringReportEntry.getValue());
		});
	}

	private void initializeReport() {

		report.put("AC", new Report("AC"));
		report.put("AL", new Report("AL"));
		report.put("AP", new Report("AP"));
		report.put("AM", new Report("AM"));
		report.put("BA", new Report("BA"));
		report.put("CE", new Report("CE"));
		report.put("DF", new Report("DF"));
		report.put("ES", new Report("ES"));
		report.put("GO", new Report("GO"));
		report.put("MA", new Report("MA"));
		report.put("MT", new Report("MT"));
		report.put("MS", new Report("MS"));
		report.put("MG", new Report("MG"));
		report.put("PA", new Report("PA"));
		report.put("PB", new Report("PB"));
		report.put("PR", new Report("PR"));
		report.put("PE", new Report("PE"));
		report.put("PI", new Report("PI"));
		report.put("RJ", new Report("RJ"));
		report.put("SP", new Report("SP"));
		report.put("RN", new Report("RN"));
		report.put("RS", new Report("RS"));
		report.put("RO", new Report("RO"));
		report.put("RR", new Report("RR"));
		report.put("SC", new Report("SC"));
		report.put("SP", new Report("SP"));
		report.put("SE", new Report("SE"));
		report.put("TO", new Report("TO"));

	}

	private void summarize(Summarization summarization) {
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

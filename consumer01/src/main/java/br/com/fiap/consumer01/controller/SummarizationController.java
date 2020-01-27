package br.com.fiap.consumer01.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	private BigDecimal total;

	@Autowired
	private SummarizationsService summarizationsService;
	
	@GetMapping
    public List<Summarization> getAll(){

		summarizations = summarizationsService.getSummarizationList();

		for (Summarization summarization: summarizations) {

			if(summarization.getVALOR_PARCELA().contains("VALOR")){
				System.out.println("IGNORADO >>>>> " + summarization.toString());
			} else {
				contabilizaRegistroPorEstado(summarization);
			}

		}

		System.out.println(total);

        return summarizations;
    }

	private void contabilizaRegistroPorEstado(Summarization summarization) {
		BigDecimal val = null;

		switch (summarization.getUF()){
			case "BA":
				val = new BigDecimal(summarization.getVALOR_PARCELA());
				total = total.add(val);
		}
	}
}

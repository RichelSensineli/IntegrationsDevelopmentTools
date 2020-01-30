package br.com.fiap.consumer03.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.consumer03.model.Summarization;
import br.com.fiap.consumer03.service.SummarizationsService;
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

	@GetMapping("/stats")
	public void getStats(){

		summarizations = summarizationsService.getSummarizationList();

		System.out.println("******************************************************");
		System.out.println("*       RELATORIO COM TOTAIS LIDOS DO TOPICO         *");
		System.out.println("******************************************************");
		System.out.println(summarizationsService.getReadMessagesCount());
		System.out.println("******************************************************");
	}
}

package br.com.fiap.consumer01.service;

import br.com.fiap.consumer01.model.Summarization;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.kafka.common.metrics.stats.Sum;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummarizationsService {
	
	private Summarization summarization;
	private static List<Summarization> summarizationList = new ArrayList<Summarization>();

	@KafkaListener(topics = "${csv.topic}", groupId = "spring.kafka.consumer.group-id")
	public void listen(String message) {
        try {
            System.out.println(">>>>   " + message);
            JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();

            Gson gson=new Gson();
            Summarization summarization = gson.fromJson(jsonObject,Summarization.class);

            summarizationList.add(summarization);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Summarization> getSummarizationList() {
        return summarizationList.stream()
                .filter(x -> !x.getVALOR_PARCELA().contains("VALOR"))
                .sorted(Comparator.comparing(Summarization::getUF))
                .collect(Collectors.toList());
    }

    public void clearSummarizationList() {
        summarizationList.clear();
    }
}

package br.com.fiap.consumer03.service;

import br.com.fiap.consumer03.model.Summarization;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SummarizationsService {
	
	private Summarization summarization;
	private static List<Summarization> summarizationList = new ArrayList<Summarization>();
	private static Integer readMessagesCount = 0;

	@KafkaListener(topics = "${csv.topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(String message) {

        readMessagesCount++;

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
	    return summarizationList;
    }

    public void clearSummarizationList() {
        summarizationList.clear();
    }

    public Integer getReadMessagesCount() {
	    return readMessagesCount;
    }
}

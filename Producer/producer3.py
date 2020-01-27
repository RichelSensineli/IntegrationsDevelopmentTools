import csv
import json
import os
import sys
from datetime import datetime
from flask import Flask, request
from kafka import SimpleProducer

app = Flask(__name__)

fieldnames = ("MES_REFERENCIA", "MES_COMPETENCIA", "UF", "CODIGO_MUNICIPIO_SIAFI", "NOME_MUNICIPIO", "NIS_FAVORECIDO", "NOME_FAVORECIDO", "VALOR_PARCELA")
qtdTotMsg = 0

def enviar_mensagem(producer2, filehandler, qtdLinhas):
    
    reader = csv.DictReader(f, fieldnames, delimiter=';', quoting=csv.QUOTE_ALL)
   
    global qtdTotMsg

    for row in reader:
        # Parse CSV para JSON  
        out = json.dumps(row)
        # Converter em bytes
        msg = out.encode()
        # Enviar mensagem para o topico Kafka
        producer2.send_messages('csv_topic', msg)
        # Imprimir a mensagem no console
        # print(msg)
        #if qtdMsg % 10000 == 0:
        #    ts3 = datetime.now()
        #    print(str(qtdMsg) + ' - ' + ts3.strftime("%m/%d/%Y, %H:%M:%S"), file=sys.stderr)
        qtdTotMsg += 1
        if qtdTotMsg == qtdLinhas:
            break

@app.route("/iniciar")
def iniciar():
    global qtdTotMsg
    qtdTotMsg = 0

    arquivo = request.args.get('arquivo')
    qtdLinhas = request.args.get('linhas', default=0, type=int)

    iniTotal = datetime.now()
    kafka = KafkaClient('kafka:9092')
    
    producer = SimpleProducer(kafka, async=True)

    arq = '/csv/' + arquivo
    f = open(arq, 'r', encoding="utf8",  errors='ignore')

    print('Abriu arquivo csv. Enviando mensagens...', file=sys.stderr)

    enviar_mensagem(producer, f, qtdLinhas)
    
    print('Fim do envio das mensagens.', file=sys.stderr)

    fimTotal = datetime.now()
    tempoTotal = fimTotal - iniTotal 
    strMensagem = 'Foi! Total de mensagens enviadas para a fila: ' + str(qtdTotMsg) + '. Início: ' + iniTotal.strftime("%m/%d/%Y, %H:%M:%S") + '. Fim: ' + fimTotal.strftime("%m/%d/%Y, %H:%M:%S") + '. Tempo total: ' + str(tempoTotal)
    print(strMensagem, file=sys.stderr)

    return strMensagem

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)     






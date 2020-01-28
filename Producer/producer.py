import csv
import json
import os
from datetime import datetime
from flask import Flask, request, render_template, url_for, redirect
from kafka import KafkaProducer, SimpleProducer, KafkaClient

app = Flask(__name__)

kafka = KafkaClient('kafka:9092')
producer2 = SimpleProducer(kafka)

producer2 = SimpleProducer(kafka, async=True, batch_send_every_n=100000)

#producer = KafkaProducer(bootstrap_servers=['kafka:9092'])

fieldnames = ("MES_REFERENCIA", "MES_COMPETENCIA", "UF", "CODIGO_MUNICIPIO_SIAFI", "NOME_MUNICIPIO", "NIS_FAVORECIDO", "NOME_FAVORECIDO", "VALOR_PARCELA")

@app.route("/")
def fileFrontPage():
    return render_template('formulario.html')

@app.route("/handleUpload", methods=['POST'])
def handleFileUpload():
    qtdMsg = 0
    if 'arquivo' in request.files:
        arquivo = request.files['arquivo']

        if arquivo.filename != '':            
            arquivo.save(os.path.join('./', arquivo.filename))

            f = open(arquivo.filename, 'r', encoding="utf8",  errors='ignore')
            #reader = csv.DictReader(f, fieldnames)
            reader = csv.DictReader(f, fieldnames, delimiter=';', quoting=csv.QUOTE_ALL)

            # Ignora o header (encoding nao suportado)
            #next(reader, None)

            ts1 = datetime.now()

            for row in reader:
                qtdMsg += 1
                # Parse CSV para JSON  
                out = json.dumps(row)
                # Converter em bytes
                msg = out.encode()
                # Enviar mensagem para o topico Kafka
                #future = producer.send('csv_topic', msg)    
                #future.get(timeout=10)
                producer2.send_messages('csv_topic', msg)
                # Imprimir a mensagem no console
                # print(msg)
                if qtdMsg % 10000 == 0:
                    ts3 = datetime.now()
                    print(str(qtdMsg) + ' - ' + ts3.strftime("%m/%d/%Y, %H:%M:%S"))

    #return redirect(url_for('fileFrontPage'))
    ts2 = datetime.now()
    return 'Foi! Total de mensagens: ' + str(qtdMsg) + '. Início: ' + ts1.strftime("%m/%d/%Y, %H:%M:%S") + '. Fim: ' + ts2.strftime("%m/%d/%Y, %H:%M:%S")

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)     






import csv
import json
import os
from flask import Flask, request, render_template, url_for, redirect
from kafka import KafkaProducer

app = Flask(__name__)

producer = KafkaProducer(bootstrap_servers=['kafka:9092'])

fieldnames = ("MES_REFERENCIA", "MES_COMPETENCIA", "UF", "CODIGO_MUNICIPIO_SIAFI", "NOME_MUNICIPIO", "NIS_FAVORECIDO", "NOME_FAVORECIDO", "VALOR_PARCELA")

@app.route("/")
def fileFrontPage():
    return render_template('formulario.html')

@app.route("/handleUpload", methods=['POST'])
def handleFileUpload():
    if 'arquivo' in request.files:
        arquivo = request.files['arquivo']

        if arquivo.filename != '':            
            arquivo.save(os.path.join('./', arquivo.filename))

            f = open(arquivo.filename, 'r', encoding="utf8",  errors='ignore')
            #reader = csv.DictReader(f, fieldnames)
            reader = csv.DictReader(f, fieldnames, delimiter=';', quoting=csv.QUOTE_ALL)

            # Ignora o header (encoding nao suportado)
            next(reader, None)

            for row in reader:
                # Parse CSV para JSON  
                out = json.dumps(row)
                # Converter em bytes
                msg = out.encode()
                # Enviar mensagem para o topico Kafka
                future = producer.send('csv_topic', msg)    
                future.get(timeout=10)
                # Imprimir a mensagem no console
                # print(msg)

    return redirect(url_for('fileFrontPage'))

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)     






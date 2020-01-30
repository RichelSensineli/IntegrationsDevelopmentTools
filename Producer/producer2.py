import csv
import json
import os
import sys
from datetime import datetime
from flask import Flask, request
from kafka import KafkaProducer, SimpleProducer, KafkaClient

app = Flask(__name__)

fieldnames = ("MES_REFERENCIA", "MES_COMPETENCIA", "UF", "CODIGO_MUNICIPIO_SIAFI", "NOME_MUNICIPIO", "NIS_FAVORECIDO", "NOME_FAVORECIDO", "VALOR_PARCELA")
qtdTotMsg = 0

def split(filehandler, row_limit, delimiter=';',
          output_name_template='output_%s.csv', output_path='.', keep_headers=False):
    reader = csv.reader(filehandler, delimiter=delimiter)
    current_piece = 1
    current_out_path = os.path.join(
        output_path,
        output_name_template % current_piece
    )
    current_out_writer = csv.writer(open(current_out_path, 'w'), delimiter=delimiter)
    current_limit = row_limit
    if keep_headers:
        headers = reader.next()
        current_out_writer.writerow(headers)
    for i, row in enumerate(reader):
        if i + 1 > current_limit:
            current_piece += 1
            current_limit = row_limit * current_piece
            current_out_path = os.path.join(
                output_path,
                output_name_template % current_piece
            )
            current_out_writer = csv.writer(open(current_out_path, 'w'), delimiter=delimiter)
            if keep_headers:
                current_out_writer.writerow(headers)
        current_out_writer.writerow(row)

def enviar_mensagem(current_piece, producer2, output_name_template='output_%s.csv', output_path='.'):
    current_out_path = os.path.join(
        output_path,
        output_name_template % current_piece
    )
    f = open(current_out_path, 'r', encoding="utf8",  errors='ignore')

    #f = open('./output_1.csv', 'r', encoding="utf8",  errors='ignore')

    reader = csv.DictReader(f, fieldnames, delimiter=';', quoting=csv.QUOTE_ALL)
    #reader = csv.DictReader(f, fieldnames)

    iniPart = datetime.now()

    qtdMsg = 0
    global qtdTotMsg

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
        #if qtdMsg % 10000 == 0:
        #    ts3 = datetime.now()
        #    print(str(qtdMsg) + ' - ' + ts3.strftime("%m/%d/%Y, %H:%M:%S"), file=sys.stderr)

    #return redirect(url_for('fileFrontPage'))
    fimPart = datetime.now()
    print(
        'Total de mensagens da particao: ' + str(qtdMsg) + '. Início: ' + iniPart.strftime("%m/%d/%Y, %H:%M:%S") + '. Fim: ' + fimPart.strftime("%m/%d/%Y, %H:%M:%S")
        , file=sys.stderr
    )
    qtdTotMsg += qtdMsg


@app.route("/iniciar")
def iniciar():
    global qtdTotMsg
    qtdTotMsg = 0

    arquivo = request.args.get('arquivo')
    qtdPorParticao = int(request.args.get('qtd_por_particao'))
    qtdParticoes = int(request.args.get('qtd_particoes'))

    iniTotal = datetime.now()
    kafka = KafkaClient('kafka:9092')
    #producer2 = SimpleProducer(kafka)

    producer2 = SimpleProducer(kafka, async=True)

    #producer = KafkaProducer(bootstrap_servers=['kafka:9092'])

    arq = '/csv/' + arquivo
    f = open(arq, 'r', encoding="utf8",  errors='ignore')

    print('Abriu arquivo csv. Fazendo o split...', file=sys.stderr)

    split(f, qtdPorParticao)

    print('Fim do split. Enviando mensagens...', file=sys.stderr)

    i = 1
    while i <= qtdParticoes:
        enviar_mensagem(i, producer2)
        print('Fim do envio das mensagens da particao ' + str(i) + '.', file=sys.stderr)
        i += 1

    fimTotal = datetime.now()
    tempoTotal = fimTotal - iniTotal
    strMensagem = 'Foi! Total de mensagens enviadas para a fila: ' + str(qtdTotMsg) + '. Início: ' + iniTotal.strftime("%m/%d/%Y, %H:%M:%S") + '. Fim: ' + fimTotal.strftime("%m/%d/%Y, %H:%M:%S") + '. Tempo total: ' + str(tempoTotal)
    print(strMensagem, file=sys.stderr)

    return strMensagem


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

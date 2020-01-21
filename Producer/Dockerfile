FROM python:3.7-alpine
WORKDIR /code
COPY . .
RUN pip3 install kafka-python Flask
CMD ["python", "producer.py"]
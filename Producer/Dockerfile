FROM python:3.6
WORKDIR /code
COPY . .
RUN pip3 install kafka-python Flask
CMD ["python", "producer2.py"]
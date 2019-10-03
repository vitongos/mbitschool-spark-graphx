FROM spark-base:2.4.4

COPY start-master.sh /

ENV SPARK_MASTER_PORT 7077
ENV SPARK_MASTER_WEBUI_PORT 8080
ENV SPARK_MASTER_LOG /spark/logs
ENV PYSPARK_PYTHON python3
ENV PYSPARK_DRIVER_PYTHON jupyter
ENV PYSPARK_DRIVER_PYTHON_OPTS "notebook --ip=0.0.0.0 --port=8081 --allow-root"

EXPOSE 8080 7077 6066

CMD ["/bin/bash", "/start-master.sh"]

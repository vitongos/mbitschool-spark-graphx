# Demos y fuentes del curso de Spark GrahpX

## Instalación

A continuación se especifica el proceso de instalación del sistema en la máquina virtual de Centos.
Ejecutar los scripts bajo usuario root.

*Importante: ejecutar la máquina virtual con un mínimo 4 GB de RAM, 8 GB recomendados*

### Prerrequisitos

* Docker
* Docker compose

### Instalar el repositorio clonándolo de Github

Ejecutar el siguiente script bajo usuario root:

```sh
yum -y install git
cd
git clone https://github.com/vitongos/mbitschool-spark-graphx graphx-src
```

### Construir las imágenes Docker

Ejecutar el siguiente script:

```sh
cd graphx-src
chmod +x build-images.sh
./build-images.sh
```

Se crearán las siguientes imágenes:

* spark-base:2.4.4: Basada en java:alpine-jdk-8 con scala, python3 y spark 2.4.4
* spark-master:2.4.4: Basada en spark-base, para el master.
* spark-worker:2.4.4: Basada en spark-base, para los workers.
* spark-submit:2.4.4: Basada en spark-base, para crear aplicaciones en el cluster (ejecuta y muere).

## Levantar el cluster

El Docker compose creará varios contenedores:

* spark-master|10.5.0.2
* spark-worker-1|10.5.0.3
* spark-worker-2|10.5.0.4
* spark-worker-3|10.5.0.5

Ejecutar el siguiente script:

```sh
docker-compose up -d
```

Para ver el estado del cluster, abrir el [master UI](http://localhost:5080) 

### Conectarse al master por consola y ejecutar los scripts de demo

Ejecutar el siguiente script:

```sh
docker exec -it spark-master bash
```

Desde el contenedor ejecutar spark-shell:

```sh
$> spark/bin/spark-shell
Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
Setting default log level to "WARN".
To adjust logging level use sc.setLogLevel(newLevel). For SparkR, use setLogLevel(newLevel).
Spark context Web UI available at http://spark-master:4040
Spark context available as 'sc' (master = local[*], app id = local-1575749832043).
Spark session available as 'spark'.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.4.4
      /_/
         
Using Scala version 2.11.12 (OpenJDK 64-Bit Server VM, Java 1.8.0_111-internal)
Type in expressions to have them evaluated.
Type :help for more information.

scala> :load /opt/spark-apps/01.graph.sample.scala
```

#!/bin/bash

while getopts a: option
do
	case "${option}"
	in
	a) APP=${OPTARG};;
	esac
done

docker run --rm --network src_spark-network -p "4040:4040" --env APP="$APP" spark-submit:2.4.4
## -v "./samples/source":/opt/spark-apps -v "./samples/data":/opt/spark-data

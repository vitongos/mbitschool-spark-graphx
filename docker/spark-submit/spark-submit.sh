 #!/bin/bash
 
/spark/bin/spark-submit \
 --master spark://spark-master:7077 \
 ${APP}

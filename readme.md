## build image

docker build -t leqing92/practice-test .  
or 
docker build -t leqing92/practice-test:0.0.1 . 

## run image

docker run -d -p 8080:3000 leqing92/practice-test:0.0.1 .

localhost:3000/

## railway redis

redis-cli -h roundhouse.proxy.rlwy.net -p 20888 --user default --pass UmEXNiofOUYcDkwectLCbpXtCUAcEUjr
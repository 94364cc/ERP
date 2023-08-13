#!/bin/bash
# 此脚本可用来本地打包并构建镜像
image=erp
namespace=devshop
#月度版本分支
version=1.0.0
#harbor=10.10.102.113:8443

sudo docker buildx inspect --bootstrap
sudo docker buildx build -t $image:$version --platform linux/amd64 -o type=docker .

# 按需是否登陆harbor，是否推送镜像
#harbor_password=Hc@Cloud12345
#sudo docker login $harbor --username admin --password $harbor_password
#sudo docker push $harbor/$namespace/$image:$version


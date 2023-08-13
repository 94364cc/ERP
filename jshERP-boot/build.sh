#!/bin/bash
# 此脚本可用来本地打包并构建镜像
image=erp
version=1.0.0

sudo docker buildx inspect --bootstrap
sudo docker buildx build -t $image:$version --platform linux/amd64 -o type=docker .


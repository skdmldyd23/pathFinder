#!/bin/bash

maintaner="anjrpdy"
app="pathfinder"

container=`docker ps -a -q --no-trunc --filter name=^/$app$`
image=`docker images -q $maintaner/$app`
script_path=$(dirname $(realpath $0))

if [ -z "$container"];
then
	echo "해당 컨테이너가 존재하지 않아 실행------------------"
elif [ -n "$container" ] || [ "$container" -eq "1" ] ;
then 
	echo "해당 컨테이너가 존재하여 정지시키고 삭제------------"
	docker stop "$container"
	docker rm "$container"
fi

# if [ -n "$image" ] || [ "$image" -eq 1 ]; 
# then docker rmi "$image"
# fi

# docker build -t "$maintaner"/"$app" "$script_path"
# docker run --restart=always -p 8181:8181 -d --name "$app" "$maintaner"/"$app"

# push용 New command
docker push "$maintaner"/"$app"
docker run --restart="always" -p 8181:8181 -d --name "$app" "$maintaner"/"$app":latest
# docker rmi `docker images | awk '$1 ~ /9m1i9n1/ {print $3}'`
#!/bin/bash

cd $(realpath $(dirname $0))

NAME=dbclient

. /$1

docker run --rm --name=$NAME -v /tmp/:/sink -v $PG_PASS/.pgpass:/root/.pgpass --net=host postgres:$PG_VER \
    pg_dump -f /tmp/$DB_NAME.dump -F custom -O -b -d $DB_NAME -h $DB_HOST -p $DB_PORT -U $DB_USER

cp -f /tmp/$DB_NAME.dump ./init-postgres/$DB_NAME.dump;

printf 'FROM postgres:%s \n\nCOPY ./init-postgres /docker-entrypoint-initdb.d/' $PG_VER > Dockerfile;

docker image build . -t ghcr.io/$CR_NAME/dump_db:latest
echo $CR_PAT | docker login ghcr.io -u USERNAME --password-stdin
docker push ghcr.io/$CR_NAME/dump_db:latest

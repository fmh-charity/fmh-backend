#!/bin/bash

cd $(realpath $(dirname $0))

NAME=dbclient

. /$1

docker run --rm --name=$NAME -v /tmp/:/sink -v $PG_PASS/.pgpass:/root/.pgpass -e POSTGRES_PASSWORD=password --net=host postgres:$PG_VER \
chmod 0600 /root/.pgpass; pg_dump -f /tmp/$DB_NAME.dump -F custom -O -b -d $DB_NAME -h $DB_HOST -p $DB_PORT -U $DB_USER -w;

cp -f /tmp/$DB_NAME.dump ./init-postgres/$DB_NAME.dump;

docker image build . -t ghcr.io/$CR_NAME/dump_db:latest

docker push ghcr.io/$CR_NAME/dump_db:latest

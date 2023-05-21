#!/bin/bash
echo "Restoring $POSTGRES_DB from $dump_file"
pg_restore -Fc -O -U $POSTGRES_USER -d $POSTGRES_DB --verbose < /docker-entrypoint-initdb.d/postgres.dump || exit 1

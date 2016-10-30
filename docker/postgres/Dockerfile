# PostgreSQL 9 on centos 7
#

FROM centos:centos7

RUN groupadd --system postgres && \
    useradd --home-dir /home/postgres --create-home --system --gid postgres postgres

RUN yum -y update && \
    yum -y install postgresql-server && \
    yum clean all

ENV PGDATA /home/postgres/pgdata

RUN mkdir $PGDATA && \
    chown postgres:postgres $PGDATA

USER postgres

RUN /usr/bin/pg_ctl initdb --pgdata=$PGDATA -o '--encoding=UTF-8'

RUN echo "host	bachproef	frederic	0.0.0.0/0	password" >> $PGDATA/pg_hba.conf

RUN /usr/bin/pg_ctl -w start && \
    psql template1 -c "CREATE USER frederic WITH PASSWORD 'frederic';" && \
    psql template1 -c "CREATE DATABASE bachproef ENCODING 'UTF-8'" && \
    psql template1 -c "GRANT ALL PRIVILEGES ON DATABASE bachproef TO frederic" && \
    /usr/bin/pg_ctl stop

EXPOSE 5432

ENTRYPOINT ["/usr/bin/postgres", "-i"]

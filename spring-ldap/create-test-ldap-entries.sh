#!/usr/bin/env bash
ldapadd -v -h 192.168.99.100:389 -c -x -D uid=admin,ou=system -w secret -f `pwd`/src/main/resources/test-server.ldif

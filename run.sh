#!/usr/bin/env bash
# Skripta za pokretanje projekta: pokrece bazu i aplikaciju
set -e

echo "=== Pokrecem PostgreSQL kontejner ==="
docker-compose up -d

echo "=== Cekam da se baza inicijalizira (5s) ==="
sleep 5

echo "=== Pokrecem aplikaciju ==="
mvn clean compile exec:java "-Dexec.mainClass=hr.algebra.Main"

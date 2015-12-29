#!/bin/sh

BIN_DIR=$(cd -P -- "$(dirname -- "$0")" >/dev/null && pwd -P)
BASE_DIR=`dirname $BIN_DIR`

cd $BASE_DIR
java -jar org.springframework.boot.loader.JarLauncher
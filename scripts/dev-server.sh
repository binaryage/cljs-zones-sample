#!/usr/bin/env bash

set -e

cd "$(dirname "${BASH_SOURCE[0]}")"; cd ..

ROOT=`pwd`
DEVSERVER_ROOT="$ROOT/resources/public"
DEVSERVER_PORT=7000

pushd "$DEVSERVER_ROOT"

echo "serving $DEVSERVER_ROOT on http://localhost:$DEVSERVER_PORT"
python -m SimpleHTTPServer "$DEVSERVER_PORT"

popd

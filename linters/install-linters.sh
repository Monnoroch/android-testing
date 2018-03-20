#!/bin/bash

echo "Running $0..."

set -e

echo "Install google-java-format"

GOOGLE_JAVA_FORMAT_HOME=linters/bin/google-java-format
mkdir -p "${GOOGLE_JAVA_FORMAT_HOME}" && \
file_path="${GOOGLE_JAVA_FORMAT_HOME}/google-java-format.jar" && \
wget --output-document="${file_path}" --quiet https://github.com/google/google-java-format/releases/download/google-java-format-1.5/google-java-format-1.5-all-deps.jar && \
echo "f5124dd4ba467fa7d682f483abae34d7  ${file_path}" | md5sum -c


echo "Install xmllint"

sudo apt-get update
sudo apt-get install -y --no-install-recommends libxml2-utils=2.9.*
sudo apt-get clean
sudo rm -rf /var/lib/apt/lists/*

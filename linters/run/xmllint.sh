#!/usr/bin/env bash

echo "Running $0..."

# find all *.xml files in repository
list=$(git ls-files | grep "\.xml$")

exitcode=0

for file in $list; do
    out=$(xmllint --noout "$file")
    if [ $? -ne 0 ]; then
        echo "xmllint failed for $file."
        echo "$out"
        exitcode=1
    fi
done

exit $exitcode

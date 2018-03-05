#!/usr/bin/env bash

echo "Running google-java-formatter..."

list=$(git ls-files | grep "\.java")

exitcode=0
for file in $list; do
    difflines=$(java -jar linters/bin/google-java-format/google-java-format.jar -length 120 --offset 4 "${file}" | diff -u "$file" -)
    if [ $? -ne 0 ]; then
        echo "File $file is not formatted correctly."
        echo "$difflines"
        exitcode=1
    fi
done

exit $exitcode

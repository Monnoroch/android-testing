#!/usr/bin/env bash

echo "Running $0..."

exitcode=0

DIR=$(dirname "$0")
DIR=$(dirname "$DIR")

output=$(gradle runCheckstyle)
if [ $? -ne 0 ]
then
    echo "${output}"
    echo "gradle checkstyle failed."
    exitcode=1
fi

if [ ! -d ./build/linters ]; then
    echo "there is nothing to check"
    exit $exitcode
fi

# Find all xml reports from a checkstyle plugin.
list=$(find ./build/linters -type f -name "*checkstyle.xml")

# A function to check the checkstyle xml result.
function checkstyle() {
    # Find all errors lines and count them.
    grep -c "^<error.*>$" "$1"
}

for file in $list; do
    count=$(checkstyle "$file")
    # Print the error message and change the exit code if any errors are found.
    if [ "$count" -ne 0 ]
    then
        exitcode=1
        echo "Found $count errors in $file."
    fi
done

exit $exitcode

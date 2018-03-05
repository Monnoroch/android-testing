#!/usr/bin/env bash

echo "Running $0..."

exitcode=0

DIR=$(dirname "$0")
DIR=$(dirname "$DIR")

output=$(gradle runAndroidLint)
if [ $? -ne 0 ]
then
    echo "${output}"
    echo "gradle android-lint failed."
    exitcode=1
fi

if [ ! -d ./build/linters ]; then
    echo "there is nothing to check"
    exit $exitcode
fi

# Find all xml reports from a android-lint plugin.
list=$(find ./build/linters -type f -name "*android-lint.xml")

# A function to check the android-lint xml result.
function androidlint() {
    # Find all errors lines and count them.
    grep -c "<issue$" "$1"
}

for file in $list; do
    count=$(androidlint "$file")
    # Print the error message and change the exit code if any errors are found.
    if [ "$count" -ne 0 ]
    then
        exitcode=1
        echo "Found $count errors in $file."
    fi
done

exit $exitcode

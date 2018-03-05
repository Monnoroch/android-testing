#!/usr/bin/env bash

echo "Running $0..."

exitcode=0

DIR=$(dirname "$0")
DIR=$(dirname "$DIR")

output=$(gradle runFindbugs --stacktrace --info)
if [ $? -ne 0 ]
then
    echo "${output}"
    echo "gradle findbugs failed."
    exitcode=1
fi

if [ ! -d ./build/linters ]; then
    echo "there is nothing to check"
    exit $exitcode
fi

# Find all xml reports from a findbugs plugin.
list=$(find ./build/linters -type f -name "*findbugs.xml")

# A function to check the findbugs xml result.
function findbugs() {
    # Find one line with the text "<FindBugsSummary" and take the "total_bugs" field from it.
    grep "<FindBugsSummary" "$1" | sed -n 's:.*total_bugs="\([^"]\+\).*:\1:p'
}

for file in $list; do
    count=$(findbugs "$file")
    # Print the error message and change the exit code if any errors are found.
    if [ "$count" -ne 0 ]
    then
        exitcode=1
        echo "Found $count errors in $file."
        cat "${file}"
    fi
done

exit $exitcode

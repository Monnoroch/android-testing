#!/bin/bash

echo "Running $0..."

diff_only=0
if [[ "$1" == "--diff-only" ]]; then
    diff_only=1
fi

declare -A extensions_set

if [[ $diff_only -eq 1 ]]; then
    merge_base=$(git merge-base origin/master HEAD)
    for file in $(git diff --name-only ${merge_base}); do
        name=$(basename "$file")
        extension="${name##*.}"
        extensions_set+=([$extension]=1)
    done
    echo "Running linters for extensions: ${!extensions_set[@]}."
else
    echo "Running all linters."
fi

function extension_in_diff {
    if [[ $diff_only -eq 0 ]]; then
        return 0
    fi

    local item="$1"
    if [[ "$item" == "" ]]; then
        return 0
    fi

    result=1
    for element in ${!extensions_set[@]}; do
        if [[ "$element" == "$item" ]]; then
            result=0
        fi
    done
    return $result
}

# Fast linters

exitcode=0

./linters/run/android-lint.sh
if [[ $? -ne 0 ]]; then
    exitcode=1
fi


./linters/run/checkstyle.sh
if [[ $? -ne 0 ]]; then
    exitcode=1
fi

./linters/run/end-line-check.sh
if [[ $? -ne 0 ]]; then
    exitcode=1
fi

./linters/run/findbugs.sh
if [[ $? -ne 0 ]]; then
    exitcode=1
fi


extension_in_diff java
if [[ $? -eq 0 ]]; then
    ./linters/run/google-java-format.sh
    if [[ $? -ne 0 ]]; then
        exitcode=1
    fi
fi


extension_in_diff xml
if [[ $? -eq 0 ]]; then
    ./linters/run/xmllint.sh
    if [[ $? -ne 0 ]]; then
        exitcode=1
    fi
fi

if [[ $exitcode -ne 0 ]]; then
    exit $exitcode
fi

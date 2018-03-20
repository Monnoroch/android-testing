#!/bin/bash

echo "Running $0..."

# extensions to be excluded from scanning
exclude_exts=(
    "png"
    "gif"
    "jks"
    "eot"
    "ttf"
    "woff"
    "ico"
)

# find all tracked files in repository
list=$(git ls-files)

# filter found list on exclude_exts
if [ ${#exclude_exts[@]} -ne 0 ]; then
    # make complete line with exclude rules for grep
    # printf creates a pipe for the array elements
    # sed add . before extensions for prevent match part of file ext, add $ after extensions for detect end of line
    exclude_exts_line=$(printf '%s\n' "${exclude_exts[@]}" | sed 's|^|.|; s|$|$|')
    # grep removes all the lines that contains the values from $exclude_exts array
    list=$(echo "$list" | grep -v -f <(echo "$exclude_exts_line"))
fi

# Filter found rules on variable ENDLINECHECK_EXCLUDE.
if [[ ! -z "${ENDLINECHECK_EXCLUDE}" ]]; then
    # Make complete line with exclude rules for grep.
    # Echo and tr split original line by ":" symbol.
    # Sed adds ^ before lines that start with /, for check them from root folder.
    exclude_paths_line=$(echo "${ENDLINECHECK_EXCLUDE}" | tr ":" "\n" | sed 's|^/|^|')
    # Grep removes all the lines that contains the values from $ENDLINECHECK_EXCLUDE line.
    list=$(echo "$list" | grep -v -f <(echo "$exclude_paths_line"))
fi

exitcode=0
while read -r file; do
    # get 2 last simbols of the file
    lastchars=$(tail -c 2 "${file}")
    # check the last simbol on end of line
    if [[ ${lastchars:1:1} != '' ]]; then
        echo "No empty line at the end of file: ${file}"
        exitcode=1
        continue
    fi
    # check two last simbols on end of line
    if [[ ${lastchars} == '' ]]; then
        echo "Two or more empty lines at the end of file: ${file}"
        exitcode=1
    fi
done < <(echo "${list}")

exit $exitcode

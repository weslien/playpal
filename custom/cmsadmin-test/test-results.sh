#!/bin/bash

output_links() {

    declare -a argArray=("${!1}")
    for name in ${argArray[@]}
    do
        echo "<a href=\"$name\">$name</a><br/>" >> test-result/index.html
    done

}

####### Check success
if [ -e test-result/result.failed ]
then
    echo Tests failed.
    exit 1
elseif [ -d test-result ]
    # Compilation failed.
    echo Compiling Play Framework project failed! No tests output exists in \test-result\ folder.
    exit 1
fi

# All tests succeeded
echo "Tests succeeded."

####### Output test-result/index.html
# HTML Header
echo "<html><head><title>Tests Run - CMSAdmin</title><body>" > test-result/index.html

# Linking passed tests
passed_files=$(find test-result -name "*.passed.html" -prune | sed s/test-result\\///)
if [ -n "${passed_files[@]}" ]; then
    echo "<h3>Tests Passed</h3>" >> test-result/index.html

    output_links passed_files[@]
fi

# Linking failed tests
passed_files=$(find test-result -name "*.failed.html" -prune | sed s/test-result\\///)
if [ -n "${failed_files[@]}" ]; then
    echo "<h3>Tests Failed</h3>" >> test-result/index.html

    output_links $failed_files[@]
fi

# HTML Footer
echo "</body></html>" >> test-result/index.html

###### Create artifact zips
eval "cd test-result"
eval "zip -q ../test-result.zip *.passed.html *.failed.html index.html css js"

if [ -d "test-result/cobertura" ]; then
    eval "cd cobertura"
    eval "zip -q ./../cobertura.zip *"
fi

exit 0
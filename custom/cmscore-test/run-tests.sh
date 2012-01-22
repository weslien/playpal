#!/bin/bash

output_links() {

    declare -a argArray=("${!1}")
    for name in ${argArray[@]}
    do
        echo "<a href=\"$name\">$name</a>" >> test-result/index.html
    done

}

####### Run tests
#play autotest

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
echo "<html><head><title>Tests Run</title><body>" > test-result/index.html

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

#    for name in ${passed_files[@]}
#    do
#        echo "<a href=\"$name\">$name</a>" >> test-result/index.html
#    done

exit 0
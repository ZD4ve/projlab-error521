#!/bin/bash

set -e

output_file="plans.rtf"

doxygen
sed -E 's/^(.*)\{\\f2 \[private\]\}/- \1/' "rtf/refman.rtf" > "tmp1"
sed -E 's/^(.*)\{\\f2 \[protected\]\}/# \1/' "tmp1" > "tmp2"
sed -E 's/^(.*)\{\\f2 \[static\]\}/\\ul\n\1/' "tmp2" > "$output_file"
rm "tmp1" "tmp2"
rm -r rtf
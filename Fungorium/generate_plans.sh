#!/bin/bash

set -e

output_file="plans.rtf"

doxygen
sed -E 's/^(.*)\{\\f2 \[private\]\}/- \1/' rtf/refman.rtf | \
sed -E 's/^(.*)\{\\f2 \[protected\]\}/# \1/' | \
sed -E 's/^(.*)\{\\f2 \[static\]\}/\\ul\n\1/' | \
sed -E 's/^([a-zA-Z][^\n]*\([^\n]*\)\}\})/+ \1/' > "$output_file"
rm -r rtf
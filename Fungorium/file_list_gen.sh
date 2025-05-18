git ls-files | while read file; do
    created=$(git log --diff-filter=A --follow --format="%ad" --date=iso -- "$file" | tail -1)
    size=$(wc -c <"$file")
    printf "%s\t%s\t%s bytes\n" "$file" "$created" "$size"
done

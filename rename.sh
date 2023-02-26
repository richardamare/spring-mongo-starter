#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Usage: $0 <old-project-name> <new-project-name>"
    exit 1
fi

old_project_name=$1
new_project_name=$2

# Rename directory
mv "$old_project_name" "$new_project_name"

# Replace occurrences of old project name in files
grep -rl "$old_project_name" "$new_project_name" | xargs sed -i "s/$old_project_name/$new_project_name/g"

# Rename files with old project name
find "$new_project_name" -type f -name "*$old_project_name*" | while read file; do
    mv "$file" "${file/$old_project_name/$new_project_name}"
done

echo "Project renamed from $old_project_name to $new_project_name"

#!/bin/sh

if which jekyll 2>/dev/null 1>/dev/null; then
  pkill -f jekyll
else
  echo "Jekyll not found, please install it by running the build.sh script"
fi

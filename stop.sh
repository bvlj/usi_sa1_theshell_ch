#!/bin/sh

if which jekyll 2>/dev/null 1>/dev/null; then
  pkill -f jekyll
fi

#!/bin/sh

if ! which jekyll 2>/dev/null 1>/dev/null; then
  sudo gem install jekyll
else
  pkill -f jekyll
fi

cd site/ && jekyll serve --incremental &
sleep 3 && open "http://localhost:4000/"
cd ..

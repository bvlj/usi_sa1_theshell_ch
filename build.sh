#!/bin/sh

if ! which jekyll 2>/dev/null 1>/dev/null; then 
  sudo gem install jekyll
else
  kill -9 $(ps ax | grep jekyll | grep -v grep | cut -d\  -f1)   
fi

cd site/ && jekyll serve &
sleep 3 && open "http://localhost:4000/"


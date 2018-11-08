/// # Bonus 1: link linter
///
/// *Authors:* Claudio Maggioni, Joey Bevilacqua
///
/// Group 1
extern crate reqwest;
extern crate regex;
#[macro_use]
extern crate lazy_static;
extern crate walkdir;

use std::fs::File;
use std::io;
use std::env;
use std::process;
use std::io::prelude::*;
use regex::Regex;
use walkdir::WalkDir;

fn read_file(path: &str) -> io::Result<String> {
    let mut file = File::open(path)?;
    let mut contents = String::new();

    file.read_to_string(&mut contents)?;
    Ok(contents)
}

fn find_urls(html: &str) -> Vec<String> {
    let mut results: Vec<String> = Vec::new();
    
    lazy_static! {
        static ref RE: Regex = Regex::new("(?:(?:href)|(?:src))=\"([^\"]*)\"").unwrap();
    }

    for cap in RE.captures_iter(html) {
        results.push(cap[1].to_string());
    }

    results
}

fn is_external_url_working(url: &str) -> bool {
    let res: Result<reqwest::Response, reqwest::Error> = reqwest::get(url);
    match res {
        Ok(r) => r.status().is_success(),
        Err(_) => false
    }
}

#[test]
fn test_is_url_working() {
    assert!(is_external_url_working("https://www.google.com"));
    assert!(is_external_url_working("https://xkcd.com"));
    assert!(!is_external_url_working("https://xkcd.com/404"));
    assert!(!is_external_url_working("notaurl"));
}

fn main() {
    let args: Vec<String> = env::args().collect();

    if args.len() != 2 {
        println!("Please provide directory with HTML files as 1st argument."); 
        process::exit(127);
    } else {
        let dir = &args[1];
        for entry in WalkDir::new(dir) {
            match entry {
                Ok(en) => {
                    let md: std::fs::Metadata = en.metadata().unwrap();
                    
                    if md.is_dir() {
                        continue;
                    }
                    
                    lazy_static! {
                        static ref RE: Regex = Regex::new(".html$").unwrap();
                    }
                   
                    let file: &str = en.file_name().to_str().unwrap();
                    if RE.is_match(file) {
                        let path: &str = en.path().to_str().unwrap();
                        let contents: String = read_file(path)
                            .expect("cannot read");

                        let urls: Vec<String> = find_urls(contents.as_str());
                        for url in urls {
                            if !is_external_url_working(&url) {
                                println!("{}: '{}' is a broken link.", path, url);
                            }
                        }
                    }
                }
                Err(e) => {
                    println!("{}", e);
                    process::exit(2);
                }
            }
        }
    }
}

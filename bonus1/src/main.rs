/// # Bonus 1: link linter
///
/// *Authors:* Claudio Maggioni, Joey Bevilacqua
///
/// Group 1

extern crate reqwest;
extern crate regex;
#[macro_use]
extern crate lazy_static;

use std::fs::File;
use std::io;
use std::io::prelude::*;
use regex::Regex;

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

fn is_url_working(url: &str) -> bool {
    let res: Result<reqwest::Response, reqwest::Error> = reqwest::get(url);
    match res {
        Ok(r) => r.status().is_success(),
        Err(_) => false
    }
}

#[test]
fn test_is_url_working() {
    assert!(is_url_working("https://www.google.com"));
    assert!(is_url_working("https://xkcd.com"));
    assert!(!is_url_working("https://xkcd.com/404"));
    assert!(!is_url_working("notaurl"));
}

fn main() {
    
}

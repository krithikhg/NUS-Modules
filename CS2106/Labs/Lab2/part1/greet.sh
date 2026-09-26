#!/bin/bash
user=$(whoami)
day=$(date +%A)
date=$(date +%d)
month=$(date +%B)
year=$(date +%Y)
time=$(date +%T)
echo "Hello $user, today is $day, $date $month $year, and the time is $time."

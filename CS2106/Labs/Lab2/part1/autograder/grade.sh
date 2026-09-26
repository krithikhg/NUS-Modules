#!/bin/bash

shopt -s nullglob # Tells bash to return nothing if wildcard finds no matches

# Check if we have enough arguments
if [[ $# -ne 1 ]]; then
    echo "Usage: ./grade.sh <MAXSCORE>"
    exit -1
fi
# Delete temporary files
rm -f ref/*.out subs/*.out *.out subs/**/sum ref/sum
# Compile the reference program
gcc ref/sum.c ref/utils.c -o ref/sum
# Generate reference output files
for input in ref/*.in; do
    ./ref/sum < $input > "$input.out"
done
# Remember to check maximum score given as argument, compared to the real number of test cases
maxScore=$1
numTest=0
for input in ref/*.in; do
    numTest=$((numTest+1))
done

if [[ $numTest -lt maxScore ]]; then
    maxScore=$numTest
fi

# Now mark submissions
echo -e "Test date and time: $(date +%A), $(date +%d) $(date +%B) $(date +%Y), $(date +%T)\n" >> results.out
studentCount=0
for student in subs/*; do
    if [[ ! -d "$student" ]]; then
        continue
    fi
    score=0
    studentCount=$((studentCount+1))
    gcc $student/sum.c $student/utils.c -o $student/sum
    if [[ $? -ne 0 ]]; then
        echo "Directory $(basename $student) has a compile error." >> results.out
    fi
    for input in ref/*.in; do
        ./$student/sum < $input > "$student.$(basename $input).out"
        diff "$student.$(basename $input).out" "$input.out"
        if [[ $? -eq 0 ]]; then
            score=$((score+1))
        fi
    done
    if [[ $score -ge $maxScore ]]; then
        score=$maxScore
    fi
    echo -e "Directory $(basename $student) score $score / $maxScore" >> results.out
done
echo -e "\nProcessed $studentCount files." >> results.out

#
# Note: See Lab02Qn.pdf for format of output file. Marks will be deducted for missing elements.
#

# Iterate over every submission directory
    # Compile C code
    # Print compile error message to output file (if any)
    # Generate output from C code using *.in files in /ref
    # Compare with reference output files  and award 1 mark if they are identical
    # print score for student
# print total submissions marked.

#!/usr/bin/python
from z3 import *
with open("challenges/day21.txt") as f:
    input = f.read().split("\n")

val = {}
val["humn"] = Int("humn")
prevlen = 0
while len(val) != prevlen:
    prevlen = len(val)
    for line in input:
        if line[:4] == "humn":
            continue
        if line[:4] == "root":
            final = (line[6:10], line[13:])
        if any(op in line for op in "+-*/"):
            try:
                exec(f"val['{line[:4]}'] = val['{line[6:10]}'] {line[11]} val['{line[13:]}']")
            except KeyError: pass
        else: exec(f"val['{line[:4]}'] = {line[6:]}")
solve(val[final[0]] == val[final[1]])
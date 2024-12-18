This solution will finish (in approx 100h on my GPU)  
idk, likely only works on windows - I disabled all warnings because I wanted the squiggly lines to go away

Notable issues:
- ComputeSharp doesn't support u64 (everything is a double)
- IEEE754 hating me and everything because imprecision
- casting a double over int.max always yields int.max instead of wrapping around
- debugging on a GPU doesn't exist
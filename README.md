NFA simulator with epsilon moves
==========

Solution for the first lab at "Introduction to Theoretical Computer Science" course - Nondeterministic finite automata simulator with epsilon transitions/moves.

Example
------

Input file consists of minimum 5 rows in this specific order:

1. line - input sequences separated with `|`, symbols of every sequence are separated with `,`
2. line - lexicographically sorted states separated with `,`
3. line - lexicographically sorted symols of the alphabet separated with `,`
4. line - lexicographically sorted acceptable states separated with `,`
5. line - starting state
6. line and all other lines - transition function in format `currentState,alphabetSymbol->collectionOfNextStates`. Epsilon transition is marked with `$`.
Empty collection of next states is marked with `#`.

Input

    a,pnp,a|pnp,lab2|pnp,a|pnp,lab2,utr,utr
    p5,s3,s4,st6,state1,state2
    a,lab2,pnp,utr
    p5
    state1
    s3,a-­>state2
    s3,lab2-­>p5,s4
    s4,$-­>st6
    s4,utr-­>p5,s3
    state1,a-­>state2
    state1,pnp-­>s3
    state2,$-­>st6
    state2,a-­>#

will give output

    state1|st6,state2|#|#
    state1|s3|p5,s4,st6
    state1|s3|st6,state2
    state1|s3|p5,s4,st6|p5,s3|#

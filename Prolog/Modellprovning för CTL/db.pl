:- use_module(library(lists)).

%                             Adj    Labeling   Sate   Formula
verify(Input) :- see(Input), read(T), read(L), read(S), read(F), seen,
check(T, L, S, [], F).

checkAX(T, L,[H|Tail], X) :- check(T, L, H, [], X), checkAX(T, L, Tail, X).
checkAX(T, L, [], X).

iterAdj(T, L,[H|Tail], U, X) :- check(T, L, H, U, X), iterAdj(T, L, Tail, U, X).
iterAdj(T, L, [], U, X).

% Literals
check(T, L, S, [], X) :- member([S, K], L), member(X, K).

% Negation
check(T, L, S, [], neg(X)) :- \+ check(T, L, S, [], X).

% And
check(T, L, S, [], and(F,G)) :- check(T, L, S, [], F), check(T, L, S, [], G).

% Or1
check(T, L, S, [], or(F,G)) :- check(T, L, S, [], F).

% Or2
check(T, L, S, [], or(F,G)) :- check(T, L, S, [], G).

% AX
check(T, L, S, [], ax(X)) :- member([S, Adj], T), checkAX(T, L, Adj, X).

% EX
check(T, L, S, [], ex(X)) :- member([S, Adj], T), member(Nstate, Adj), check(T, L, Nstate, [], X).

% AG 1
check(T, L, S, U, ag(X)) :- member(S, U).

% AG 2
check(T, L, S, U, ag(X)) :- \+ member(S, U), check(T, L, S, [], X), member([S, Adj], T), iterAdj(T, L, Adj, [S|U], ag(X)).

% EG 1
check(T, L, S, U, eg(X)) :- member(S, U).

% EG 2
check(T, L, S, U, eg(X)) :- \+ member(S, U), check(T, L, S, [], X), member([S, Adj], T), member(Node, Adj), check(T, L, Node, [S|U], eg(X)).

% EF 1
check(T, L, S, U, ef(X)) :- \+ member(S, U), check(T, L, S, [], X).

% EF 2
check(T, L, S, U, ef(X)) :- \+ member(S, U), member([S, Adj], T), member(Node, Adj), check(T, L, Node, [S|U], ef(X)).

% AF 1
check(T, L, S, U, af(X)) :- \+ member(S, U), check(T, L, S, [], X).

% AF 2
check(T, L, S, U, af(X)) :- \+ member(S, U), member([S, Adj], T), iterAdj(T, L, Adj, [S|U], af(X)).

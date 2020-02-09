% import proof
verify(InputFileName) :- see(InputFileName),
read(Prems), read(Goal), read(Proof),
seen,
valid_proof(Prems, Goal, Proof, [], 0).

% no proof and nothing proved cant be a valid proof
valid_proof(Prems, Goal, [], [], InBox) :- !, fail.

% premise
valid_proof(Prems, Goal, [[RowNumb, Formula, premise] | T], Proved, InBox) :- member(Formula, Prems), valid_proof(Prems, Goal, T, [[RowNumb, Formula, premise] | Proved], InBox).

% assumpiton
valid_proof(Prems, Goal, [[[RowNumb, Formula, assumption] | T2] | T1], Proved, InBox) :- valid_proof(Prems, Goal, T2, [[RowNumb, Formula, assumption] | Proved], 1), valid_proof(Prems, Goal, T1, [[[RowNumb, Formula, assumption] | T2] | Proved], InBox).

% lem
valid_proof(Prems, Goal, [[RowNumb, or(X, neg(X)), lem] | T], Proved, InBox) :- valid_proof(Prems, Goal, T, [[RowNumb, or(X, neg(X)), lem] | Proved], InBox).

% contel
valid_proof(Prems, Goal, [[RowNumb, Formula, contel(X)] | T], Proved, InBox) :- member([X, cont,_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, contel(X)] | Proved], InBox).

% andint
valid_proof(Prems, Goal, [[RowNumb, and(Xval, Yval), andint(X, Y)] | T], Proved, InBox) :- member([X, Xval,_], Proved), member([Y, Yval,_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, and(Xval, Yval), andint(X, Y)] | Proved], InBox).

% andel1
valid_proof(Prems, Goal, [[RowNumb, Formula, andel1(X)] | T], Proved, InBox) :- member([X, and(Formula,_),_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, andel1(X)] | Proved], InBox).

% andel2
valid_proof(Prems, Goal, [[RowNumb, Formula, andel2(X)] | T], Proved, InBox) :- member([X, and(_,Formula),_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, andel2(X)] | Proved], InBox).

% orint1
valid_proof(Prems, Goal, [[RowNumb, or(Xval, Yval), orint1(X)] | T], Proved, InBox) :- member([X, Xval,_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, or(Xval, Yval), orint1(X)] | Proved], InBox).

% orint2
valid_proof(Prems, Goal, [[RowNumb, or(Xval, Yval), orint2(Y)] | T], Proved, InBox) :- member([Y, Yval,_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, or(Xval, Yval), orint2(Y)] | Proved], InBox).

% impel
valid_proof(Prems, Goal, [[RowNumb, Formula, impel(X,Y)] | T], Proved, InBox) :- member([X, Xval, _], Proved), member([Y, imp(Xval, Formula), _], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, impel(X,Y)]|Proved], InBox).

% impint
valid_proof(Prems, Goal, [[RowNumb, imp(Xval, Yval), impint(X,Y)] | T], Proved, InBox) :- getBox(X, Proved, NewList), member([X, Xval, _], NewList), member([Y, Yval, _], NewList), valid_proof(Prems, Goal, T, [[RowNumb, imp(Xval, Yval), impint(X,Y)] | Proved], InBox).

% negnegel
valid_proof(Prems, Goal, [[RowNumb, Formula, negnegel(X)] | T], Proved, InBox) :- member([X, neg(neg(Formula)),_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, negnegel(X)] | Proved], InBox).

% negnegint
valid_proof(Prems, Goal, [[RowNumb, neg(neg(Formula)), negnegint(X)] | T], Proved, InBox) :- member([X, Formula,_], Proved), valid_proof(Prems, Goal, T, [[RowNumb, neg(neg(Formula)), negnegint(X)] | Proved], InBox).

% negel
valid_proof(Prems, Goal, [[RowNumb, cont, negel(X,Y)] | T], Proved, InBox) :- member([X, Xval, _], Proved), member([Y, Yval, _], Proved), neg(Xval) = Yval, valid_proof(Prems, Goal, T, [[RowNumb, cont, negel(X,Y)] | Proved], InBox).

% negint
valid_proof(Prems, Goal, [[RowNumb, neg(Xval), negint(X,Y)] | T], Proved, InBox) :- getBox(X, Proved, NewList), member([X, Xval, assumption], NewList), member([Y, cont, _], NewList), valid_proof(Prems, Goal, T, [[RowNumb, Formula, negint(X,Y)] | Proved], InBox).

% Copy
valid_proof(Prems, Goal, [[RowNumb, Formula, copy(X)] | T], Proved, InBox) :- member([X, Formula, _], Proved), valid_proof(Prems, Goal, T, [[RowNumb, Formula, copy(X)] | Proved], InBox).

% orel
valid_proof(Prems, Goal, [[RowNumb, Zval, orel(X, Y, Z, R, W)] | T], Proved, InBox) :- getBox(Y, Proved, NewListY), getBox(R, Proved, NewListR), member([Y, Yval, _], NewListY), member([R, Rval, _], NewListX), member([X, or(Yval, Rval), _], Proved), member([Z, Zval, _], NewListY), member([W, Zval, _], NewListR), valid_proof(Prems, Goal, T, [[RowNumb, Zval, orel(X, Y, Z, R,W)] | Proved], InBox).

% PBC
valid_proof(Prems, Goal, [[RowNumb, Formula, pbc(X,Y)] | T], Proved, InBox) :- getBox(X, Proved, NewList), member([X, neg(Formula), assumption], NewList), member([Y, cont, _], NewList), valid_proof(Prems, Goal, T, [[RowNumb, Formula, pbc(X,Y)]|Proved], InBox).

% mt
valid_proof(Prems, Goal, [[RowNumb, neg(Xval), mt(X,Y)] | T], Proved, InBox) :- member([X, imp(Xval, Yval), _], Proved), member([Y, neg(Yval), _], Proved), valid_proof(Prems, Goal, T, [[RowNumb, neg(Xval), mt(X,Y)]|Proved], InBox).

% a valid proof cant end on an assumpiton
valid_proof(Prems, Goal, [], [[_, _, assumption]|T], 0) :- !, fail.

% a valid proof must end in its goal
valid_proof(Prems, Goal, [], [[_, Goal, _]|T], 0).

% we are currently in an assumption box
valid_proof(Prems, Goal, [], L, 1).

getBox(X, [L|_], L):- member([X, _, _], L).
getBox(X, [_|T], L):- getBox(X, T, L).

% For corner cases and examples, see the project page.

% prod(P,L) :- P is product of entries in list L
prod(1,[]) :- !.
prod( X,[H|T]) :-
	prod(Y,T),
	X is H * Y.

% add_tail(L,M,E) :- L is M with [E] appended.  prolog already has append/3
add_tail([E],[],E) :- !.
add_tail([L1|T1],[L1|T2],E) :-
	add_tail(T1,T2,E).
	
% fill(L,X,N) :- L is list of N copies of X
%Z is Y.
fill([],_,0) :- !.
fill([],_,N) :- N =< 0, !.
fill([X],X,1) :- !.
fill([X|T],X,N) :- 
	2 =< N,
	N2 is N-1,
	fill(T,X, N2).

%pos(L,E,Ind,Curr)
pos([],_,Ind,_, Ind) :-!.
pos([H|T], E,_,Curr,X) :-  H = E, Curr2 is Curr + 1, pos(T,E,Curr,Curr2,X).
pos([H|T], E, Ind, Curr,X) :- H \=E, Curr2 is Curr + 1, pos(T,E,Ind,Curr2,X).

% rindex(I,L,E) :- I is rightmost 0-based index of E in L (or -1 if E is not in L).
% TO DO: implement rindex/3
rindex(-1, [],_) :-!.
rindex(0,[E],E) :-!.
rindex(I,L,E) :- pos(L,E,-1,0,X), I is X.

% even_elts(L,M) :- L is a list containing the even elements of list M
 even_elts([],[]) :-!.
 even_elts([X],[X]) :-!.
 even_elts([H1|H2],[H1,_|T1]) :-
	even_elts(H2,T1).

%sub_hlp(L,M,N,Ct,SL) :- L is the given list, M is the begin index, N is the end, Ct is the index, and SL is the returned list
 sub_hlp([],_,_,_,[]) :-!.
 sub_hlp(_,M,N,Ct,SL) :- Ct > N.
 sub_hlp([H|T],M,N,Ct,SL) :- Ct >= M, Ct =< N, append(SL,[H],SL2), Ct2 is Ct + 1, sub_hlp(T,M,N,Ct2,SL2).
 sub_hlp([H|T],M,N,Ct,SL) :- Ct2 is Ct + 1, sub_hlp(T,M,N,Ct2,SL2).
 
% sublist(K,M,N,L) :- K is a sublist of L from indeces M to N, inclusive.
sublist([],_,_,[]) :-!.
sublist([],M,N,L):- N < M, !.
sublist(K,M,N,L):- sub_hlp(L,M,N,0,K).


%cut_hlp (M,L) L is the given list and M is the new 
cut_hlp(L2,[H|T]) :- append(T,[H],L2).

%cut_lst(M,L). L is the given list, M is the new list without the last element 
cut_lst([],[]):-!.
cut_lst([M],[M]) :- !.
cut_lst(M,L) :-  reverse(Rl,L), cut_hlp(M1,Rl), reverse(M,M1).

% rotate(L,M,N) :- List L is the list M, rotated N times to the right. Alternatively, M is L, rotated N times to the left.
rotate([],[],_):- !.
rotate([M|T],[M|T],N) :- N =< 0, !.
%rotate([M],[M],_):-!.
rotate(L,M,N) :- 
	cut_lst(L2,M),
	N2 is N -1,
	rotate(L,L2,N2).

% unzip(Lefts,Rights,L) :- L is a list of pairs, Lefts is the list of all the
% 	left elements from L, Rights is the list of all the right elements of L.
unzip([],[],[]) :-!.
unzip([H1|Lefts],[T1|Rights],[[H1,T1]|T2]):- unzip(Lefts,Rights,T2).


% poly(CS, X, Val) :- CS is a list of constants; Val = CS[0]*X^n + ... + CS[n]*X + CS[n+1]
poly([],_,0) :-!.
poly([H],X,V) :- V is H * (X**0), !.
poly([H|T],X,V):-
	length(T,L),
	poly(T,X,V2),
	V is V2 + H * (X**L).
	

% in_set(X) :- List X represents a string that is in the set
% {a^n b^n a^m b^m | n,m >= 1 } U {a^n b^m a^m b^n | n,m >= 1}
% i.e., it matches the language captured by the following CFG:
%    S -> T | V
%    T -> UU
%    U -> aUb | ab
%    V -> aVb | aWb
%    W -> bWa | ba
% TO DO: implement in_set/1
%in_set(L) :- setT(L), !; setV(L), !.
%setT(L) :- append(L1,L2,L), setU(L1),setU(L2). 
%setU(L) :- 
	%setU(L),
	%append(L,b, L2), 

	s --> t.
	s --> v.
	t --> u,u.
	u --> [a],u,[b]. 
	u --> [a],[b].
	v --> [a],v,[b].
	v --> [a],w,[b].
	w --> [b],w,[a].
	w --> [b],[a].
	in_set(L) :- s(L,[]), !.

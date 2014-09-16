maze(4,0,3,0,0).
cell(0,0,[d],[0.391538986557049]).
cell(1,0,[r,d],[16.597130417636, 0.889878639213553]).
cell(2,0,[l,d],[0.011123208182191, 18.6496954092342]).
cell(3,0,[],[]).
cell(0,1,[u,r],[63.1258159853081, 3.14882640637611]).
cell(1,1,[u,l,r,d],[0.577082416767899, 11.2788559044107, 0.0116108917113176, 25.6907194043197]).
cell(2,1,[u,l,r,d],[89.8399554017928, 0.120311605902415, 0.0687987167581341, 198.151088713489]).
cell(3,1,[l,d],[0.0114526228490019, 152.662532290366]).
cell(0,2,[r,d],[0.0148854629087619, 0.019301544005463]).
cell(1,2,[u,l,r],[9.93466159987408, 0.199913563972552, 1.26393492879008]).
cell(2,2,[u,l,d],[12.3336316807166, 1.59269860596813, 0.680879328533728]).
cell(3,2,[u],[18.8277117544323]).
cell(0,3,[u],[15.6415340291405]).
cell(1,3,[],[]).
cell(2,3,[u,r],[0.216152697975287, 0.0138637250041849]).
cell(3,3,[l],[0.0113867473179591]).
path("path1",0,3,[u,r,u,l,u]).
path("path2",0,3,[u,r,r,u,l,l,u]).


%reachable(Cs0, Ws,Fs)
/*
  * reachable(C,Fs) holds iff cells in list Fs are reachable via some
  *   path from cell C.  This recursively invokes predicate
  *   reachable(Cs,Ws,Fs) where Ws is a worklist of nodes visited, and
  *   Cs is a list of nodes to consider. Once Cs becomes empty, the
  *   final list is the worklist.
  */
 add_cells(_, [],Q,Q) :-!.
 add_cells([SX,SY],[H|T],Q,Q2) :- 
	H = u, EY is SY - 1, append([[SX,EY]],Q, NQ), add_cells([SX,SY],T,NQ,Q2);
	H = d, EY is SY + 1, append([[SX,EY]],Q, NQ), add_cells([SX,SY],T,NQ,Q2);
	H = l, EX is SX - 1, append([[EX,SY]],Q, NQ), add_cells([SX,SY],T,NQ,Q2);
	H = r, EX is SX + 1, append([[EX,SY]],Q, NQ), add_cells([SX,SY],T,NQ,Q2).
									 
  
reachable(C,Fs) :- reachable([C],[],Fs).
reachable([], Ws,Ws) :-!.
reachable([[X,Y]|T], Ws,F) :- member([X,Y],Ws), reachable(T, Ws,F).
reachable([[X,Y]|T], Ws,F) :- cell(X,Y,Dir,_), add_cells([X,Y],Dir,T,Q), append([[X,Y]],Ws,Wsn), reachable(Q,Wsn,F).

%matches weight with dir 
match(D,Sum,[],[]) :-!.
match(D,Sum,[L1|T1],[L2|T2]) :- L1 = D, Sum is L2.
match(D,Sum,[L1|T1],[L2|T2])  :- match(D,Sum,T1,T2).
 
%takes in a Start X and Y and direction list and adjusts the directions 
move(SX,SY, [], W,W) :-!.
move(SX,SY, [H|T], W,Ws) :- 
	H = u,  cell(SX,SY,Dir,Wgh), match(u,W1, Dir,Wgh), W2 is W + W1, SY1 is SY - 1, move(SX,SY1,T,W2,Ws) ;
	H = d, cell(SX,SY,Dir,Wgh), match(d,W1, Dir,Wgh), W2 is W + W1 , SY1 is SY + 1, move(SX,SY1,T,W2,Ws) ;
	H = l,  cell(SX,SY,Dir,Wgh), match(l,W1, Dir,Wgh),W2 is W + W1, SX1 is SX - 1 , move(SX1,SY, T,W2,Ws) ;
	H = r,  cell(SX,SY,Dir,Wgh), match(r,W1, Dir,Wgh),  W2 is W + W1 , SX1 is SX + 1, move(SX1,SY, T,W2,Ws).
/*
  * okpath(N,Wt) iff
  *  named path N is legal for the maze and has weight Wt rounded to the closest integer
  */
 okpath(N,Wt2) :-  path(N,SX,SY,Dir), move(SX,SY,Dir,0,Wt), Wt2 is round(Wt).


/*
  * bestpath(N,W) iff path named N is the shortest among the named paths, with integer weight W
  */
bestpath(N,W) :- findall(Wt, okpath(_,Wt), WL), min_list(WL,W), okpath(N,W).

/*
  * stats(U,D,L,R) iff the maze has
  *   U cells with open up-walls
  *   D cells with open down-walls, etc.
  */

% count(X,N,L,N) :- where X is the element, N is the number of X and L is the list. the second N is the return val
 count(_,N,[],N) :-!.
count(X,N,[H|T], N2) :- H = X, N1 is N + 1, count(X,N1,T,N2).
count(X,N,[_|T], N2) :- count(X,N,T,N2).
  
/* TO DO: define stats(U,D,L,R) */
stats(U,D,L,R) :-
	findall(Dir,cell(_,_,Dir,_), UL), flatten(UL,L1),
		count(u,0,L1,W1),
		U is W1, 
		count(d,0,L1,W2),
		D is W2, 
		count(l,0,L1,W3),
		L is  W3, 
		count(r,0,L1,W4), 
		R is W4.
	

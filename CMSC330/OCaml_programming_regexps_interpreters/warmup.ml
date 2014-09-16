(* Put your solutions to part 1 in this file *)
let rec prod l  = match l with 
	[] -> 1
	|[x] -> x
	|h::t -> h * (prod t);;
	
let rec add_tail l e = 
	match l with 
	[] -> [e]
	|(fst::rest) -> fst:: (add_tail rest e);;

let rec fill x n = 
	if n <= 0 then []
	else x::(fill x (n-1));;
	
let rec length x =
  match x with
      [] -> 0
    | _::t -> 1+(length t);;
	
	
let rec append_item ls elem =
    match ls with
    [] -> [elem]
  | h::t -> 
          let t' = append_item t elem in
          h :: t';;

append_item [1;2] 3;;        (* What is the trace of this? *)

(* A quick divergence: recall the '@' operator: let's write our own *)

let rec append_list ls ls' =
    match ls with
    [] -> ls'
  | h :: t -> h :: (append_list t ls');;


	
let rindex l e = 
 let rec index l pos i = 
    match l with 
    [] -> pos
    |h::t -> if (h = e) then index t i (i+1)
             else index t pos (i+1)
 in index l (-1) 0;;
		
let rec even_elts l = 
	match l with 
	[] -> []
	|[x] -> [x]
	|(fst::sec::rest) -> fst:: (even_elts rest);;
	
(*Sublist helper*)
let rec index l i n = 
       if i >= n then l
       else 
	match l with 
	[] -> []     
        |h::t -> (index t (i+1) n)
;;
let rec chop l i m =
      if i < 0 then m
      else
      match l with 
      [] -> []
      |h::t -> (chop t (i-1) (add_tail m h))
;;
	  
let sublist n m l=
	chop (index l 0 n) (m-n) []
;;

let rec getTail l =
	  match l with 
	   h::[] -> h
      |h::t -> getTail t
;;

let rec rotate n l =
	if n < 1 || (length l) <= 1 then l
	else 
	rotate (n-1) ((getTail l)::(chop l ((length l)-2) []))
;;

let rec app_int f m n = 
	if n < m then []
	else 
		if m <= n then (f m)::(app_int f (m+1) n)
		else []
;;
	
let rec xcord l =
	match l with 
	[] -> []
	|(a,b)::t -> a:: xcord t
;;

let rec ycord l = 
    match l with 
	[] -> []
	|(a,b)::t -> b:: ycord t
;;

let unzip l =
	if (length l) < 1 then ([],[])
    else ((xcord l),(ycord l))
;;
type expr =
    Int of int
  | Negate of expr
  | Plus of expr * expr
  | Minus of expr * expr
  | Mult of expr * expr
  | Var of string

type assignment = (string * int) list

let rec no_mult expr = 
	match expr with
	Int x -> true 
	|Negate x -> true
	|Plus	(x,y) -> true
	|Minus	(x,y) -> true
	|Mult	(x,y) -> false
	|Var x -> true
;;

(*Helper for vars_of*)
let rec vars_help expr =
	match expr with 
	Int x -> [] 
	|Var x -> [x]
	|Negate x -> vars_help x
	|Plus	(x,y) -> (vars_help x) @ (vars_help y)
	|Minus	(x,y) -> (vars_help x) @ (vars_help y)
	|Mult	(x,y) -> (vars_help x) @ (vars_help y)
;;
let rec hasElm e lst =
		match lst with
		[] -> false 
		|h::t -> if (h = e) then true
				else hasElm e t
;;
let rec append_item ls elem =
    match ls with
    [] -> [elem]
  | h::t -> 
          let t' = append_item t elem in
          h :: t';;

let rec uniq l arr =
	match l with 
	[] -> arr
	|h::t -> if (hasElm h arr) = false then 
				uniq t (append_item arr h)
			else uniq t arr
;;

let vars_of expr =
	uniq (vars_help expr) []
;;

let rec eval assignment expr =
	match expr with 
	Int x -> x
	|Negate x -> -(eval assignment x)
	|Plus	(x,y) -> (eval assignment x) + (eval assignment y)
	|Minus	(x,y) -> (eval assignment x) - (eval assignment y)
	|Mult	(x,y) -> (eval assignment x) * (eval assignment y)
	|Var x -> List.assoc x assignment 
;;

let rec replace expr tofind =
	match expr with 
	[] -> Var(tofind)
	|(a,b)::t -> if (a = tofind) then b
				 else replace t tofind
;;

let rec subst expr str= 
	match expr with 
	Int v -> Int v
	|Negate x -> Negate(subst x str )
	|Plus	(x,y) -> Plus((subst x str),(subst y str))
	|Minus	(x,y) -> Minus((subst x str),(subst y str))
	|Mult	(x,y) -> Mult((subst x str),(subst y str)) 
	|Var x -> replace str x
;;

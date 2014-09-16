(* Put your solutions to part 2 in this file *)

(* Do *not* change the following data type *)
type regexp =
    REEpsilon
  | REConst of char
  | REStar of regexp
  | REUnion of regexp * regexp
  | REConcat of regexp * regexp

let rec check reg = 
	match reg with 
	REEpsilon  -> true
	|REConst reg1 -> false 
	|REStar reg1 -> true
	|REUnion (reg1,reg2) -> if (check reg1 = true) then true
				          else (check reg2) 
	|REConcat (reg1,reg2) -> if (check reg1 = true) then (check reg2)
						  else false 
;;
	
let rec conHelp reg1 reg2 = 
   match reg1 with 
   [] -> []
   |(a,e)::t -> (a,REConcat(e,reg2)):: (conHelp t reg2)
;;

let rec starHelp reg self= 
	match reg with 
	[] -> []
	|(a,f)::t -> (a,REConcat(f,REStar self))::(starHelp t self)
;;
let rec trans reg =
    match reg with 
	REEpsilon -> []
	|REConst reg1 -> [(reg1,REEpsilon)]
	|REUnion (reg1,reg2) -> trans reg1 @ trans reg2
	|REConcat (reg1,reg2) -> if (check reg1 = true) then 
				     (conHelp (trans reg1) reg2) @ (trans reg2)
				 else (conHelp (trans reg1) reg2) @ []
	|REStar reg -> starHelp (trans reg) reg
;;

	
    					 
							 
							 
							 
							 
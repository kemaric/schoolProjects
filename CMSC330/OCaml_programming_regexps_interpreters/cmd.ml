#use "arith.ml"

type cmd =
    Skip
  | Assign of string * expr
  | Seq of cmd * cmd
  | IfNonZero of expr * cmd * cmd
  | WhileNonZero of expr * cmd

  
let rec exec assignment cmd =
	match cmd with 
	Skip -> assignment
	|Assign (str,exp)-> [(str,(eval assignment exp))] @ assignment 
	|Seq (c1,c2)-> (exec (exec assignment c1) c2)
	|IfNonZero (exp,c1,c2) -> if(eval assignment exp) != 0 then (exec assignment c1)
								else (exec assignment c2)
	|WhileNonZero (exp,c) -> if(eval assignment exp) = 0 then assignment
							else exec (exec assignment c) cmd
;;
(**********************************************************************)

(*
** Abstract syntax
*)

type expr =
  | EVar of string
  | ENum of int
  | EPlus of expr * expr

type cmd =				(* Suggested concrete syntax:	*)
  | Skip				(* skip *)
  | Halt				(* halt *)
  | Assign of string * expr		(* x = E *)
  | Goto of string			(* goto id *)
  | IfNonZero of expr * string		(* if E != 0 then goto id *)
  | Spawn of string  			(* spawn id *)
  | Acquire of string			(* acquire id *)
  | Release of string			(* release id *)
  | Wait of string			(* wait id *)
  | NotifyAll of string			(* notifyAll id	*)
 (* The commands below are the ones you will implement *)
  | TryAcquire of string * string
  | RdAcquire of string
  | WrAcquire of string
  | RdWrRelease of string
  | Barrier
  | CreateLatch of string * expr
  | WaitLatch of string
  | CountDown of string
  | AcqSem of string
  | RelSem of string

let print_cmd = function
  | Skip -> print_string "skip"
  | Halt -> print_string "halt"
  | Assign _ -> print_string "assign"
  | Goto _ -> print_string "goto"
  | IfNonZero _ -> print_string "if"
  | Spawn _ -> print_string "spawn"
  | Acquire _ -> print_string "acquire"
  | Release _ -> print_string "release"
  | Wait _ -> print_string "wait"
  | NotifyAll _ -> print_string "notifyAll"
 (* TO DO: Add cases below if you want to add printers *)
  | _ -> print_string "UNKNOWN COMMAND"

(*********************************************************************
**
** Run-time data structures
*)

type mem = (string * int) list
type pc = int
(* Sugg. conc. syn.-  id:  cmd 	*)
type program = (string option * cmd) array (* string option is label*)

(*********************************************************************
**
** Auxiliary semantic functions and values used in semantics
*)

(*
** eval evaluates expressions in a given memory.
*)

let rec eval (m:mem) = function
  | EVar x -> List.assoc x m
  | ENum n -> n
  | EPlus (e1, e2) -> (eval m e1) + (eval m e2)

(*
** find_label p l computes the index of the first instruction labeled by 
l.
** If the label is not in the program, an array out-of-bounds exception 
will
** be reported.
*)

let find_label (p:program) (l:string) =
  let rec find_label' i =
    if (fst (p.(i))) = Some l then i else find_label' (i+1)
  in
    find_label' 0

(*
** upd m x n produces a new memory like m but with x updated to v.
*)

let upd (m:mem) (x:string) (n:int) =
  (x, n)::(List.remove_assoc x m)

(*
** Following are various functions for manipulating 'a option values.
*)

let val_of = function
  | Some x -> x
  | None -> failwith "Error"

let is_some = function
  | Some _ -> true
  | None -> false

let is_none = function
  | Some _ -> false
  | None -> true

(*
** remove x l removes x from the list argument l
*)

let rec remove x = function
    [] -> []
  | (h,m,n)::t -> if x = h then (remove x t) else (h,m,n)::(remove x t)

 let rec wrremove x y ls = match ls with  
	[]->[]
	|(h,n)::t -> if (x = h && y = n) then (wrremove x y t) else (h,n)::(wrremove x y t)
	
let rec clear_barr = function
	[]->[]
	|_::t -> (clear_barr t)
;;
(*
let rec num_running = function 
	[]-> 0
	
;;*)
let rec decr_latch lac = function 
	[]->[]
	|(l,n)::t -> 
	if (lac = l && n > 0) then (l,(n-1))::(decr_latch lac t) 
	else (l,n)::(decr_latch lac t)
;;

let rec change_sem sem cond = function 
	[]->[]
	|(l,n)::t -> 
	if l = sem then 
		if cond = "+" then(l,(n+1))::(change_sem sem cond t)
		else (l,(n-1))::(change_sem sem cond t)
	else (l,n)::(change_sem sem cond t)
(**********************************************************************
**
** INTERPRETER 
*)

type tid = int
type lock = string
type wait_elt =
  | Awaiting of lock
  | Woken of lock
type waiter = tid * wait_elt

exception My_exception of int 

let rec assoc l = match l with 
	[] -> []
	|(h,t,n)::rst -> (h,t)::assoc(rst);;

let rec decr_loc loc = function
	[]->[]
	|(h,t,n)::rst -> 
		if loc = h then 
			if (n > 1) then (h,t,(n-1))::(decr_loc loc rst)
			else (decr_loc loc rst)
		else (h,t,n)::(decr_loc loc rst)
;;

let rec upd_loc loc = function
	[]->[]
	|(h,t,n)::rst -> if loc = h then (h,t,(n+1))::(upd_loc loc rst) 
		else (h,t,n)::(upd_loc loc rst)
;;
let rec to_arr l = match l with 
	[]->[]
	|(h,t,n)::rst -> h:: to_arr rst
;;
(* TO DO: You may modify the type config, but do not modify any other 
types *)

(*lock is a list of lock in use by threads*)
type lock' = (lock * tid * int)
(*r is read, w is write*)
type barrier = (tid * int) list
type rlocks = (lock * tid ) list
type wlocks = (lock * tid ) list 
type latchs = (string * int) list 
type sema =  (string * int) list 
type config = mem * (pc option array) * (lock' list) * (waiter list) * rlocks * wlocks * barrier *  latchs * sema 

type scheduler = pc option array -> int

let print_config ((m,pcs,locks,ws,rlocks, wlocks,barrier,latchs,sema):config):unit =
  let m' = List.map (fun (s,n) -> Printf.sprintf "%s=%d" s n)
    (List.sort (fun (x,_) (y,_) -> compare x y) m) in
  let pcs = Array.map (function Some pc -> Printf.sprintf "pc %d" pc | 
None -> "end") pcs in
  let wait_to_string = function Awaiting l -> Printf.sprintf "await %s" l
 | Woken l -> Printf.sprintf "woken %s" l in
  let ws' = List.map (fun (t, w) -> Printf.sprintf "%d:%s" t (
wait_to_string w)) ws in
    Printf.printf "[%s] <%s> {%s} %s\n" (String.concat "; " (Array.to_list pcs))
      (String.concat "; " (to_arr locks))
      (String.concat "; " ws')
      (String.concat "; " m')

(*
** Update function for thread pools.  The idea is to update the program 
counter for
** thread tid.
*)

let upd_pc (tid:int) (pc:pc option) (pcs:pc option array):pc option array
 =
  let pcs' = Array.copy pcs in
    pcs'.(tid) <- pc;
    pcs'

(* 
** The next functions are used to implement schedulers, which the run 
functions
** will use to select the next thread to execute.
*)

(* first_avail computes the first available thread after thread n that is
** capable of executing.  Note that this can diverge if there is no such 
thread.
*)

let rec first_avail (pcs: pc option array) (n:tid) =
  let n' = n mod (Array.length pcs) in
    match pcs.(n') with
      | Some _ -> n'
      | None -> first_avail pcs ((n'+1) mod (Array.length pcs))

(* The sequential scheduler executes each thread to completion:  no 
interleaving *)
let seq_sched (pcs:pc option array):int =
  first_avail pcs 0

(*
** make_rr_sched produces a new scheduler that executes available 
processes in
** a round-robin fashion.  It uses an internal state variable to record 
the next possible
** thread to execute.
*)

let make_rr_sched () =
  let n = ref 0 in
    fun (pcs:pc option array) ->
      let temp = first_avail pcs (!n) in
	n := temp + 1;
	temp

(** TO DO (part 1): write function make_rr_sched_n : int -> scheduler
    that creates a scheduler that runs each thread for n ticks (rather
    than 1 tick) before switching to the next available thread. It should
    be the case that make_rr_sched_n 1 returns a scheduler that behaves
    identically to make_rr_sched (). *)
let make_rr_sched_n n = 
	(*let n = ref n in*)
	(*if(n = 1) then make_rr_sched else*)
		let tid = ref 0 in 
			let counter =  ref 0 in 
				fun (pcs:pc option array)->
					let temp = first_avail pcs (!tid) in
						if ((!counter) < n && temp = (!tid)) then 
							(counter := (!counter) + 1; 
							temp)
						else(
							tid := first_avail pcs ((!tid) + 1); 
							counter := 1;
							(!tid)
							)
	;;

(*
 ** notify_all lock changes all tid's corresponding to lock to be Woken.
 *)
let rec notify_all lock = function
    [] -> []
  | (tid, Awaiting lock')::t ->
      if lock = lock' then
	(tid, Woken lock')::(notify_all lock t)
      else
	(tid, Awaiting lock')::(notify_all lock t)
  | (tid, w)::t -> (tid, w)::(notify_all lock t)

(*
** add_waiter adds tid as waiting for lock to ws.
*)
let add_waiter lock tid ws =
  (tid, Awaiting lock)::ws

(*
** remove_waiter removes tid from the list of waiters
*)
let rec remove_waiter tid = function
  | [] -> []
  | (tid', w)::t -> if tid = tid' then (remove_waiter tid t) else (tid', 
w)::(remove_waiter tid t)

(* TODO (part 4, optional): implemented deadlocked : program -> config
   -> bool to determine whether a configuration is deadlocked. Call this
   function from step in the cases for Acquire/Release *)

(*
 ** Step function for programs: invokes the scheduler to pick a thread, 
and steps it
 *)
let rec step (sched:scheduler) (p:program) ((m,pcs,locks,ws,rlocks, wlocks, barrier, latchs,sema):config):
config =
  let tid = sched pcs in (* pick a thread *)
    (* deal with the thread if it was/is waiting *)
    if List.mem_assoc tid ws then 
      match List.assoc tid ws with
	| Awaiting _ -> (m, pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)  (* still asleep *)
	| Woken lock ->
			(*EDIT: turned lockes into string array*)
	    if List.mem lock (to_arr locks) then
	      (m, pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema) (* blocked waiting for lock *)
	    else
			(*EDIT: used my version instead of lock::locks*)
	      (m, pcs, (locks@[(lock,tid,1)]), remove_waiter tid ws, rlocks, wlocks, barrier, latchs,sema)
    else (* thread is ready to run, so take a step *)
      let pc = val_of (pcs.(tid)) in
	match snd (p.(pc)) with
	  | Skip -> (m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
	  | Assign(x, e) -> (upd m x (eval m e), upd_pc tid (Some (pc+1)) pcs
, locks, ws, rlocks, wlocks, barrier, latchs,sema)
	  | Goto lab -> (m, upd_pc tid (Some (find_label p lab)) pcs, locks, 
ws, rlocks, wlocks, barrier, latchs,sema)
	  | IfNonZero(e, lab) ->
	      if (eval m e) != 0 then
		(m, upd_pc tid (Some (find_label p lab)) pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
	      else
		(m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
	  | Halt -> (m, upd_pc tid None pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
	  | Spawn lab -> (m, Array.append (upd_pc tid (Some (pc+1)) pcs) [|
Some (find_label p lab)|], locks, ws, rlocks, wlocks, barrier, latchs,sema)
	  | Acquire lock ->
	      (* (part 2.3): make locks reentrant *)
			if List.mem_assoc lock (assoc locks) then 
				if List.assoc lock (assoc locks) = tid then
					(m, upd_pc tid (Some(pc+1)) pcs, (upd_loc lock locks), ws, rlocks, wlocks, barrier, latchs,sema)
				else(m, pcs, locks,ws, rlocks, wlocks, barrier, latchs,sema)
			else (m, upd_pc tid (Some(pc+1)) pcs,(locks@[(lock,tid,1)]),ws, rlocks, wlocks, barrier, latchs,sema)
											
		(*(m, pcs, locks,ws)
	      else
		(m, upd_pc tid (Some (pc+1)) pcs, lock::locks, ws)*)
	  | Release lock ->
			if((List.mem_assoc lock (assoc locks)) && 
				List.assoc lock (assoc locks) = tid) then 
				(*(part 2.3): make locks reentrant *)
				(m, upd_pc tid (Some (pc+1)) pcs, decr_loc lock locks, ws, rlocks, wlocks, barrier, latchs,sema)
			(*(part 2.1): throw an exception if this thread does 
not hold lock *)
			else raise(My_exception tid)
	  | Wait lock ->
	      (*(part 2.1): throw an exception if this thread does 
not hold lock *)
			if((List.mem_assoc lock (assoc locks)) && 
				List.assoc lock (assoc locks) = tid) then 
	     (m, upd_pc tid (Some (pc+1)) pcs, remove lock locks, add_waiter
		lock tid ws, rlocks, wlocks, barrier, latchs,sema) 
		else raise(My_exception tid)
	  | NotifyAll lock ->
	      (*(part 2.1): throw an exception if this thread does 
not hold lock *)
			if((List.mem_assoc lock (assoc locks)) && 
				List.assoc lock (assoc locks) = tid) then 
	      (m, upd_pc tid (Some (pc+1)) pcs, locks, notify_all lock ws, rlocks, wlocks, barrier, latchs, sema)
			else raise(My_exception tid)
	  (*(part 2.2): Implement the TryAcquire(lock,lab) command *)
	  | TryAcquire(lock,lab) -> 
			if(List.mem_assoc lock (assoc locks)) then 
				if (List.assoc lock (assoc locks) = tid) then
					(m, upd_pc tid (Some (find_label p lab)) pcs, (upd_loc lock locks), ws, rlocks, wlocks, barrier, latchs, sema)
				else(m, upd_pc tid (Some(pc+1)) pcs, locks,ws, rlocks, wlocks, barrier, latchs, sema)
			else (m, upd_pc tid (Some(find_label p lab)) pcs,(locks@[(lock,tid,1)]), ws, rlocks, wlocks, barrier, latchs, sema)			
	  (* TO DO (part 2.4): Add support for reader/writer locks *)
	  | RdAcquire lock -> 
			if List.mem_assoc lock wlocks then 
				if (List.assoc lock wlocks = tid) then raise(My_exception tid)
				else (m, pcs, locks,ws, rlocks, wlocks, barrier, latchs ,sema)
			else 
				if List.mem_assoc lock rlocks && (List.assoc lock rlocks = tid) then raise(My_exception tid)
				else
				(m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks@[(lock,tid)], wlocks, barrier, latchs,sema)
	  | WrAcquire lock -> 
			if List.mem_assoc lock wlocks then
				if (List.assoc lock wlocks = tid) then raise(My_exception tid)
				else (m, pcs, locks,ws, rlocks, wlocks, barrier, latchs,sema)
			else if List.mem_assoc lock rlocks then 
				if (List.assoc lock rlocks = tid) then raise(My_exception tid)
				else (m, pcs, locks,ws, rlocks, wlocks, barrier, latchs,sema)
			else(m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks@[(lock,tid)], barrier, latchs,sema)
	  | RdWrRelease lock ->
			if (List.mem_assoc lock wlocks  && List.assoc lock wlocks = tid) then
				(m, upd_pc tid (Some (pc+1)) pcs,locks, ws, rlocks, wrremove lock tid wlocks, barrier, latchs,sema)
			else 
				if (List.mem_assoc lock rlocks && List.assoc lock rlocks = tid) then 
				(m, upd_pc tid (Some (pc+1)) pcs,locks, ws, wrremove lock tid rlocks, wlocks, barrier, latchs,sema)
				else raise(My_exception tid)
		 
	  (* TO DO (part 3.1): Implement barriers *)
	  | Barrier -> failwith "Unimplemented"
			(*if List.mem_assoc tid barrier = false then*)
			(*(m, pcs,locks, ws, wrremove lock rlocks, wlocks, barrier@[(tid,pc)], latchs)
			else 
				if (*check if Some.lenght = barrier.length*) then 
					(*update the pc for each thread and clear bar*)
				else (*block each thread in barrier*)*)
	  (* TO DO (part 3.2): Implement countdown latches *)
	  | CreateLatch (latch,n) -> 
	  (m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs@[(latch, eval m n)],sema)
          | WaitLatch latch -> 
			if List.mem_assoc latch latchs then
				if List.assoc latch latchs > 0 then 
					 (m, pcs, locks, ws, rlocks, wlocks, barrier, latchs, sema)
				else (m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
			else raise(My_exception tid)
          | CountDown latch -> 
			if List.mem_assoc latch latchs then
				if List.assoc latch latchs > 0 then 
					 (m, pcs, locks, ws, rlocks, wlocks, barrier, decr_latch latch latchs,sema)
				else (m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs,sema)
			else raise(My_exception tid)
	  (* TO DO (part 3.3): Implement semaphores *)
          | AcqSem sem -> 
			if List.mem_assoc sem sema then 
				(m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs, change_sem sem "-" sema)
			else raise(My_exception tid)
          | RelSem sem ->
			if List.mem_assoc sem sema then 
				(m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs, change_sem sem "+" sema)
			else (m, upd_pc tid (Some (pc+1)) pcs, locks, ws, rlocks, wlocks, barrier, latchs, sema@[(sem,1)])

(* IMPORTANT GRADING NOTE:

  Modify the next three functions as appropriate as you change the
  interpreter's data structures.

  mem_of should get the set of variable bindings in the heap
  pcs_of should return the set of program counters
  to_initial_config takes a memory and an array of program counter 
options,
    and should create an initial config with no locks held, no barriers
    encountered, etc. 
 *)
let mem_of (c:config):(string * int) list =
  match c with (m, _, _, _, _, _, _, _, _) -> m

let pcs_of (c:config):pc option array =
  match c with (_, pcs, _, _, _, _, _, _, _) -> pcs

let to_initial_config (m:(string * int) list) (pcs:pc option array) =
  (m, pcs, [], [],[],[],[],[],[("",0)])

let run (s:scheduler) (p:program) =
  let halted pcs = Array.fold_left (fun a pc -> a && (is_none pc)) true 
pcs in
  let rec run' (p:program) (c:config) =
    let (_, pcs, _, _, _, _, _, _, _) as c' = step s p c in
      print_config c';
      if not (halted pcs) then
	run' p c'
  in
    run' p (to_initial_config [] [|Some 0|])

let deadlocked (p:program) (c:config):bool =
  failwith "Implement me"

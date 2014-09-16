class NFA
  # A non-deterministic (or deterministic) finite automaton

  # The states of the automaton are arbitrary Ruby objects.  For
  # example, they could be integers, strings, or regular expressions.

  # The automaton is represented by three fields:
  #
  #  @start -- the initial state
  #  @final -- a set of final states (an array)
  #  @delta -- the transition function
  #    In Java notation, @delta has type Hash<State,Hash<Sym,Array<State>>>,
  #    i.e., @delta[state] is itself a hash, and @delta[state][sym]
  #    is an array containing all possible states to transition to
  #    from "state" upon seeing "sym" from the input.

  attr_accessor :delta
  attr_accessor :final
  attr_accessor :start
  def initialize
    @delta = Hash.new  # transition function
    @final = []  # final states
    @start = nil   # start state
  end
  def set_start(s)
    @start = s
  end
  def make_final(s)
    @final << s
  end
  def add_trans(s1, sym, s2)  # sym should be a string
    fail "sym must be a single character" if sym.length != 1
    @delta[s1] = Hash.new if not (@delta[s1])
    if (@delta[s1][sym])
      @delta[s1][sym] << s2
    else
      @delta[s1][sym] = [s2]
    end
  end
  def states
    r = [@start] + @final
    @delta.each { |s1, h|
      r << s1
      h.each { |sym, s2s| r += s2s }
    }
    r.uniq
  end
  def to_s
    r = "Start state: #{@start}\n"
    r << "Final states: #{@final.join(" ")}\n"
    @delta.each { |s1, h|
      h.each { |sym, s2s|
        s2s.each { |s2|
          r << "#{s1} -->#{sym} #{s2}\n"
        }
      }
    }
    r
  end
  def accept?(s)
    cur = [@start]
    pos = 0
    while pos < s.length
      res = []
      (cur.map { |state|
         if @delta[state] then
           @delta[state][s[pos].chr]
         else
           []
         end }).each { |move|
        if move then
          res.concat(move)
        end
      }
      cur = res
      pos = pos + 1
    end
    cur.find { |state| @final.include? state }
  end

  ####################################################

  # Return the NFA corresponding to regular expression r
  def NFA.of_re(r)
    # FILL IN
	#puts "HI!!!!!!!!!!"
    nfa = NFA.new
	nfa.set_start(r)
	to_proc = Array.new
	to_proc.push(r)
	while (to_proc.empty? == false) do
		exp = to_proc.pop
		if exp.check then nfa.make_final(exp) end
		exp.trans.each{|a,d|
			if nfa.states.include?(d) == false
				to_proc.push(d)
			end 
			nfa.add_trans(exp,a,d)
		}
	end
	return nfa
  end

  # Return true if self is actually a DFA
  def is_dfa?
    # FILL IN
	@delta.each{|state,hsh|
		hsh.each{|currState,arr|
			 if arr.length > 1 then return false end 
		}
	}
    return true
  end

  #getting the Alphbet 
  def get_alphabet
	alpha = Array.new
	@delta.each{|s, hsh|
		hsh.each{|key, val|
			if(key != nil) then alpha.push(key) end
		}
	}
	alpha.uniq!
	return alpha
  end 
 
 #the move function
 def move(state,symb)
	moveArr = Array.new
	state.each{|s|
	if @delta.has_key?(s) == true
		if @delta[s].has_key?(symb) == true
			moveArr.push(@delta[s][symb])
		end
	end
	}
	moveArr.flatten!
	moveArr.uniq!
	return moveArr
 end
 
  # Return a new NFA that accepts the same language as self,
  # but is a DFA.  Self is not modified
  def to_dfa
    # FILL IN
	if self.is_dfa? == true then 
		nfa = NFA.new
		nfa.start = @start.dup
		nfa.final = @final.dup
		nfa.delta = @delta.dup
		return nfa 
	end  
	state = (@start).dup
	visited = Array.new
	bigR = Array.new
	#visited.push([state])
	bigR.push([state])
	nfa = NFA.new
	nfa.set_start([state])
	alpha = get_alphabet
	while bigR.empty? == false do
		r = bigR.pop
		if visited.include?(r) == false then visited.push(r) end
		alpha.each{|letter|
			moves = move(r,letter)
			if moves.empty? == false then
				if visited.include?(moves) == false then
					bigR.push(moves)
				end
				nfa.add_trans(r,letter,moves) 
			end
		}
	end
	nfa.states.each{|key|
	#puts "key: #{key}   value: #{val}"
		@final.each{|s|
			if key.include?(s)
				nfa.make_final(key)
			end
		}
	}
	return nfa
  end
end

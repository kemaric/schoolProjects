class REEpsilon
  def ==(r)
    r.class == REEpsilon
  end
  alias :eql? :==
  def hash
    42
  end
  def to_s
    "()"
  end
  def check
    return true
  end
  def trans
	return []
  end
end

class REConst
  attr_accessor :sym
  def initialize(c)
    @sym = c
  end
  def ==(r)
    r.class == REConst && r.sym == @sym
  end
  alias :eql? :==
  def hash
    @sym[0]
  end
  def to_s
    sym
  end
  def check
    false   # filled in for you
  end
  def trans
    [[@sym, REEpsilon.new]]   # filled in for you
  end
end

class REStar
  attr_accessor :expr
  def initialize(e)
    @expr = e
  end
  def ==(r)
    r.class == REStar && r.expr == @expr
  end
  alias :eql? :==
  def hash
    (@expr.hash * 13 + 2) & 0x7FFFFFFF
  end
  def to_s
    if @expr.class == REUnion or @expr.class == REConcat
      "(#{@expr.to_s})*"
    else
      "#{@expr.to_s}*"
    end
  end
  def check
    return true
  end
  def trans
   newArr = Array.new()
	@expr.trans().each{|a,f|
		temp = Array.new(2)
		temp[0] = a
		new = REConcat.new(f,self)
		temp[1] = new.dup
		newArr.push(temp)
	}
	return newArr
  end
end

class REUnion
  attr_accessor :expr1
  attr_accessor :expr2
  def initialize(e1, e2)
    @expr1 = e1
    @expr2 = e2
  end
  def ==(r) # union is symmetric
   r.class == REUnion && 
     ((r.expr1 == @expr1 && r.expr2 == @expr2) ||
      (r.expr1 == @expr2 && r.expr2 == @expr1)) 
  end
  alias :eql? :==
  def hash # make sure e1 cup e2 and e2 cup e1 have the same hash
    h1 = @expr1.hash
    h2 = @expr2.hash
    if h1 < h2 then
      return (h1 * 129 + h2 * 13 + 2) & 0x7FFFFFFF
    else
      return (h2 * 129 + h1 * 13 + 2) & 0x7FFFFFFF
    end
  end
  def to_s
    "#{expr1}|#{expr2}"
  end
  def check
	if @expr1.check() then return true
	else return @expr2.check() end
  end
  def trans
	return @expr1.trans() | @expr2.trans()
  end
end

class REConcat
  attr_accessor :expr1
  attr_accessor :expr2
  def initialize(e1, e2)
    @expr1 = e1
    @expr2 = e2
  end
  def ==(r)
    r.class == REConcat && r.expr1 == @expr1 && r.expr2 == @expr2
  end
  alias :eql? :==
  def hash
    (expr1.hash * 129 + @expr2.hash * 13 + 3) & 0x7FFFFFFF
  end
  def to_s
    if @expr1.class == REUnion
      r = "(#{@expr1})"
    else
      r = "#{@expr1}"
    end
    if @expr2.class == REUnion
      r += "(#{@expr2})"
    else
      r += "#{@expr2}"
    end
    r
  end
  def check
    if @expr1.check() then return expr2.check()
	else return false end
  end
  def trans
	if expr1.check() then s = expr2.trans()
	else s = Array.new end
	newArr = Array.new
	expr1.trans().each{|a,e|
		arr = Array.new(2)
		arr[0] = a.dup
		new = REConcat.new(e,expr2)
		arr[1] = new.dup
		newArr.push(arr)
	}
	return (newArr|s) 
  end
end

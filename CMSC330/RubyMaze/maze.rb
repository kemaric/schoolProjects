#!/usr/local/bin/ruby

### DO NOT INCLUDE YOUR NAME IN THIS FILE
## Other students will be reading your code as a code review
## If you include your name, they will know who you are

# ########################################
# CMSC 330 - Project 1
# ########################################

#-----------------------------------------------------------
# FUNCTION DECLARATIONS
#-----------------------------------------------------------


class Section
  attr_accessor "x", "y", "dir", "tag"
  def initialize (x,y)
    @x = Integer(x)
    @y = Integer(y)
    @dir = Hash.new()
    @tag = false
  end
   
  def matchDir(direction, weight)
    index = 0;
    direction.each_char{ |char|
        @dir.store(char,weight[index]);
        index +=1;
  }
  end
end

class Path
  attr_accessor "name","startSec","direction","totalW"
  def initialize(name,startSec, direction, totalW) 
    @name = name
    @startSec = startSec
    @direction = direction
    @totalW = totalW
  end 
end

class Maze
  attr_accessor "size" "xStart", "yStart", "xEnd", "yEnd", "grid"
  def initialize(size, xStart, yStart, xEnd, yEnd)
    @size = Integer(size)
    @xStart = Integer(xStart)
    @yStart = Integer(yStart)
    @xEnd = Integer(xEnd)
    @yEnd = Integer(yEnd)
    
    #grid is a 2D array. Outer being x and inner being y
    @grid = Array.new(@size){Array.new(@size)}
  end
  
  def initMaze(file)
      file.rewind;
      line = file.gets
     while line = file.gets do
       line =~ /^(\d+)\s(\d+)\s([dulr]*)\s?(\d+.\d+)?\s?(\d+.\d+)?\s?(\d+.\d+)?\s?(\d+.\d+)?/
       if $1 == nil || $2 == nil then break end
       direction = $3; weight =[ $4, $5, $6, $7];
       section = Section.new($1,$2)       
       section.matchDir(direction,weight)
       @grid[section.x][section.y] = section;
     end
      
  end
  
end 

def parse(file)
  invalidArr = Array.new()
  line = file.gets
  if line !~/^maze:\s\d\s\d+:\d+ ->\s\d+:\d+/
    invalidArr.push(line)
  end
  while line = file.gets
    if ((line !~ /^(\d+),(\d+):\s([dulr]*)?\s?(-?\d+.\d+)?,?(-?\d+.\d+)?,?(-?\d+.\d+)?,?(-?\d+.\d+)?$/) &&
      (line !~ /(("*([^:,]+):[(](\d+),(\d+)[)]((,[udlr])+)"),*)/)
      )
      invalidArr.push(line)
    end 
  end 
   
  if invalidArr.empty?
    file.rewind;
    line = file.gets
    line =~/^maze:\s(\d+)\s(\d+):(\d+)\s->\s(\d+):(\d+)$/
    puts "#{$1} #{$2} #{$3} #{$4} #{$5}"
    while line = file.gets
      if (line =~ /^(\d+),(\d+):\s([dulr]*)?\s?(-?\d+.\d+)?,?(-?\d+.\d+)?,?(-?\d+.\d+)?,?(-?\d+.\d+)?$/) 
            puts "#{$1} #{$2} #{$3} #{$4} #{$5} #{$6} #{$7}"      
      elsif (line =~ /\"([^:, ]+):[(](\d+),(\d+)[)],([,udlr]*)\",?/)
        arr = line.scan(/\"([^:, ]+):[(](\d+),(\d+)[)],([,udlr]*)\",?/)
        arr.each{ |ln|
          puts "path #{ln[0]} #{ln[1]} #{ln[2]} #{ln[3].gsub(",", "")}"
        }
      end
    end 
  else 
    puts "invalid maze"
    invalidArr.each{ |line|
      puts line
    }
  end
  
end

#This finds the number of openings in the maze 
def find_open(file) 
  line = file.gets
  if line == nil then return end
  openings = {"u" => 0, "d" => 0, "l" => 0, "r" => 0 };
  # read additional lines
  while line = file.gets do
    # begins with "path", must be path specification
    if line[0...4] != "path" 
      if line.include? "u"
        openings["u"] += 1;
      end
      if line.include? "d"
        openings["d"] += 1;
      end
      if line.include? "l"
        openings["l"] += 1;
      end
      if line.include? "r"
        openings["r"] += 1;
      end 
    end
  end
  puts "u: #{openings["u"]}, d: #{openings["d"]}, l: #{openings["l"]}, r: #{openings["r"]}"
end

#This finds the number of closed cells in the maze 
def number_closed(file)
  numClosed = 0;
  line = file.gets
  if line == nil then return end
  # read additional lines
  while line = file.gets do
    # begins with "path", must be path specification
    if line[0...4] != "path" 
      if (!(line.include? "u") && !(line.include? "d") &&
      !(line.include? "l") && !(line.include? "r"))
        numClosed += 1;
      end
    end
  end
  puts "#{numClosed}"
end


#This function traveres the pathes and 
def rank_by_weight(file)
  file.rewind;
  line = file.gets
  if line == nil then return end
      
    # read 1st line, must be maze header
    sz, sx, sy, ex, ey = line.split(/\s/)
    maze = Maze.new(sz,sx,sy,ex,ey)
    maze.initMaze(file)
    array = Array.new;
    file.rewind;
    #storing the paths' information into the array 
  while line = file.gets do
     if (line[0...4] == "path")  
         p, name, x, y, ds = line.split(/\s/);
        startS = maze.grid[Integer(x)][Integer(y)]
        element = Path.new(name,startS,ds,0.0)
        array.push(element)
     end
    end
 # puts array.to_s   
   
if array.size < 1 then return (puts "None")  end
    index = 0;
    #iterates through each of the the paths in array  
    while  index < array.size do
    direction = array[index].direction;
       direction.each_char{ |ch|
        array[index].totalW += Float(array[index].startSec.dir[ch])
      case ch
      when "u"
         x = array[index].startSec.x;
         y = array[index].startSec.y;
         y -= 1;
         startS = maze.grid[Integer(x)][Integer(y)]
         array[index].startSec = startS;
      when "d"
         x = array[index].startSec.x;
         y = array[index].startSec.y;
         y += 1;
         startS = maze.grid[Integer(x)][Integer(y)]
         array[index].startSec = startS;   
      when "l"
         x = array[index].startSec.x;
         y = array[index].startSec.y;
         x -= 1;
         startS = maze.grid[Integer(x)][Integer(y)]
         array[index].startSec = startS;
      when "r"
   
         x = array[index].startSec.x;
         y = array[index].startSec.y;
         x += 1;
         startS = maze.grid[Integer(x)][Integer(y)]
         array[index].startSec = startS;
     
      end 
    }
    index += 1
    end 
   array.sort!{|p1, p2| p1.totalW <=> p1.totalW}
  list = ""
     array.each{ |path|
       list += "#{path.name}, "
   }
   list.chomp!(", ")
   puts list
end


#-----------------------------------------------------------
# the following is a parser that reads in a simpler version
# of the maze files.  Use it to get started writing the rest
# of the assignment.  You can feel free to move or modify 
# this function however you like in working on your assignment.


def read_and_print_simple_file(file)

  line = file.gets
  if line == nil then return end
    
  # read 1st line, must be maze header
  sz, sx, sy, ex, ey = line.split(/\s/)
  puts "+-" * Integer(sz) + "+";
  counter = 0
  bottom = "+"
  maze = Maze.new(sz,sx,sy,ex,ey)
  maze.initMaze(file)
  xCount = 0
  while xCount < Integer(sz)
    yCount = 0
    bottom = "+"
    middle = "|"
    while yCount < Integer(sz)
    section = maze.grid[yCount][xCount]
      if section != nil && section.dir.has_key?("d")
            bottom += " +"
      else
            bottom += "-+"
      end
 
      if section != nil && section.dir.has_key?("r") 
        if(section.x == Integer(sx) && section.y == Integer(sy))
          middle += "s"
        elsif(section.x == Integer(ex) && section.y == Integer(ey))
          middle += "e"
        else
          middle += "  "
        end
      else
        if(section != nil && section.x == Integer(sx) && section.y == Integer(sy))
            middle += "s|"
        elsif(section != nil && section.x == Integer(ex) && section.y == Integer(ey))
            middle += "e|"
        else 
          middle += " |"
        end
      end
      yCount += 1
    end
    xCount +=1
    puts middle;
    puts bottom;
  end
 # puts "+-" * Integer(sz) + "+"
end

def solve_maze(file)
   line = file.gets
   if line == nil then return end
     
   # read 1st line, must be maze header
   sz, sx, sy, ex, ey = line.split(/\s/)
   maze = Maze.new(sz,sx,sy,ex,ey)
   maze.initMaze(file)
   
   section = maze.grid[Integer(sx)][Integer(sy)]
   visitedStack = Array.new()
   visitedStack.push(section)
   while visitedStack.empty? == false do
     section = visitedStack.pop()
     if section.x == Integer(ex ) && section.y == Integer(ey)
       return true
     end 
     if(section.tag == false)
          section.tag = true
          section.dir.each_key{ |char|
            case char
                  when "u"
                     x = section.x;
                     y = section.y;
                     y -= 1;
                     neighbor =  maze.grid[x][y]
                  when "d"
                    x = section.x;
                    y = section.y;
                    y += 1; 
                    neighbor =  maze.grid[x][y]
                  when "l"
                      x = section.x;
                      y = section.y;
                     x -= 1;
                    neighbor =  maze.grid[x][y]
                  when "r"
                      x = section.x;
                      y = section.y;
                     x += 1;
                     neighbor =  maze.grid[x][y]
                  else
                    break;
                  end 
           if neighbor.tag == false
             visitedStack.push(neighbor)
           end
         } 
     end
   end 
   return false;
end 


#-----------------------------------------------------------
# EXECUTABLE CODE
#-----------------------------------------------------------

#----------------------------------
# check # of command line arguments

if ARGV.length < 2
  fail "usage: maze.rb <command> <filename>" 
end

command = ARGV[0]
file = ARGV[1]
maze_file = open(file)

#----------------------------------
# perform command

case command

when "parse"
  parse(maze_file)

when "print"
  read_and_print_simple_file(maze_file)

when "open"
  find_open(maze_file)

when "closed"
  number_closed(maze_file)
  
when "paths"
  rank_by_weight(maze_file)
  
when "solve"
  if(solve_maze(maze_file))
    puts true
  else
   puts  false
  end
else
  fail "Invalid command"
end

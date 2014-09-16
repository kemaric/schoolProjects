#!/usr/bin/env ruby

# tests maze.rb for public inputs

mazes = ["maze1", "maze2"]
modes = ["closed", "open", "print", "paths", "solve"]

mazes.each do
        |maze|
   modes.each do
        |mode|

        puts "TESTING: #{mode} inputs/#{maze}"

        system("ruby maze.rb #{mode} inputs/#{maze}")
   end
end

mazes = ["maze1-std", "maze2-std", "maze3-std", "maze4-std"]
modes = ["parse"]

mazes.each do
        |maze|
   modes.each do
        |mode|

        puts "TESTING: #{mode} inputs/#{maze}"

        system("ruby maze.rb #{mode} inputs/#{maze}")
   end
end


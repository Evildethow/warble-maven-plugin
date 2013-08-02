class HelloWorld
  require "java"

  stringHello= "Hello World"
  stringDate = java.util.Date.new

  puts "#{stringHello.to_s}"
  puts "Date := #{stringDate.to_s}"
end
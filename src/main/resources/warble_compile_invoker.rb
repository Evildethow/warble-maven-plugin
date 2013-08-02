require 'warbler'
require 'rake'


print('Currently compiling from ' + Dir.pwd)

Warbler::Task.new(:warble) do |t|
  t.config.compiled_ruby_files = FileList['target/**/*.rb']
end

Rake::Task['warble:compiled'].invoke
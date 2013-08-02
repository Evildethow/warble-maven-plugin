require 'warbler'
require 'rake'


print('Currently compiling from ' + Dir.pwd)

Warbler::Task.new(:warble) do |t|
  t.config.compiled_ruby_files = FileList['src/test/test-project/target/**/*.rb']
end

Rake::Task['warble:compiled'].invoke
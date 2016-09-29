module.exports = function(grunt) {
  grunt.initConfig({
    ngAnnotate: {
        options: {
            singleQuotes: true
        },
        app: {
            files: {
                './src/main/resources/ngscripts/script.anotate.js': ['./src/main/resources/scripts/script.js']
            }
        }
    },

    concat: {
        js: {
            src: ['./src/main/resources/ngscripts/script.anotate.js'],
            dest: './src/main/resources/ngscripts/script.result.js'
        }
    },

    uglify: {
        js: {
            src: ['./src/main/resources/ngscripts/script.result.js'],
            dest: './src/main/resources/scripts/script.min.js'
        }
    }
  });
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-ng-annotate');

  grunt.registerTask('default', ['ngAnnotate','concat','uglify']);
};
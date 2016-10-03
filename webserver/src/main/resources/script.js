var app = angular.module('filterSpecial', ['ngMaterial', 'ngMessages', 'material.svgAssetsCache']);

app.directive('fileModel', ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);

app.service('fileUpload', ['$http', function($http) {
	this.uploadFileToUrl = function(file, name, tag, uploadUrl,callback) {
		var fd = new FormData();
		fd.append('file', file);
		fd.append('name', name);
		fd.append('tag', tag);

		$http.put(uploadUrl, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		}).success(function(response) {
            console.log(response);
            callback(response);
		}).error(function(e) {
			console.log(e);
		});
	}
}]);

app.controller('filterCtrl', ['$scope', 'fileUpload', '$mdDialog', '$http', function($scope, fileUpload, $mdDialog, $http) {
	$scope.startsWith = function(actual, expected) {
		var lowerStr = (actual + "").toLowerCase();
		return lowerStr.indexOf(expected.toLowerCase()) === 0;
	}

	$scope.getTagsForInitImages = function() {
		for (i = 0; i < $scope.images.length; i++) {
			$scope.getTags(i);
		}
	}

	$scope.getTags = function(i) {
		$http.get('/tags/image/' + $scope.images[i].id).success(function(data) {
			$scope.images[i].tags = data;
		}).error(function(e) {
			console.log(e);
		});
	}

	$scope.getTagsForImageWithId = function(id,callback){
	    $http.get('/tags/image/' + id)
	        .success(function(data) {
                callback(data);
            }).error(function(e) {
                console.log(e);
            });
	}

	$scope.getAllImages = function() {
		$http.get('/images/').success(function(data) {
			$scope.images = data;
			$scope.getTagsForInitImages();
		});
	}

	$scope.uploadFile = function() {
		var uploadUrl = "/image";

		console.log(uploadUrl);
		var file = $scope.myFile;
		console.log('file is ');
		console.dir(file);
		fileUpload.uploadFileToUrl(file, $scope.name, $scope.tag, uploadUrl,function(response) {
		    $scope.getTagsForImageWithId(response.id,function(tags) {
		        response.tags = tags;
		        $scope.images.push(response);
		    });
		});
	};

	$scope.deleteImage = function(id) {
        $http.post('/delete/' + id).success(function() {
            var imageToDeleteId = $scope.images.map(function(image) {
                return image.id;
            }).indexOf(id);
            images.slice(imageToDeleteId,1);
        }).error(function(e) {
            console.log(e);
        });
	}

	$scope.showAdvanced = function(ev, id, name, tags) {
		$mdDialog.show({
				controller: DialogController,
				template: '<img src="/image/' + id + '/png" ></img> <p style="margin:3px">Name: ' + name + '</p><p style="margin:5px">Tags: ' + tags.toString() + '</p>',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true,
			});
	};

	function DialogController($scope, $mdDialog) {
		$scope.hide = function() {
			$mdDialog.hide();
		};

		$scope.cancel = function() {
			$mdDialog.cancel();
		};

		$scope.answer = function(answer) {
			$mdDialog.hide(answer);
		};
	}
}]);